/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.restcomm.media.rtp.jitter.adaptive.strategy;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.restcomm.media.rtp.RtpPacket;
import org.restcomm.media.rtp.jitter.adaptive.AdaptiveJitterBuffer;

/**
 * Algorithm 1 - from Adaptive Playout Mechanisms for Packetized Audio Applications in Wide-Area Networks (Ramachandran Ramjee,
 * Jim Kurose, Don Towsley) <br>
 * <br>
 * <br>
 * di = a * di-1 + (1 - a) * ni <br/>
 * vi = a*vi-1 + (1 - a) * |di - n1|
 * 
 * ai : the time at which packet i is received at the receiving host, <br>
 * vi : the queueing delay experienced by packet i as it is sent from the source to the destination host, <br>
 * di : the amount of time from when the ith packet is generated by the source until it is played out at the destination host, <br>
 * ni : the total delay" introduced by the network, ni = ai - ti. Because we have not assumed that the sender and receiver
 * clocks are synchronized, ni may not be equal to the actual delay experienced by the packet ).
 * 
 * @author jqayyum
 *
 */
public class LinearRecursiveFilter implements PlayoutStrategy, Serializable {
    private static final long serialVersionUID = 172437427076563785L;

    private final static Logger logger = Logger.getLogger(LinearRecursiveFilter.class);

    static final double a = 0.998002;
    double delay = 0;
    double variance = 0;
    long firstPacketDelay = -1;
    long playoutOffset = -1;
    int lastPlayedSequence = -1;

    AdaptiveJitterBuffer jitterBuffer;

    public LinearRecursiveFilter() {
    }

    public void setJitterBuffer(AdaptiveJitterBuffer jitterBuffer) {
        this.jitterBuffer = jitterBuffer;
    }

    @Override
    public long getPlayoutOffset(RtpPacket p) {
        logger.debug("get playout offset");

        long currentDelay = jitterBuffer.getCurrentDelay();
        if (firstPacketDelay == -1) {
            this.firstPacketDelay = currentDelay;
        }
        delay = (a * delay + (1 - a) * jitterBuffer.getCurrentDelay());
        variance = a * variance + (1 - a) * Math.abs(currentDelay - firstPacketDelay);
        // beginning of talkspurt
        if (p.getMarker()) {
            // playout offset for the talkspurt
            playoutOffset = (long) Math.ceil(delay + 4 * variance);
            lastPlayedSequence = p.getSeqNumber();
        } else {
            playoutOffset = -1; // no playout offset for inter-talkspurt packets
        }
        return playoutOffset;
    }

    

    @Override
    public String toString() {
        return "LinearRecursiveFilter [delay=" + delay + ", variance=" + variance + ", firstPacketDelay=" + firstPacketDelay
                + ", playoutOffset=" + playoutOffset + ", jitterBuffer=" + jitterBuffer + "]";
    }

}
