/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2017, Telestax Inc and individual contributors
 * by the @authors tag.
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

package org.restcomm.media.core.asr;

import org.restcomm.media.core.asr.AsrEngine;
import org.restcomm.media.core.asr.AsrEngineProvider;
import org.restcomm.media.core.drivers.asr.AsrDriverManager;
import org.restcomm.media.core.resource.vad.VoiceActivityDetectorProvider;
import org.restcomm.media.core.scheduler.PriorityQueueScheduler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gdubina
 */
public class AsrEngineProviderImpl implements AsrEngineProvider {

    private final AtomicInteger id;
    private final PriorityQueueScheduler mediaScheduler;
    private final AsrDriverManager driverManager;
    private final VoiceActivityDetectorProvider vadProvider;

    public AsrEngineProviderImpl(final PriorityQueueScheduler mediaScheduler, final AsrDriverManager driverManager, VoiceActivityDetectorProvider vadProvider) {
        this.mediaScheduler = mediaScheduler;
        this.driverManager = driverManager;
        this.id = new AtomicInteger(0);
        this.vadProvider = vadProvider;
    }

    @Override
    public AsrEngine provide() {
        return new AsrEngineImpl(nextId(), this.mediaScheduler, this.driverManager, this.vadProvider.provide());
    }

    private String nextId() {
        return "asr-engine" + id.getAndIncrement();
    }

}
