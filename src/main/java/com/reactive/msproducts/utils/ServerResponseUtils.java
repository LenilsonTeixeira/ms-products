package com.reactive.msproducts.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class ServerResponseUtils {

    private ServerResponseUtils() {}

    private static BodyInserter<ExceptionDetail, ReactiveHttpOutputMessage> reactiveException(final HttpStatus httpStatus, final Throwable throwable) {
        return BodyInserters.fromObject(new ExceptionDetail(httpStatus, throwable));
    }

    public static Mono<ServerResponse> throwException(final Throwable throwable, final HttpStatus httpStatus) {
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(reactiveException(httpStatus, throwable));
    }

    public static Mono<ServerResponse> ok(final Object body) {
        return ServerResponse.ok().body(BodyInserters.fromObject(body));
    }

    public static Mono<ServerResponse> internalServerError(final Throwable throwable) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reactiveException(httpStatus, throwable));
    }

    public static Mono<ServerResponse> badRequest(final Throwable throwable) {
        return ServerResponse.badRequest().body(reactiveException(HttpStatus.BAD_REQUEST, throwable));
    }

    public static Mono<ServerResponse> notFound() {
        return ServerResponse.notFound().build();
    }

    @Getter
    static class ExceptionDetail {

        private Long timestamp;

        private Integer status;

        private HttpStatus error;

        private String message;

        private ExceptionDetail(final HttpStatus httpStatus, final Throwable throwable) {
            this.timestamp = Instant.now().toEpochMilli();
            this.status = httpStatus.value();
            this.error = httpStatus;
            this.message = throwable.getMessage();
        }

        static ExceptionDetail of(final HttpStatus httpStatus, final Throwable throwable) {
            return new ExceptionDetail(httpStatus, throwable);
        }
    }
}
