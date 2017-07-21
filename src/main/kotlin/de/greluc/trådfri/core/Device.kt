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

/**
 * This class is a implementation of a generic Trådfri compatible device.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
open class Device(val id: String) {
    var name = ""
    var firmware = ""
    var manufacturer = ""
    var model = ""
    var type = DeviceType.DEV_TYPE_UNKNOWN

    open fun generatePayload() = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Device) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}