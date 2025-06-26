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

        // main page
        registry.addViewController("/").setViewName("user/main");

        // user auth
        registry.addViewController("/auth/login").setViewName("auth/login");
        registry.addViewController("/auth/signup").setViewName("auth/signup");
        registry.addViewController("/auth/reactivate").setViewName("auth/reactivate");
        registry.addViewController("/auth/logout").setViewName("auth/login");

        // user main
        registry.addViewController("/user/main-search").setViewName("user/main-search");
        registry.addViewController("/user/search/books").setViewName("user/main-search");
        registry.addViewController("/user/main-book-detail").setViewName("user/main-book-detail");
        registry.addViewController("/user/main-cart").setViewName("user/main-cart");
        registry.addViewController("/user/main-order").setViewName("user/main-order");
        registry.addViewController("/user/main-non-member-order").setViewName("user/main-non-member-order");
        registry.addViewController("/user/main-non-member-order-detail").setViewName("user/main-non-member-order-detail");

        // user address
        registry.addViewController("/user/mypage-address-form").setViewName("user/mypage-address-form");
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}