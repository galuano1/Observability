package com.galuano1.frontend.controller;

import com.galuano1.zipkin.filter.ZipkinConstants;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class DemoController {

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public String put(HttpServletRequest request) throws IOException {
        String traceId = request.getAttribute(ZipkinConstants.TRACE_ID).toString();
        String spanId = request.getAttribute(ZipkinConstants.SPAN_ID).toString();
        String parentSpanId = request.getAttribute(ZipkinConstants.PARENT_SPAN_ID) != null ?
                request.getAttribute(ZipkinConstants.PARENT_SPAN_ID).toString() : null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(System.getEnv("WEBTEST_URL"));
        httpPut.addHeader(ZipkinConstants.TRACE_ID, traceId);
        httpPut.addHeader(ZipkinConstants.SPAN_ID, spanId);
        httpPut.addHeader(ZipkinConstants.PARENT_SPAN_ID, parentSpanId);
        httpPut.setEntity(new StringEntity(""));

        String response = httpclient.execute(httpPut, getRespHandler());
        return "Response from backend: " + response;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get(HttpServletRequest request) throws IOException {
        String traceId = request.getAttribute(ZipkinConstants.TRACE_ID).toString();
        String spanId = request.getAttribute(ZipkinConstants.SPAN_ID).toString();
        String parentSpanId = request.getAttribute(ZipkinConstants.PARENT_SPAN_ID) != null ?
                request.getAttribute(ZipkinConstants.PARENT_SPAN_ID).toString() : null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://webtest:8080/");
        httpGet.addHeader(ZipkinConstants.TRACE_ID, traceId);
        httpGet.addHeader(ZipkinConstants.SPAN_ID, spanId);
        httpGet.addHeader(ZipkinConstants.PARENT_SPAN_ID, parentSpanId);
        String response = httpclient.execute(httpGet, getRespHandler());
        return "Response from backend: " + response;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String post(HttpServletRequest request) throws IOException {
        String traceId = request.getAttribute(ZipkinConstants.TRACE_ID).toString();
        String spanId = request.getAttribute(ZipkinConstants.SPAN_ID).toString();
        String parentSpanId = request.getAttribute(ZipkinConstants.PARENT_SPAN_ID) != null ?
                request.getAttribute(ZipkinConstants.PARENT_SPAN_ID).toString() : null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://webtest:8080/");
        httpPost.addHeader(ZipkinConstants.TRACE_ID, traceId);
        httpPost.addHeader(ZipkinConstants.SPAN_ID, spanId);
        httpPost.addHeader(ZipkinConstants.PARENT_SPAN_ID, parentSpanId);
        httpPost.setEntity(new StringEntity(""));
        String response = httpclient.execute(httpPost, getRespHandler());
        return "Response from backend: " + response;
    }

    private ResponseHandler<String> getRespHandler() {
        return (response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        });
    }
}
