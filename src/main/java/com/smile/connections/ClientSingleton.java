package com.smile.connections;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import com.smile.interceptors.TimingInterceptor;

/**
 * ClientSingleton class used to generate a single client
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public enum ClientSingleton {

    INSTANCE;

    private static final String SERVER_URL = "http://test.hapifhir.io/baseR4";

    private final FhirContext fhirContext = FhirContext.forR4();
    private final IGenericClient client = fhirContext.newRestfulGenericClient(SERVER_URL);

    ClientSingleton() {
        client.registerInterceptor(new LoggingInterceptor(false));
        client.registerInterceptor(new TimingInterceptor());
    }

    public IGenericClient getClient() {
        return client;
    }

}
