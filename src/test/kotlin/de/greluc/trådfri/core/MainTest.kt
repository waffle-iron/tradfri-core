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
 * or visit www.greluc.de if you need additional information or have any
 * questions.
 */

package de.greluc.trådfri.core

/**
 * This file is used to test some functionalities of the library.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */

fun main(args: Array<String>) {
    println("Test started.")

    println(Constants.ATTR_APPLICATION_TYPE)
    println(Constants.ATTR_APPLICATION_TYPE.value)

    val gwData = GatewayData("a", "b")

    println(gwData.toString())
}
