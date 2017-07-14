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
 * This enum class holds constants that are used over the whole project.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
internal enum class Constants(val value: Int) { //TODO comments for every entry and every group
    OOT_DEVICES(value = 15001), ROOT_GROUPS(value = 15004), ROOT_MOODS(value = 15005), ROOT_SMART_TASKS(value = 15010), ROOT_START_ACTION(value = 15013), // found under ATTR_START_ACTION
    PATH_GATEWAY_INFO_1(value = 15011), PATH_GATEWAY_INFO_2(value = 15012),

    ATTR_APPLICATION_TYPE(value = 5750), ATTR_DEVICE_INFO(value = 3), ATTR_NAME(value = 9001), ATTR_CREATED_AT(value = 9002), ATTR_ID(value = 9003), ATTR_REACHABLE_STATE(value = 9019), ATTR_LAST_SEEN(value = 9020), ATTR_LIGHT_CONTROL(value = 3311), // array

    ATTR_NTP(value = 9023), ATTR_FIRMWARE_VERSION(value = 9029), ATTR_CURRENT_TIME_UNIX(value = 9059), ATTR_CURRENT_TIME_ISO8601(value = 9060), ATTR_FIRST_SETUP(value = 9069), // ??? unix epoch value when gateway first setup
    ATTR_GATEWAY_ID(value = 9081), // ??? id of the gateway

    ATTR_LIGHT_STATE(value = 5850), // 0 / 1
    ATTR_LIGHT_DIMMER(value = 5851), // Dimmer, not following spec: 0..255
    ATTR_LIGHT_COLOR(value = 5706), // string representing a value in some color space
    ATTR_LIGHT_COLOR_X(value = 5709), ATTR_LIGHT_COLOR_Y(5710),

    ATTR_START_ACTION(value = 9042), // array

    ATTR_SMART_TASK_TYPE(value = 9040), // 4 = transition | 1 = not home | 2 = on/off
    ATTR_SMART_TASK_NOT_AT_HOME(value = 1), ATTR_SMART_TASK_LIGHTS_OFF(value = 2), ATTR_SMART_TASK_WAKE_UP(value = 4),

    ATTR_SMART_TASK_TRIGGER_TIME_INTERVAL(value = 9044), ATTR_SMART_TASK_TRIGGER_TIME_START_HOUR(value = 9046), ATTR_SMART_TASK_TRIGGER_TIME_START_MIN(value = 9047),

    ATTR_TRANSITION_TIME(value = 5712), ATTR_REPEAT_DAYS(value = 9041),

    DEFAULT_GATEWAY_PORT(value = 5684)
}