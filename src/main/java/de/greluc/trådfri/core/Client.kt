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

import de.greluc.trådfri.core.Constants.METHOD_DELETE
import de.greluc.trådfri.core.Constants.METHOD_DISCOVER
import de.greluc.trådfri.core.Constants.METHOD_GET
import de.greluc.trådfri.core.Constants.METHOD_OBSERVE
import de.greluc.trådfri.core.Constants.METHOD_POST
import de.greluc.trådfri.core.Constants.METHOD_PUT
import de.greluc.trådfri.core.Constants.PRESET_CLIENT_IDENTITY
import org.eclipse.californium.core.CaliforniumLogger
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.coap.Request
import org.eclipse.californium.core.coap.Response
import org.eclipse.californium.core.network.CoapEndpoint
import org.eclipse.californium.core.network.Endpoint
import org.eclipse.californium.core.network.EndpointManager
import org.eclipse.californium.core.network.config.NetworkConfig
import org.eclipse.californium.scandium.DTLSConnector
import org.eclipse.californium.scandium.ScandiumLogger
import org.eclipse.californium.scandium.config.DtlsConnectorConfig
import org.eclipse.californium.scandium.dtls.cipher.CipherSuite
import org.eclipse.californium.scandium.dtls.pskstore.InMemoryPskStore
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.security.GeneralSecurityException
import java.util.*
import java.util.logging.Level

/**
 * This class implements a secure CoAP client.
 * Console-Client fom Californium-Tools was used as a blueprint.

 * @author Lucas Greuloch (greluc)
 * *
 * @version 1.0.0-SNAPSHOT 19.07.2017
 */
class Client @Throws(IOException::class, GeneralSecurityException::class) //TODO get secure input of psk over the api from outside
constructor(private val gateway: Gateway, psk: CharArray) {

    // initialize parameters
    private var uri: URI? = null

    init {
        val builder = DtlsConnectorConfig.Builder()

        val pskStore = InMemoryPskStore()
        pskStore.addKnownPeer(gateway.getInetAddress(), PRESET_CLIENT_IDENTITY, psk.toString().toByteArray()) //TODO get secure input of psk over the api from outside
        builder.setPskStore(pskStore)
        builder.setSupportedCipherSuites(arrayOf(CipherSuite.TLS_PSK_WITH_AES_128_CCM_8))

        val dtlsconnector = DTLSConnector(builder.build(), null)

        dtlsEndpoint = CoapEndpoint(dtlsconnector, NetworkConfig.getStandard())
        dtlsEndpoint.start()
        EndpointManager.getEndpointManager().defaultEndpoint = dtlsEndpoint
    }

    internal fun sendMessage(path: String, method: String, payload: String, loop: Boolean): LinkedList<Response> {
        try {
            uri = URI("coaps://" + gateway.getInetAddress().hostString + ":" + gateway.getInetAddress().port + path)
        } catch (e: URISyntaxException) {
            System.err.println("Failed to parse URI: " + e.message)
        }

        // create request according to specified method
        val request = newRequest(method)

        request!!.setURI(uri!!)
        request.setPayload(payload)
        request.options.contentFormat = MediaTypeRegistry.TEXT_PLAIN

        // execute request
        val listResponse = LinkedList<Response>()
        try {
            request.send(dtlsEndpoint)

            // loop for receiving multiple responses
            do {
                // receive response
                var response: Response? = null
                try {
                    response = request.waitForResponse()
                } catch (e: InterruptedException) {
                    System.err.println("Failed to receive response: " + e.message)
                }

                // output response
                if (response != null) {
                    listResponse.add(response)
                } else {
                    // no response received
                    System.err.println("Request timed out")
                    break
                }
            } while (loop)
        } catch (e: Exception) {
            System.err.println("Failed to execute request: " + e.message)
        }

        return listResponse
    }

    /*
     * Instantiates a new request based on a string describing a method.
     *
     * @return A new request object, or null if method not recognized
     */
    private fun newRequest(method: String): Request? {
        if (method == METHOD_GET) {
            return Request.newGet()
        } else if (method == METHOD_POST) {
            return Request.newPost()
        } else if (method == METHOD_PUT) {
            return Request.newPut()
        } else if (method == METHOD_DELETE) {
            return Request.newDelete()
        } else if (method == METHOD_DISCOVER) {
            return Request.newGet()
        } else if (method == METHOD_OBSERVE) {
            val request = Request.newGet()
            request.setObserve()
            return request
        } else {
            System.err.println("Unknown method: " + method)
            return null
        }
    }

    companion object {

        init {
            CaliforniumLogger.initialize()
            CaliforniumLogger.setLevel(Level.WARNING)

            ScandiumLogger.initialize()
            ScandiumLogger.setLevel(Level.ALL)
        }

        // for coaps
        private lateinit var dtlsEndpoint: Endpoint
    }
}