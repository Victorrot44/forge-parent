package io.github.victorrot44.forge.web.starter.logging;

import io.github.victorrot44.forge.web.autoconfigure.ForgeWebProperties;
import io.github.victorrot44.forge.web.core.sanitize.JsonSanitizer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ForgeLoggingFilter extends OncePerRequestFilter {

    private static final String LOG_TEMPLATE = """
            Request[Method = \"{}\", URI = \"{}\", RequestId = \"{}\", Body = {}] - Response[Status = {}, Body = {}, Duration = {}]
            """;

    private final ForgeWebProperties.Logging logging;
    private final ForgeWebProperties.Context context;
    private final JsonSanitizer jsonSanitizer;
    private boolean enabled;

    public ForgeLoggingFilter(ForgeWebProperties properties, JsonSanitizer jsonSanitizer) {
        this.enabled = properties.enabled();
        this.logging = properties.logging();
        this.context = properties.context();
        this.jsonSanitizer = jsonSanitizer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!enabled || !logging.enabled() || isExcludedUri(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, 0);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String requestBody = processRequestBody(requestWrapper);
            String responseBody = processResponseBody(responseWrapper);
            log.info(
                    LOG_TEMPLATE,
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getHeader(context.requestIdHeader()),
                    requestBody,
                    response.getStatus(),
                    responseBody,
                    duration
            );
            responseWrapper.copyBodyToResponse();
        }
    }

    private boolean isExcludedUri(String uri) {
        return logging.excludedUris() != null
                && logging.excludedUris().stream()
                .anyMatch(pattern -> matches(pattern, uri));
    }

    private boolean matches(String pattern, String uri) {
        return pattern.equals(uri);
    }

    private String processRequestBody(ContentCachingRequestWrapper request) {
        byte[] body = request.getContentAsByteArray();
        if (body == null ||  body.length == 0) {
            return "[empty]";
        }
        return processBody(
                body,
                request.getCharacterEncoding(),
                request.getContentType()
        );
    }

    private String processResponseBody(ContentCachingResponseWrapper response) {
        byte[] body = response.getContentAsByteArray();
        if (body == null || body.length == 0) {
            return "[empty]";
        }
        return processBody(
                body,
                response.getCharacterEncoding(),
                response.getContentType()
        );
    }

    private String processBody(byte[] body, String encoding, String contentType) {
        if (isMultipart(contentType)) {
            return "[multipart]";
        }
        if (isBinary(contentType)) {
            return "[binary]";
        }
        String value = toString(body, encoding);
        if (isJson(contentType)) {
            return jsonSanitizer.sanitize(value);
        }
        if (isText(contentType)) {
            return truncate(value);
        }
        return "[unknown]";
    }

    private boolean isJson(String contentType) {
        return contentType != null && contentType.toLowerCase().startsWith("application/json") || contentType.toLowerCase().endsWith("json");
    }

    private boolean isText(String contentType) {
        return contentType != null && contentType.toLowerCase().startsWith("text/");
    }

    private boolean isMultipart(String contentType) {
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }

    private boolean isBinary(String contentType) {
        return contentType != null && !isJson(contentType) && !isText(contentType) && !isMultipart(contentType);
    }

    private String truncate(String value) {
        if (value.length() <= logging.maxTextBodyLength()) {
            return value;
        }
        return value.substring(0, logging.maxTextBodyLength()) + "[...Truncado]";
    }

    private String toString(byte[] body, String encoding) {
        Charset charset = encoding != null ? Charset.forName(encoding) : StandardCharsets.UTF_8;
        return new String(body, charset);
    }

}
