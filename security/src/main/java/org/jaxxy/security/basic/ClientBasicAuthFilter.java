/*
 * Copyright (c) 2018 The Jaxxy Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jaxxy.security.basic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ClientBasicAuthFilter implements ClientRequestFilter {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final String HEADER_FORMAT = "Basic %s";
    private final String headerValue;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a {@link ClientRequestFilter} which adds a basic authorization header to each request using the UTF-8
     * charset.
     *
     * @param username the username
     * @param password the password
     */
    public ClientBasicAuthFilter(String username, String password) {
        this(username, password, StandardCharsets.UTF_8);
    }

    /**
     * Creates a {@link ClientRequestFilter} which adds a basic authorization header to each request.
     *
     * @param username the username
     * @param password the password
     * @param charset  the charset
     */
    public ClientBasicAuthFilter(String username, String password, Charset charset) {
        this.headerValue = String.format(HEADER_FORMAT, Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes(charset)));
    }

//----------------------------------------------------------------------------------------------------------------------
// ClientRequestFilter Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void filter(ClientRequestContext request) throws IOException {
        request.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, headerValue);
    }
}
