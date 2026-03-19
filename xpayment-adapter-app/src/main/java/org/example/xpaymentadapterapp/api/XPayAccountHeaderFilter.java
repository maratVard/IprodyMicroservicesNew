package org.example.xpaymentadapterapp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.xpaymentadapterapp.api.generated.model.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class XPayAccountHeaderFilter extends OncePerRequestFilter {

    private static final String X_PAY_ACCOUNT_HEADER = "X-Pay-Account";
    private static final String PAYMENT_AGENT_IPRODY_API_TOKEN = "paymentAgentIprodyApiToken";

    private final ObjectMapper objectMapper;

    public XPayAccountHeaderFilter(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var xpayAccountHeader = request.getHeader(X_PAY_ACCOUNT_HEADER);
        if (xpayAccountHeader == null || !xpayAccountHeader.equals(PAYMENT_AGENT_IPRODY_API_TOKEN)) {
            var errorResponse = new ErrorResponse();
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage("Whether missing or incorrect required header: X-Pay-Account");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }
}