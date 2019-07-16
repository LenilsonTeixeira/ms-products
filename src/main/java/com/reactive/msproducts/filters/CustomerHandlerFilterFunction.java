package com.reactive.msproducts.filters;

import com.reactive.msproducts.enums.ServerRequestEnum;
import com.reactive.msproducts.utils.ServerResponseUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final String HEADER_CUSTOMER_ID_REQUIRED = String.format("Header '%s' is required", ServerRequestEnum.HEADER_CUSTOMER_ID.getAttribute());

    private static boolean hasCustomerIdHeader(final ServerRequest request) {
        return StringUtils.isNotBlank(request.headers().asHttpHeaders().getFirst(ServerRequestEnum.HEADER_CUSTOMER_ID.getAttribute()));
    }

    private static Mono<ServerResponse> noCustomerIdHeaderResponse() {
        return ServerResponseUtils.badRequest(new RuntimeException(HEADER_CUSTOMER_ID_REQUIRED));
    }

    @Override
    public Mono<ServerResponse> filter(final ServerRequest request, final HandlerFunction<ServerResponse> handlerFunction) {
        return hasCustomerIdHeader(request) ? handlerFunction.handle(request) : noCustomerIdHeaderResponse();
    }
}
