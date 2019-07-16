package com.reactive.msproducts.filters;

import com.reactive.msproducts.enums.ServerRequestEnum;
import com.reactive.msproducts.utils.ServerResponseUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class DateParamHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final String QUERY_PARAM_LIMIT_INVALID = String
            .format("Query param '%s' is not a valid number", ServerRequestEnum.LIMIT.getAttribute());

    private static Mono<ServerResponse> invalidDate() {
        return ServerResponseUtils.badRequest(new RuntimeException(QUERY_PARAM_LIMIT_INVALID));
    }

    private static Mono<ServerResponse> validateDate( Optional<String> date) {
        if (date.isPresent()) {
            try {
                Integer.parseInt(date.get());
            } catch (NumberFormatException e) {
                return invalidDate();
            }
            if (Integer.valueOf(date.get()) < 0) {
                return invalidDate();
            }
        }
        return null;
    }

    @Override
    public Mono<ServerResponse> filter(final ServerRequest request,
                                       final HandlerFunction<ServerResponse> handlerFunction) {

        final Optional<String> beginDate = request.queryParam(ServerRequestEnum.START_DATE.getAttribute());
        final Optional<String> endDate = request.queryParam(ServerRequestEnum.END_DATE.getAttribute());

        validateDate(beginDate);
        validateDate(endDate);

        return handlerFunction.handle(request);
    }
}
