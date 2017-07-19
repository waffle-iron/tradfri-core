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
 * or visit www.trådfri-central.de if you need additional information or have any
 * questions.
 */

package de.greluc.trådfri.core

import de.greluc.trådfri.core.Constants.PRESET_GATEWAY_PORT
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * This class builds a new Controller and is the entry point for Trådfri-Core.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 19.07.2017
 */
class ControllerBuilder {
    var address: InetAddress = InetAddress.getLocalHost()
    var port: String = PRESET_GATEWAY_PORT

    fun setServerAddress(address: String) {
        try {
            this.address = InetAddress.getByName(address)
        } catch (e: UnknownHostException) {
            error("Server address is invalid!")
        }
    }

    fun setServerPort(port: String) {
        try {
            if (Integer.parseInt(port) in 0..65535) {
                this.port = port
            } else {
                error("Port is invalid!")
            }
        } catch (e: NumberFormatException) {
            error("Port is invalid!")
        }
    }

    fun setServerPort(port: Int) {
        if (port in 0..65535) {
            this.port = port.toString()
        } else {
            error("Port is invalid!")
        }
    }
}