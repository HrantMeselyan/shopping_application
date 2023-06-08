package com.example.shopping_application.controller;

import com.example.shopping_application.dto.categoryDto.CategoryDto;
import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Role;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CategoryService;
import com.example.shopping_application.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String main(ModelMap modelmap) {
        Map<String, List<CategoryDto>> parentCategoriesMap = categoryService.getParentCategoriesWithChildren();
        modelmap.addAttribute("parentCategoriesMap", parentCategoriesMap);
        return "index";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("profilePic") String imageName) throws IOException {
        return mainService.getImage(imageName);
    }

    @GetMapping("/customLogin")
    public String customLogin() {
        return "customLoginPage";
    }

    @GetMapping("/customSuccessLogin")
    public String customSuccessLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getRole() == Role.ADMIN) {
                return "redirect:/user/admin";
            } else if (user.getRole() == Role.USER) {
                return "redirect:/";
            }
        }
        return "redirect:/";
    }
}