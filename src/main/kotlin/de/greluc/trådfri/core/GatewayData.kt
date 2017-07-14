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

import de.greluc.trådfri.core.Constants.PRESET_GATEWAY_PORT
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * This data class stores the information about the Trådfri gateway.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
internal class GatewayData(host: String, private val port: String = PRESET_GATEWAY_PORT) {
    private val address: InetAddress

    init {
        //TODO display error dialogue instead of error messages in console
        try {
            address = InetAddress.getByName(host)
        } catch (e: UnknownHostException) {
            error("Host is not an InetAddress!!!")
            e.printStackTrace()
        }

        try {
            if (Integer.parseInt(port) < 0 || Integer.parseInt(port) > 65535) error("No valid Port entered!!!")
        } catch (e: NumberFormatException) {
            error("No valid Port entered!!!")
            e.printStackTrace()
        }

    }

    fun getInetAddress() = address.hostAddress

    fun getPort() = port
}