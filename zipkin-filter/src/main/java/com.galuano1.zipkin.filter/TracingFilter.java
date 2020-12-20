package com.galuano1.zipkin.filter;

import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;

/**
 * Filter class to inject into Servlet containers
 */
public class TracingFilter implements Filter {
    private Reporter reporter = null;
    private String serviceName = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        String zipkinUrl = System.getenv("ZIPKIN_URL");
        reporter = AsyncReporter.create(URLConnectionSender.create(zipkinUrl + "/api/v2/spans"));
        serviceName = System.getenv("SERVICE_NAME");

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Doing this for HTTP Servlets only, others have to be handled differently
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            String traceId = httpReq.getHeader(ZipkinConstants.TRACE_ID) != null ? httpReq.getHeader(ZipkinConstants.TRACE_ID) : getGeneratedId();
            String parentSpanId = httpReq.getHeader(ZipkinConstants.SPAN_ID);
            String method = httpReq.getMethod();
            String uri = httpReq.getRequestURI();
            String spanId = getGeneratedId();
            long startTimeStamp = System.currentTimeMillis()*1000;
            long startTimeNanos = System.nanoTime();
            httpReq.setAttribute(ZipkinConstants.TRACE_ID, traceId);
            httpReq.setAttribute(ZipkinConstants.SPAN_ID, spanId);
            httpReq.setAttribute(ZipkinConstants.PARENT_SPAN_ID, parentSpanId);


            filterChain.doFilter(servletRequest, servletResponse);
            long duration = (System.nanoTime() - startTimeNanos)/1000;
            Span span = Span.newBuilder()
                    .id(spanId)
                    .traceId(traceId)
                    .parentId(parentSpanId)
                    .timestamp(startTimeStamp)
                    .duration(duration)
                    .name(method + " " + uri)
                    .kind(Span.Kind.SERVER)
                    .localEndpoint(Endpoint.newBuilder().serviceName(serviceName).ip(httpReq.getLocalAddr()).port(httpReq.getLocalPort()).build())
                    .remoteEndpoint(Endpoint.newBuilder().ip(httpReq.getRemoteAddr()).port(httpReq.getRemotePort()).build())
                    .putTag("http.method", method)
                    .putTag("http.path", uri)
                    .build();
            reporter.report(span);




        }
    }

    public void destroy() {
        // Nothing to do
    }

    private String getGeneratedId() {
        return Long.toHexString(new Random().nextLong());
    }
}
