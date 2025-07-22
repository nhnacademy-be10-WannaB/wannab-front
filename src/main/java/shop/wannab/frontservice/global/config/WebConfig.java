package shop.wannab.frontservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){

        registry.addViewController("/auth/login-form").setViewName("auth/login");
        registry.addViewController("/auth/signup").setViewName("auth/signup");
        registry.addViewController("/auth/reactivate").setViewName("auth/reactivate");

        registry.addViewController("/user/search/books").setViewName("user/main-search");
        registry.addViewController("/guest/main-non-member-order").setViewName("guest/main-non-member-order");
        registry.addViewController("/guest/main-non-member-order-detail").setViewName("user/order-detail");

        registry.addViewController("/api-docs").setViewName("docs/api-docs");
        registry.addViewController("/docs/book-service").setViewName("docs/book-service");
        registry.addViewController("/docs/coupon-service").setViewName("docs/coupon-service");
        registry.addViewController("/docs/order-payment-service").setViewName("docs/order-payment-service");


    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}