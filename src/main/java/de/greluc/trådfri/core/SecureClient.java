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
import org.eclipse.californium.core.Utils;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.logging.Level;

import static de.greluc.trådfri.core.Constants.*;

/**
 * This class implements a secure CoAP client.
 * Console-Client fom Californium-Tools was used as a blueprint.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
public class SecureClient {

    // for coaps
    private static Endpoint dtlsEndpoint;

    static {
        CaliforniumLogger.initialize();
        CaliforniumLogger.setLevel(Level.WARNING);

        ScandiumLogger.initialize();
        ScandiumLogger.setLevel(Level.FINER);
    }

    // the trust store file used for DTLS server authentication
    private final String TRUST_STORE_LOCATION = "certs/trustStore.jks";
    private final String TRUST_STORE_PASSWORD = "rootPass";
    private final String KEY_STORE_LOCATION = "certs/keyStore.jks";
    private final String KEY_STORE_PASSWORD = "endPass";
    // exit codes for runtime errors
    private final int ERR_MISSING_METHOD = 1;
    private final int ERR_UNKNOWN_METHOD = 2;
    private final int ERR_MISSING_URI = 3;
    private final int ERR_BAD_URI = 4;
    private final int ERR_REQUEST_FAILED = 5;
    private final int ERR_RESPONSE_FAILED = 6;
    // Succes exit code
    private final int SUCCESS = 0;
    // initialize parameters
    private String method = null;
    private URI uri = null;
    private String payload = "";
    private Gateway gateway;
    private boolean loop = false;

    public SecureClient(Boolean loop, Gateway gateway, String psk) throws IOException, GeneralSecurityException //TODO get secure input of psk over the api from outside
    {
        this.loop = loop;
        this.gateway = gateway;

        // load trust store
        KeyStore trustStore = KeyStore.getInstance("JKS");
        InputStream inTrust = new FileInputStream(TRUST_STORE_LOCATION);
        trustStore.load(inTrust, TRUST_STORE_PASSWORD.toCharArray());
        // load multiple certificates if needed
        Certificate[] trustedCertificates = new Certificate[1];
        trustedCertificates[0] = trustStore.getCertificate("root");

        DtlsConnectorConfig.Builder builder = new DtlsConnectorConfig.Builder();

        builder.setTrustStore(trustedCertificates);

        InMemoryPskStore pskStore = new InMemoryPskStore();
        pskStore.addKnownPeer(gateway.getInetAddress(), Constants.PRESET_CLIENT_IDENTITY, psk.getBytes()); //TODO get secure input of psk over the api from outside
        builder.setPskStore(pskStore);
        builder.setSupportedCipherSuites(new CipherSuite[]{CipherSuite.TLS_PSK_WITH_AES_128_CCM_8});

        DTLSConnector dtlsconnector = new DTLSConnector(builder.build(), null);

        dtlsEndpoint = new CoapEndpoint(dtlsconnector, NetworkConfig.getStandard());
        dtlsEndpoint.start();
        EndpointManager.getEndpointManager().setDefaultEndpoint(dtlsEndpoint);
    }

    private int sendMessage(String uriAsString, CoAPMessage message) {
        this.method = message.getMethod();

        try {
            uri = new URI(uriAsString);
        } catch (URISyntaxException e) {
            System.err.println("Failed to parse URI: " + e.getMessage());
            System.exit(ERR_BAD_URI);
        }

        payload = message.getPayload();

        // check if mandatory parameters specified
        if (method == null) {
            System.err.println("Method not specified");
            System.exit(ERR_MISSING_METHOD);
        }
        if (uri == null) {
            System.err.println("URI not specified");
            System.exit(ERR_MISSING_URI);
        }

        // create request according to specified method
        Request request = newRequest(method);

        // set request URI
        if (method.equals("DISCOVER") && (uri.getPath() == null || uri.getPath().isEmpty() || uri.getPath().equals("/"))) {
            // add discovery resource path to URI
            try {
                uri = new URI(uri.getScheme(), uri.getAuthority(), ATTR_DISCOVER_ALL, uri.getQuery());

            } catch (URISyntaxException e) {
                System.err.println("Failed to parse URI: " + e.getMessage());
                System.exit(ERR_BAD_URI);
            }
        }

        request.setURI(uri);
        request.setPayload(payload);
        request.getOptions().setContentFormat(MediaTypeRegistry.TEXT_PLAIN);

        // execute request
        try {
            request.send();

            // loop for receiving multiple responses
            do {

                // receive response
                Response response = null;
                try {
                    response = request.waitForResponse();
                } catch (InterruptedException e) {
                    System.err.println("Failed to receive response: " + e.getMessage());
                    System.exit(ERR_RESPONSE_FAILED);
                }

                // output response

                if (response != null) {

                    System.out.println(Utils.prettyPrint(response));
                    System.out.println("Time elapsed (ms): " + response.getRTT());

                    // check of response contains resources
                    if (response.getOptions().isContentFormat(MediaTypeRegistry.APPLICATION_LINK_FORMAT)) {

                        String linkFormat = response.getPayloadString();

                        // output discovered resources
                        System.out.println("\nDiscovered resources:");
                        System.out.println(linkFormat);

                    } else {
                        // check if link format was expected by client
                        if (method.equals("DISCOVER")) {
                            System.out.println("Server error: Link format not specified");
                        }
                    }

                } else {
                    // no response received
                    System.err.println("Request timed out");
                    break;
                }

            } while (loop);

            return SUCCESS;
        } catch (Exception e) {
            System.err.println("Failed to execute request: " + e.getMessage());
            return ERR_REQUEST_FAILED;
        }
    }

    /*
     * Instantiates a new request based on a string describing a method.
     *
     * @return A new request object, or null if method not recognized
     */
    private Request newRequest(String method) {
        if (method.equals(METHOD_GET)) {
            return Request.newGet();
        } else if (method.equals(METHOD_POST)) {
            return Request.newPost();
        } else if (method.equals(METHOD_PUT)) {
            return Request.newPut();
        } else if (method.equals(METHOD_DELETE)) {
            return Request.newDelete();
        } else if (method.equals(METHOD_DISCOVER)) {
            return Request.newGet();
        } else if (method.equals(METHOD_OBSERVE)) {
            Request request = Request.newGet();
            request.setObserve();
            loop = true;
            return request;
        } else {
            System.err.println("Unknown method: " + method);
            System.exit(ERR_UNKNOWN_METHOD);
            return null;
        }
    }

}
