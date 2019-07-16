package com.reactive.msproducts.filters;

import com.reactive.msproducts.enums.ServerRequestEnum;
import com.reactive.msproducts.exceptions.MsProductsException;
import com.reactive.msproducts.utils.ServerResponseUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class LimitParamHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final String QUERY_PARAM_LIMIT_INVALID = String.format("Query param '%s' is not a valid number", ServerRequestEnum.LIMIT.getAttribute());

    private static Mono<ServerResponse> invalidLimitValue() {
        return ServerResponseUtils.badRequest(new MsProductsException(QUERY_PARAM_LIMIT_INVALID));
    }

    @Override
    public Mono<ServerResponse> filter(final ServerRequest request, final HandlerFunction<ServerResponse> handlerFunction) {

        final Optional<String> limit = request.queryParam(ServerRequestEnum.LIMIT.getAttribute());

        if (limit.isPresent()) {
            try {
                Integer.parseInt(limit.get());
            } catch (NumberFormatException e) {
                return invalidLimitValue();
            }
            if (Integer.valueOf(limit.get()) < 0) {
                return invalidLimitValue();
            }
        }
        return handlerFunction.handle(request);
    }
}