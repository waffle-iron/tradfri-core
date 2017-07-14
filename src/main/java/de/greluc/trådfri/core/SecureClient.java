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

package de.greluc.trådfri.core;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.ScandiumLogger;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.pskstore.StaticPskStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.logging.Level;

/**
 * This class is the implementation of an secure CoAP Client.
 * It's based on the SecureClient Example from the Californium libraries.
 *
 * @author Lucas Greuloch (greluc)
 * @version 1.0.0-SNAPSHOT 13.07.2017
 */
public class SecureClient {

    private static final String TRUST_STORE_PASSWORD = "rootPass";
    private static final String KEY_STORE_PASSWORD = "endPass";
    private static final String KEY_STORE_LOCATION = "certs/keyStore.jks";
    private static final String TRUST_STORE_LOCATION = "certs/trustStore.jks";

    static {
        ScandiumLogger.initialize();
        ScandiumLogger.setLevel(Level.FINE);
    }

    private final String SERVER_URI;

    private DTLSConnector dtlsConnector;

    public SecureClient(String uri) {
        this.SERVER_URI = uri;

        try {
            // load key store
            KeyStore keyStore = KeyStore.getInstance("JKS");
            InputStream in = getClass().getClassLoader().getResourceAsStream(KEY_STORE_LOCATION);
            keyStore.load(in, KEY_STORE_PASSWORD.toCharArray());
            in.close();

            // load trust store
            KeyStore trustStore = KeyStore.getInstance("JKS");
            in = getClass().getClassLoader().getResourceAsStream(TRUST_STORE_LOCATION);
            trustStore.load(in, TRUST_STORE_PASSWORD.toCharArray());
            in.close();

            // You can load multiple certificates if needed
            Certificate[] trustedCertificates = new Certificate[1];
            trustedCertificates[0] = trustStore.getCertificate("root");

            DtlsConnectorConfig.Builder builder = new DtlsConnectorConfig.Builder();
            builder.setPskStore(new StaticPskStore("Client_identity", "secretPSK".getBytes()));
            builder.setIdentity((PrivateKey) keyStore.getKey("client", KEY_STORE_PASSWORD.toCharArray()),
                    keyStore.getCertificateChain("client"), true);
            builder.setTrustStore(trustedCertificates);
            dtlsConnector = new DTLSConnector(builder.build());

        } catch (GeneralSecurityException | IOException e) {
            System.err.println("Could not load the keystore");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SecureClient client = new SecureClient("coaps://localhost/secure");
        client.test();

        synchronized (SecureClient.class) {
            SecureClient.class.wait();
        }
    }

    private void test() {

        CoapResponse response = null;
        try {
            URI uri = new URI(SERVER_URI);

            CoapClient client = new CoapClient(uri);
            client.setEndpoint(new CoapEndpoint(dtlsConnector, NetworkConfig.getStandard()));
            response = client.get();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI: " + e.getMessage());
            System.exit(-1);
        }

        if (response != null) {

            System.out.println(response.getCode());
            System.out.println(response.getOptions());
            System.out.println(response.getResponseText());

            System.out.println("\nADVANCED\n");
            System.out.println(Utils.prettyPrint(response));

        } else {
            System.out.println("No response received.");
        }
    }
}
