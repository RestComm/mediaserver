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

package org.restcomm.media.bootstrap.ioc;

import org.restcomm.media.bootstrap.ioc.provider.rtp.CnameGeneratorGuiceProvider;
import org.restcomm.media.bootstrap.ioc.provider.rtp.PortManagerGuiceProvider;
import org.restcomm.media.bootstrap.ioc.provider.rtp.RtpConnectionFactoryGuiceProvider;
import org.restcomm.media.bootstrap.ioc.provider.rtp.RtpSessionFactoryGuiceProvider;
import org.restcomm.media.network.deprecated.PortManager;
import org.restcomm.media.rtp.CnameGenerator;
import org.restcomm.media.rtp.RtpConnectionFactory;
import org.restcomm.media.rtp.RtpSessionFactory;

import com.google.inject.AbstractModule;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com)
 *
 */
public class RtpModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CnameGenerator.class).toProvider(CnameGeneratorGuiceProvider.class).asEagerSingleton();
        bind(PortManager.class).toProvider(PortManagerGuiceProvider.class).asEagerSingleton();
        bind(RtpConnectionFactory.class).toProvider(RtpConnectionFactoryGuiceProvider.class).asEagerSingleton();
        bind(RtpSessionFactory.class).toProvider(RtpSessionFactoryGuiceProvider.class).asEagerSingleton();
    }

}