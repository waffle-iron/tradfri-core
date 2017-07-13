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
 * This enum class holds constants that are used over the whole project.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
internal enum class Constants(val value: Int) { //TODO comments for every entry and every group
    OOT_DEVICES(15001), ROOT_GROUPS(15004), ROOT_MOODS(15005), ROOT_SMART_TASKS(15010), ROOT_START_ACTION(15013), // found under ATTR_START_ACTION
    PATH_GATEWAY_INFO_1(15011), PATH_GATEWAY_INFO_2(15012),

    ATTR_APPLICATION_TYPE(5750), ATTR_DEVICE_INFO(3), ATTR_NAME(9001), ATTR_CREATED_AT(9002), ATTR_ID(9003), ATTR_REACHABLE_STATE(9019), ATTR_LAST_SEEN(9020), ATTR_LIGHT_CONTROL(3311), // array

    ATTR_NTP(9023), ATTR_FIRMWARE_VERSION(9029), ATTR_CURRENT_TIME_UNIX(9059), ATTR_CURRENT_TIME_ISO8601(9060), ATTR_FIRST_SETUP(9069), // ??? unix epoch value when gateway first setup
    ATTR_GATEWAY_ID(9081), // ??? id of the gateway

    ATTR_LIGHT_STATE(5850), // 0 / 1
    ATTR_LIGHT_DIMMER(5851), // Dimmer, not following spec: 0..255
    ATTR_LIGHT_COLOR(5706), // string representing a value in some color space
    ATTR_LIGHT_COLOR_X(5709), ATTR_LIGHT_COLOR_Y(5710),

    ATTR_START_ACTION(9042), // array

    ATTR_SMART_TASK_TYPE(9040), // 4 = transition | 1 = not home | 2 = on/off
    ATTR_SMART_TASK_NOT_AT_HOME(1), ATTR_SMART_TASK_LIGHTS_OFF(2), ATTR_SMART_TASK_WAKE_UP(4),

    ATTR_SMART_TASK_TRIGGER_TIME_INTERVAL(9044), ATTR_SMART_TASK_TRIGGER_TIME_START_HOUR(9046), ATTR_SMART_TASK_TRIGGER_TIME_START_MIN(9047),

    ATTR_TRANSITION_TIME(5712), ATTR_REPEAT_DAYS(9041),
}