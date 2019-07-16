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
public class OffsetParamHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final String OFFSET_INVALID = String.format("Query param '%s' is not a valid number", ServerRequestEnum.OFFSET.getAttribute());

    private static Mono<ServerResponse> invalidOffsetValue() {
        return ServerResponseUtils.badRequest(new MsProductsException(OFFSET_INVALID));
    }

    @Override
    public Mono<ServerResponse> filter(final ServerRequest request, final HandlerFunction<ServerResponse> handlerFunction) {

        final Optional<String> offset = request.queryParam(ServerRequestEnum.OFFSET.getAttribute());

        if (offset.isPresent()) {
            try {
                Integer.parseInt(offset.get());
            } catch (NumberFormatException e) {
                return invalidOffsetValue();
            }
            if (Integer.valueOf(offset.get()) < 0) {
                return invalidOffsetValue();
            }
        }
        return handlerFunction.handle(request);
    }
}