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
 * This enum class holds constants that are used over the whole project.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
object Constants { //TODO comments for every entry and every group and add missing attr
    const val OOT_DEVICES: String = "15001"
    const val ROOT_GROUPS: String = "15004"
    const val ROOT_MOODS: String = "15005"
    const val ROOT_SMART_TASKS: String = "15010"
    const val ROOT_START_ACTION: String = "15013" // found under ATTR_START_ACTION
    const val PATH_GATEWAY_INFO_PART1: String = "15011"
    const val PATH_GATEWAY_INFO_PART2: String = "15012"

    const val ATTR_APPLICATION_TYPE: String = "5750"
    const val ATTR_DEVICE_INFO: String = "3"
    const val ATTR_DEVICE_INFO_MANUFACTURER: String = "0"
    const val ATTR_DEVICE_INFO_MODEL: String = "1"
    const val ATTR_DEVICE_INFO_UNKONW1: String = "2" //TODO ???
    const val ATTR_DEVICE_INFO_FIRMWARE: String = "3"
    const val ATTR_DEVICE_INFO_UNKONW2: String = "6" //TODO ???
    const val ATTR_DEVICE_INFO_UNKONW3: String = "9" //TODO ???
    const val ATTR_NAME: String = "9001"
    const val ATTR_CREATED_AT: String = "9002"
    const val ATTR_ID: String = "9003"
    const val ATTR_REACHABLE_STATE: String = "9019"
    const val ATTR_LAST_SEEN: String = "9020"
    const val ATTR_LIGHT_CONTROL: String = "3311" // array

    const val ATTR_NTP: String = "9023"
    const val ATTR_FIRMWARE_VERSION: String = "9029"
    const val ATTR_RESET_SOFT: String = "9031"
    const val ATTR_FIRMWARE_UPDATE: String = "9034"
    const val ATTR_CURRENT_TIME_UNIX: String = "9059"
    const val ATTR_CURRENT_TIME_ISO8601: String = "9060"
    const val ATTR_FIRST_SETUP: String = "9069" // ??? unix epoch value when gateway first setup
    const val ATTR_GATEWAY_ID: String = "9081" // ??? id of the gateway

    const val ATTR_LIGHT_STATE: String = "5850" // 0 / 1
    const val ATTR_LIGHT_DIMMER: String = "5851" // Dimmer, not following spec: 0..255
    const val ATTR_LIGHT_COLOR: String = "5706" // string representing a value in some color space
    const val ATTR_LIGHT_COLOR_X: String = "5709"
    const val ATTR_LIGHT_COLOR_Y: String = "5710"

    const val ATTR_START_ACTION: String = "9042" // array

    const val ATTR_SMART_TASK_TYPE: String = "9040" // 4 = transition | 1 = not home | 2 = on/off
    const val ATTR_SMART_TASK_NOT_AT_HOME: String = "1"
    const val ATTR_SMART_TASK_LIGHTS_OFF: String = "2"
    const val ATTR_SMART_TASK_WAKE_UP: String = "4"

    const val ATTR_SMART_TASK_TRIGGER_TIME_INTERVAL: String = "9044"
    const val ATTR_SMART_TASK_TRIGGER_TIME_START_HOUR: String = "9046"
    const val ATTR_SMART_TASK_TRIGGER_TIME_START_MIN: String = "9047"

    const val ATTR_TRANSITION_TIME: String = "5712"
    const val ATTR_REPEAT_DAYS: String = "9041"

    const val ATTR_DISCOVER_ALL: String = "/.well-known/core"

    const val METHOD_GET: String = "GET"
    const val METHOD_POST: String = "POST"
    const val METHOD_PUT: String = "PUT"
    const val METHOD_DELETE: String = "DELETE"
    const val METHOD_DISCOVER: String = "DISCOVER"
    const val METHOD_OBSERVE: String = "OBSERVE"

    const val PRESET_CLIENT_IDENTITY: String = "password"
    const val PRESET_TEST_PSK: String = "sesame"
    const val PRESET_GATEWAY_PORT: String = "5684"

    const val PRESET_COLOR_COLD: String = "f5faf6"
    const val PRESET_COLOR_COLD_X: String = "24930"
    const val PRESET_COLOR_COLD_Y: String = "24694"
    const val PRESET_COLOR_NORMAL: String = "f1e0b5"
    const val PRESET_COLOR_NORMAL_X: String = "30140"
    const val PRESET_COLOR_NORMAL_Y: String = "26909"
    const val PRESET_COLOR_WARM: String = "efd275"
    const val PRESET_COLOR_WARM_X: String = "33135"
    const val PRESET_COLOR_WARM_Y: String = "27211"

    const val PRESET_DIMMER_0: String = "0"
    const val PRESET_DIMMER_MIN: String = "12"
    const val PRESET_DIMMER_MEDIUM: String = "127"
    const val PRESET_DIMMER_MAX: String = "254"

}

/*
Some findings regarding /15011 a.k.a the gateway (mostly based on looking at the code of the Android app):

a POST on /15011/9031 performs a soft reset
a PUT on /15011/9034 is used for performing a firmware update, but it looks like this is not implemented yet (firmware payload is empty)
a PUT on /15011/15012 can be used to perform many different operations (check OTA, commissioning, update time…) on the gateway, e.g update the current time by PUTting a timestamp with key 9060, etc.
/15011/9033 is TBD
/15011/9063 (POST) appears to be used for re-keying the gateway, as the code seems to involve the generation and transfer of a new PSK
 */

/*
| Object 				| Object ID   |
|:----------------------|:-----------:|
|    Digital Input				| 3200|
|    Digital Output  			| 3201|
|    Analogue Input  			| 3202|
|    Analogue Output 			| 3203|
|    Generic Sensor  			| 3300|
|    Illuminance Sensor 		| 3301|
|    Presence sensor 			| 3302|
|    Temperature Sensor 		| 3303|
|    Humidity Sensor 			| 3304|
|    Power Measurement 			| 3305|
|    Actuation 					| 3306|
|    Set Point 					| 3308|
|    Load Controller 				| 3310|
|    Light Controller 				| 3311|
|    Power Controller 				| 3312|
|    Accelerometer 				| 3313|
|    Magnetometer 				| 3314|
|    Barometer  	   		 	| 3315|
|    Voltage					| 3316|
|    Current					| 3317|
|    Frequency					| 3318|
|    Depth						| 3319|
|    Percentage					| 3320|
|    Altitude					| 3321|
|    Load						| 3322|
|    Pressure					| 3323|
|    Loudness					| 3324|
|    Concentration 				| 3325|
|    Acidity					| 3326|
|    Conductivity				| 3327|
|    Power						| 3328|
|    Power Factor				| 3329|
|    Distance					| 3330|
|    Energy						| 3331|
|    Direction					| 3332|
|    Time						| 3333|
|    Gyrometer					| 3334|
|    Color 						| 3335|
|    GPS Location 				| 3336|
|    Positioner 				| 3337|
|    Buzzer						| 3338|
|    Audio Clip					| 3339|
|    Timer						| 3340|
|    Addressable Text Display	| 3341|
|    On/Off Switch				| 3342|
|    Level Controllers			| 3343|
|    Up/Down Controller			| 3344|
|    Multiple Axis Joystick		| 3345|
|    Rate						| 3346|
|    Push Button				| 3347|
|    Multi-state Selector		| 3348|
|    Bitmap                     | 3349|
|    Stopwatch                  | 3350|



Below there is the set of Resources that can be used as building blocks for your Objects.

| Resource              | Resource ID   | Operations |Type   |
|:--------------------------------|:---:|:----------:|:-----:|
|    Digital Input State					| 5500|           R|Boolean|
|    Digital Input Counter				| 5501|           R|Integer|
|    Digital Input Polarity				| 5502|         R,W|Boolean|
|    Digital Input Debounce				| 5503|         R,W|Integer|
|    Digital Input Edge Selection	| 5504|         R,W|Integer|
|    Digital Input Counter Reset	| 5505|           E| Opaque|
|    Current Time 	              | 5506|         R,W|   Time|
|    Fractional Time  	          | 5507|         R,W|  Float|
|    Min X Value	                | 5508|           R|  Float|
|    Max X Value	                | 5509|           R|  Float|
|    Min Y Value 	                | 5510|           R|  Float|
|    Max Y Value	                | 5511|           R|  Float|
|    Min Z Value 	                | 5512|           R|  Float|
|    Max Z Value                	| 5513|           R|  Float|
|    Latitude	                    | 5514|           R| String|
|    Longitude 	                  | 5515|           R| String|
|    Uncertainty 	                | 5516|           R| String|
|    Velocity 	                  | 5517|           R| Opaque|
|    Timestamp                    | 5518|           R|   Time|
|    Min Limit                  	| 5519|           R|  Float|
|    Max Limit 	                  | 5520|           R|  Float|
|    Delay Duration             	| 5521|         R,W|  Float|
|    Clip 	                      | 5522|         R,W| Opaque|
|    Trigger 	                    | 5523|           E| Opaque|
|    Duration 	                  | 5524|         R,W|  Float|
|    Minimum Off-time             | 5525|         R,W|  Float|
|    Mode                         | 5526|         R,W|Integer|
|    Text                   	    | 5527|         R,W| String|
|    X Coordinate 	              | 5528|         R,W|Integer|
|    Y Coordinate 	              | 5529|         R,W|Integer|
|    Clear Display                | 5530|           E|       |
|    Contrast                     | 5531|         R,W|  Float|
|    Increase Input State         | 5532|           R|Boolean|
|    Decrease Input State         | 5533|           R|Boolean|
|    Counter 	                    | 5534|         R,W|Integer|
|    Current Position             | 5536|         R,W|  Float|
|    Transition Time              | 5537|         R,W|  Float|
|    Remaining Time               | 5538|           R|  Float|
|    Up Counter                   | 5541|         R,W|Integer|
|    Down Counter 	              | 5542|         R,W|Integer|
|    Digital State 	              | 5543|           R|Boolean|
|    Cumulative Time 	            | 5544|         R,W|  Float|
|    Max X Coordinate             | 5545|           R|Integer|
|    Max Y Coordinate 	          | 5546|           R|Integer|
|    Multi-state Input 	          | 5547|           R|Integer|
|    Level             	          | 5548|         R,W|  Float|
|    Digital Output State					| 5550|         R,W|Boolean|
|    Digital Output Polarity			| 5551|         R,W|Boolean|
|    Analog Input State					  | 5600|           R|  Float|
|    Min Measured Value           | 5601|           R|  Float|
|    Max Measured Value           | 5602|           R|  Float|
|    Min Range Value              | 5603|           R|  Float|
|    Max Range Value              | 5604|           R|  Float|
|Reset Min and Max Measured Values| 5605|           E| Opaque|
|    Analog Output Current Value  | 5650|         R,W|  Float|
|    Sensor Value	                | 5700|           R|  Float|
|    Sensor Units                 | 5701|           R| String|
|    X Value                      | 5702|           R|  Float|
|    Y Value                      | 5703|           R|  Float|
|    Z Value                      | 5704|           R|  Float|
|    Compass Direction            | 5705|           R|  Float|
|    Colour                       | 5706|         R,W| String|
|    Application Type	            | 5750|         R,W| String|
|    Sensor Type	                | 5751|           R| String|
|    Instantaneous active power   | 5800|           R|  Float|
|    Min Measured active power    | 5801|           R|  Float|
|    Max Measured active power    | 5802|           R|  Float|
|    Min Range active power       | 5803|           R|  Float|
|    Max Range active power       | 5804|           R|  Float|
|    Cumulative active power      | 5805|           R|  Float|
|    Active Power Calibration     | 5806|           W|  Float|
|    Instantaneous reactive power | 5810|           R|  Float|
|    Min Measured reactive power  | 5811|           R|  Float|
|    Max Measured reactive power  | 5812|           R|  Float|
|    Min Range reactive power     | 5813|           R|  Float|
|    Max Range reactive power     | 5814|           R|  Float|
|    Cumulative reactive power    | 5815|           R|  Float|
|    Reactive Power Calibration   | 5816|           W|  Float|
|    Power Factor                 | 5820|           R|  Float|
|    Current Calibration          | 5821|         R,W|  Float|
|    Reset Cumulative energy      | 5822|           E| Opaque|
|    Event Identifier             | 5823|         R,W| String|
|    Start Time                   | 5824|         R,W|  Float|
|    Duration In Min              | 5825|         R,W|  Float|
|    Criticality Level            | 5826|         R,W|Integer|
|    Avg Load Adj Pct             | 5827|         R,W| String|
|    Duty Cycle                   | 5828|         R,W|Integer|
|    On/Off                       | 5850|         R,W|Boolean|
|    Dimmer                       | 5851|         R,W|Integer|
|    On Time                      | 5852|         R,W|Integer|
|    Muti-state Output            | 5853|         R,W| String|
|    Off Time                     | 5854|         R,W|Integer|
|    Set Point Value              | 5900|         R,W|  Float|
|    Busy to Clear delay          | 5903|         R,W|Integer|
|    Clear to Busy delay          | 5904|         R,W|Integer|
|    Bitmap Input                 | 5910|           R|Integer|
|    Bitmap Input Reset           | 5911|           E| Opaque|
|    Element Description          | 5912|         R,W| String|
*/