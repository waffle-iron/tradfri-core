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

package de.greluc.trådfri.core;

import org.eclipse.californium.core.CaliforniumLogger;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.ScandiumLogger;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.cipher.CipherSuite;
import org.eclipse.californium.scandium.dtls.pskstore.InMemoryPskStore;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.logging.Level;

import static de.greluc.trådfri.core.Constants.*;

/**
 * This class implements a secure CoAP client.
 * Console-Client fom Californium-Tools was used as a blueprint.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
public class Client
{

    static
    {
        CaliforniumLogger.initialize();
        CaliforniumLogger.setLevel(Level.WARNING);

        ScandiumLogger.initialize();
        ScandiumLogger.setLevel(Level.ALL);
    }

    // for coaps
    private static Endpoint dtlsEndpoint;

    // exit codes for runtime errors
    // initialize parameters
    private URI uri;
    private Gateway gateway;

    public Client(Gateway gateway, String psk) throws IOException, GeneralSecurityException //TODO get secure input of psk over the api from outside
    {
        this.gateway = gateway;

        DtlsConnectorConfig.Builder builder = new DtlsConnectorConfig.Builder();

        InMemoryPskStore pskStore = new InMemoryPskStore();
        pskStore.addKnownPeer(gateway.getInetAddress(), PRESET_CLIENT_IDENTITY, psk.getBytes()); //TODO get secure input of psk over the api from outside
        builder.setPskStore(pskStore);
        builder.setSupportedCipherSuites(new CipherSuite[]{CipherSuite.TLS_PSK_WITH_AES_128_CCM_8});

        DTLSConnector dtlsconnector = new DTLSConnector(builder.build(), null);

        dtlsEndpoint = new CoapEndpoint(dtlsconnector, NetworkConfig.getStandard());
        dtlsEndpoint.start();
        EndpointManager.getEndpointManager().setDefaultEndpoint(dtlsEndpoint);
    }

    LinkedList<Response> sendMessage(String path, String method, String payload, Boolean loop)
    {
        try
        {
            uri = new URI("coaps://" + gateway.getInetAddress().getHostString() + ":" + gateway.getInetAddress().getPort() + path);
        }
        catch(URISyntaxException e)
        {
            System.err.println("Failed to parse URI: " + e.getMessage());
        }

        // create request according to specified method
        Request request = newRequest(method);

        request.setURI(uri);
        request.setPayload(payload);
        request.getOptions().setContentFormat(MediaTypeRegistry.TEXT_PLAIN);

        // execute request
        LinkedList<Response> listResponse = new LinkedList<>();
        try
        {
            request.send(dtlsEndpoint);

            // loop for receiving multiple responses
            do
            {
                // receive response
                Response response = null;
                try
                {
                    response = request.waitForResponse();
                }
                catch(InterruptedException e)
                {
                    System.err.println("Failed to receive response: " + e.getMessage());
                }

                // output response
                if(response != null)
                {
                    listResponse.add(response);
                }
                else
                {
                    // no response received
                    System.err.println("Request timed out");
                    break;
                }
            }
            while(loop);
        }
        catch(Exception e)
        {
            System.err.println("Failed to execute request: " + e.getMessage());
        }
        return listResponse;
    }

    /*
     * Instantiates a new request based on a string describing a method.
     *
     * @return A new request object, or null if method not recognized
     */
    private Request newRequest(String method)
    {
        if(method.equals(METHOD_GET))
        {
            return Request.newGet();
        }
        else if(method.equals(METHOD_POST))
        {
            return Request.newPost();
        }
        else if(method.equals(METHOD_PUT))
        {
            return Request.newPut();
        }
        else if(method.equals(METHOD_DELETE))
        {
            return Request.newDelete();
        }
        else if(method.equals(METHOD_DISCOVER))
        {
            return Request.newGet();
        }
        else if(method.equals(METHOD_OBSERVE))
        {
            Request request = Request.newGet();
            request.setObserve();
            return request;
        }
        else
        {
            System.err.println("Unknown method: " + method);
            return null;
        }
    }
}