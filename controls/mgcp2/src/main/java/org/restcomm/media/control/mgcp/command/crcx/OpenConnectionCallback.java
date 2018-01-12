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

package org.restcomm.media.control.mgcp.command.crcx;

import com.google.common.util.concurrent.FutureCallback;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com)
 *
 */
public class OpenConnectionCallback implements FutureCallback<String> {

    private final CreateConnectionFsm fsm;
    private final CreateConnectionContext context;
    
    private final boolean primary;

    public OpenConnectionCallback(boolean primary, CreateConnectionFsm fsm, CreateConnectionContext context) {
        this.fsm = fsm;
        this.context = context;
        
        this.primary = primary;
    }

    @Override
    public void onSuccess(String result) {
        // Save data into context
        if(result != null) {
            this.context.setLocalDescription(result.replace("inactive", "sendrecv"));
        } else {
        this.context.setLocalDescription(result);
        }


        // Mark connection as open
        if(this.primary) {
            this.context.setPrimaryConnectionOpen(true);
        } else {
            this.context.setSecondaryConnectionOpen(true);
        }
        
        // Move to next state
        this.fsm.fire(CreateConnectionEvent.CONNECTION_OPENED, this.context);
    }

    @Override
    public void onFailure(Throwable t) {
        // Save error in context
        context.setError(t);
        
        // Fail command
        this.fsm.fire(CreateConnectionEvent.FAILURE, this.context);
    }

}
