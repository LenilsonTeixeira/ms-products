package com.reactive.msproducts.functions;

import com.reactive.msproducts.dtos.SearchFiltersDTO;
import com.reactive.msproducts.enums.ServerRequestEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Component
public class ExtractFiltersFunction implements Function<ServerRequest, Mono<SearchFiltersDTO>> {
    @Override
    public Mono<SearchFiltersDTO> apply(final ServerRequest serverRequest) {

        log.debug("Function Execution: {}", serverRequest);

        final HttpHeaders headers = serverRequest.headers().asHttpHeaders();

        final String customerId = headers.getFirst(ServerRequestEnum.HEADER_CUSTOMER_ID.getAttribute());
        final Optional<String>  limit = serverRequest.queryParam(ServerRequestEnum.LIMIT.getAttribute());
        final Optional<String>  offset = serverRequest.queryParam(ServerRequestEnum.OFFSET.getAttribute());


        final SearchFiltersDTO searchFiltersDTO = SearchFiltersDTO
                .builder()
                .customerId(customerId)
                .limit(limit.map(Integer::valueOf)
                        .orElseGet(() -> Integer.valueOf(ServerRequestEnum.LIMIT_DEFAULT_VALUE.getAttribute())))
                .offset(offset.map(Integer::valueOf)
                        .orElseGet(() -> Integer.valueOf(ServerRequestEnum.OFFSET_DEFAULT_VALUE.getAttribute())))
                .build();

        return Mono.just(searchFiltersDTO);
    }
}
