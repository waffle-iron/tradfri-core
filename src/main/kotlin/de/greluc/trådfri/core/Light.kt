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

import de.greluc.trådfri.core.Constants.ATTR_ID
import de.greluc.trådfri.core.Constants.ATTR_LIGHT_STATE
import de.greluc.trådfri.core.Constants.ATTR_NAME
import javax.json.Json

/**
 * This class represents a light. It extends the generic Device class.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 19.07.2017
 */
open class Light(id: String) : Device(id) {
    var state = ""

    override fun generatePayload(): String {
        val factory = Json.createBuilderFactory(null)
        val value = factory.createObjectBuilder()
                .add(ATTR_NAME, name)
                .add(ATTR_ID, id)
                .add(ATTR_LIGHT_STATE, state)
                .add("lastName", "Smith")
                .add("age", 25)
                .add("address", factory.createObjectBuilder()
                        .add("streetAddress", "21 2nd Street")
                        .add("city", "New York")
                        .add("state", "NY")
                        .add("postalCode", "10021"))
                .add("phoneNumber", factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "212 555-1234"))
                        .add(factory.createObjectBuilder()
                                .add("type", "fax")
                                .add("number", "646 555-4567")))
                .build()

        return value.toString()
    }
}