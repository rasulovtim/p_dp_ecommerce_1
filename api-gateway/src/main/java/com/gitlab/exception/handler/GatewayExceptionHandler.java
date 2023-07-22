package com.gitlab.exception.handler;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Map;
import java.util.Objects;


@Component
public class GatewayExceptionHandler extends AbstractErrorWebExceptionHandler {
    public GatewayExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.values());
        Map<String, Object> errorAttributes = getErrorAttributes(request, errorAttributeOptions);

        Throwable error = getError(request);
        final String message;

        if (error instanceof ConnectException) {
            return ServerResponse.status(HttpStatus.NOT_FOUND).body(BodyInserters.fromValue(getError(request).getMessage()));
        } else {
            message = (String) errorAttributes.get("message");
        }

        Map<String, Object> responseBody = Map.of("message", message);

        int statusCode = Integer.parseInt(Objects.toString(errorAttributes.get("status")));
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(BodyInserters.fromValue(responseBody));
    }
}
