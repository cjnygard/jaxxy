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

package org.jaxxy.security.token;

import javax.ws.rs.core.HttpHeaders;

import org.jaxxy.test.JaxrsClientConfig;
import org.jaxxy.test.JaxrsTestCase;
import org.jaxxy.test.hello.DefaultEchoHeaderResource;
import org.jaxxy.test.hello.EchoHeaderResource;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTokenAuthFilterTest extends JaxrsTestCase<EchoHeaderResource> {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    protected void configureClient(JaxrsClientConfig config) {
        config.withProvider(new ClientTokenAuthFilter(() -> "FooBarBaz"));
    }

    @Override
    protected EchoHeaderResource createServiceObject() {
        return new DefaultEchoHeaderResource(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void testFilter(){
        assertThat(clientProxy().echo()).isEqualTo("Bearer FooBarBaz");
    }
}