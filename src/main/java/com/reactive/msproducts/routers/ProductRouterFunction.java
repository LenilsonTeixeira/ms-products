package com.reactive.msproducts.routers;

import com.reactive.msproducts.constants.MsProductsConstants;
import com.reactive.msproducts.filters.CustomerHandlerFilterFunction;
import com.reactive.msproducts.filters.DateParamHandlerFilterFunction;
import com.reactive.msproducts.filters.LimitParamHandlerFilterFunction;
import com.reactive.msproducts.filters.OffsetParamHandlerFilterFunction;
import com.reactive.msproducts.handlers.ProductHandlerFunction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Log4j2
@Configuration
public class ProductRouterFunction {

    @Autowired
    private CustomerHandlerFilterFunction customerHandlerFilterFunction;

    @Autowired
    private OffsetParamHandlerFilterFunction offsetParamHandlerFilterFunction;

    @Autowired
    private DateParamHandlerFilterFunction dateParamHandlerFilterFunction;

    @Autowired
    private LimitParamHandlerFilterFunction limitParamHandlerFilterFunction;


    @Bean
    public RouterFunction<ServerResponse> productRouter(ProductHandlerFunction productHandlerFunction) {
        return  RouterFunctions.nest(path(MsProductsConstants.MS_PRODUCTS_BASE_PATH),
                RouterFunctions.route(RequestPredicates.GET("/products")
                        .and(accept(APPLICATION_JSON)), productHandlerFunction::getAll)
                        .filter(customerHandlerFilterFunction)
                        .filter(offsetParamHandlerFilterFunction)
                        .filter(dateParamHandlerFilterFunction)
                        .filter(limitParamHandlerFilterFunction)
                        .andRoute(RequestPredicates.GET("/products/{id}")
                                .and(accept(APPLICATION_JSON)), productHandlerFunction::getById)
                        .filter(customerHandlerFilterFunction)
                        .andRoute(RequestPredicates.POST( "/products")
                                .and(accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))), productHandlerFunction::insertProduct)
                        .filter(customerHandlerFilterFunction));

    }

}
