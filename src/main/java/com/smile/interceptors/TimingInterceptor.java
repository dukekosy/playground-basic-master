package com.smile.interceptors;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;
import com.smile.common.SmileConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * TimingInterceptor class used to intercept the request and response to time api calls
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
@Interceptor
public class TimingInterceptor implements IClientInterceptor {

    StopWatch stopWatch;
    static volatile List<Long> responseTimes = new ArrayList<>();

    /*
     * This is used to interrupt a request to the Patient endpoint and start a stopWatch
     *
     * @param iHttpRequest request to intercept
     *
     */
    @Override
    @Hook(Pointcut.CLIENT_REQUEST)
    public void interceptRequest(IHttpRequest iHttpRequest) {
        if (iHttpRequest.getUri().contains(SmileConstants.END_POINT)) {
            stopWatch = new StopWatch();
        }
    }

    /*
     * This is used to interrupt a response from the Patient endpoint, collect last 20 reading and calculate the average.
     *
     * @param iHttpResponse response to intercept
     *
     */
    @Override
    @Hook(Pointcut.CLIENT_RESPONSE)
    public void interceptResponse(IHttpResponse iHttpResponse) {
        if (stopWatch != null) {
            long time = stopWatch.getMillis();
            responseTimes.add(time);
            if (responseTimes.size() == 20) {
                System.out.println("Average Response time: " + calculateAverageTime());
                responseTimes = new ArrayList<>(20);
            }
        }
    }

    /*
     * Calculate the average of saved measurements.
     *
     * @param iHttpResponse response to intercept
     *
     */
    private long calculateAverageTime() {
        return responseTimes.stream().mapToInt(Long::intValue).sum() / responseTimes.size();
    }
}
