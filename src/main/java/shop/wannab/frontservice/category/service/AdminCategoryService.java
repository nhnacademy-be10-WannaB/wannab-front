package shop.wannab.frontservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.category.client.CategoryClient;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryClient categoryClient;

    public void createCategory(CategoryCreateRequest request) {
        try {
            categoryClient.createCategory(request);
            log.info("새로운 카테고리 생성 성공");
        } catch (Exception e) {
            log.error("새로운 카테고리 생성 실패 카테고리 이름 : {}", request.getName(), e);
        }
    }
}