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

package org.restcomm.media.core.resource.player.audio;

import org.restcomm.media.core.spi.player.Player;
import org.restcomm.media.core.spi.player.PlayerEvent;

/**
 *
 * @author kulikov
 */
public class AudioPlayerEvent implements PlayerEvent {

    private int eventID;
    private Player player;
    private int qualifier = 0;
    
    public AudioPlayerEvent(Player player, int eventID) {
        this.eventID = eventID;
        this.player = player;
    }
        

    public int getEventID() {
        return eventID;
    }

    @Override
    public int getID() {
        return eventID;
    }

    @Override
    public Player getSource() {
        return player;
    }

    @Override
    public int getQualifier() {
        return qualifier;
    }

}
