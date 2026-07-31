package io.github.victorrot44.forge.web.starter.context;

import io.github.victorrot44.forge.web.autoconfigure.ForgeWebProperties;
import io.github.victorrot44.forge.web.core.context.ForgeContext;
import io.github.victorrot44.forge.web.core.context.ForgeContextHolder;
import io.github.victorrot44.forge.web.core.context.RequestIdResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ForgeContextFilter extends OncePerRequestFilter {

    private final RequestIdResolver requestIdResolver;
    private final ForgeWebProperties properties;

    public ForgeContextFilter(RequestIdResolver requestIdResolver, ForgeWebProperties properties) {
        this.requestIdResolver = requestIdResolver;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestIdHeader = properties.context().requestIdHeader();
        String requestId = request.getHeader(requestIdHeader);
        String resolvedRequestId = requestIdResolver.resolve(requestId);
        response.setHeader(requestIdHeader, resolvedRequestId);
        ForgeContext context = ForgeContext.of(resolvedRequestId);
        ForgeContextHolder.setContext(context);
        try {
            filterChain.doFilter(request, response);
        } finally {
            ForgeContextHolder.clearContext();
        }
    }

}
