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
 * This class stores the message as an JSON object.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
internal class CoAPMessage { //TODO implement JSON format of Trådfri
    private val payload: String = ""
    private val method: String = ""

    fun getPayload() = payload

    fun getMethod() = method
}

/*
echo '{ "3311" : [{ "5851" : 255 }] }' | ./coap-client -u "Client_identity" -k "YOUR_KEY" -v 10 -m put "coaps://192.169..0.3:5684/15001/65538" -f -
 */