package org.buffalo.coders.aws.lambda.common;

/*-
 * #%L
 * common
 * %%
 * Copyright (C) 2019 Buffalo Coders
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamLambdaHandler implements RequestStreamHandler {

    private static final Logger LOG = LoggerFactory.getLogger(StreamLambdaHandler.class);

    private static final String DEFAULT_PACKAGES = "";

    private final ResourceConfig application;

    private final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    public StreamLambdaHandler() {
        this(DEFAULT_PACKAGES);
    }

    public StreamLambdaHandler(String packages) {
        super();
        application = new ResourceConfig().packages(packages).register(JacksonFeature.class);
        handler = JerseyLambdaContainerHandler.getAwsProxyHandler(application);
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LOG.info("APPLICATION: " + application);
        LOG.info("HANDLER:     " + handler);
        LOG.info("INPUT:       " + inputStream);
        LOG.info("OUTPUT:      " + outputStream);
        LOG.info("CONTEXT:     " + context);

        handler.proxyStream(inputStream, outputStream, context);
    }

}
