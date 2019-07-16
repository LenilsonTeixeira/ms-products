package com.reactive.msproducts.handlers;

import com.reactive.msproducts.exceptions.MsProductsException;
import com.reactive.msproducts.models.Product;
import com.reactive.msproducts.repositories.ProductRepository;
import com.reactive.msproducts.utils.ServerResponseUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Log4j2
@Component
public class ProductHandlerFunction {

    @Autowired
    private ProductRepository productRepository;


    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {

        log.debug("Request handling {}", serverRequest);

        return ok()
                .body(productRepository.findAll(), Product.class)
                .onErrorResume(MsProductsException.class, ServerResponseUtils::badRequest);
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {

        log.debug("Request handling {}", serverRequest);

        return ok()
                .body(productRepository.findById(serverRequest.pathVariable("id")), Product.class);
    }

    public Mono<ServerResponse> insertProduct(ServerRequest serverRequest) {

        log.debug("Request handling {}", serverRequest);

        final Mono<Product> action = serverRequest.bodyToMono(Product.class);

        final String productId = UUID.randomUUID().toString();
        return created(UriComponentsBuilder.fromPath("products/" + productId).build().toUri())
                .contentType(APPLICATION_JSON)
                .body(
                        fromPublisher(
                                action.map(p -> new Product(p, productId)).flatMap(productRepository::save), Product.class));

    }
}
