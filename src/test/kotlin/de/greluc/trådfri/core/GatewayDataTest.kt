/*
 * Copyright (c) 2017 Lucas Greuloch (greluc). All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (a copy is included in the LICENSE file
 * that accompanied this code).
 *
 * Please contact lucas.greuloch@gmail.com
 * or visit trådfri.greluc.de if you need additional information or have any
 * questions.
 */

package de.greluc.trådfri.core

/**
 * Created by Lucas on 14.07.2017.
 */
internal class GatewayDataTest {
    @org.junit.jupiter.api.Test fun getInetAddress() {
        assert(GatewayData("192.168.1.1", "5864").getInetAddress().address.hostAddress.equals("192.168.1.1"))
        assert(GatewayData("192.168.1.1", "5864").getInetAddress().port == 5864)
        //assert(GatewayData("controller.trådfri.greluc.de").getInetAddress().equals("X.X.X.X:5864")) TODO add when static ip is available
    }

}