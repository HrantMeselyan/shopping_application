package com.example.shopping_application.controller;

import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.dto.userDto.UserUpdateDto;
import com.example.shopping_application.entity.*;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserController {
    private final MailService mailService;
    private final UserService userService;
    private final OrderService orderService;
    private final NotificationService notificationService;
    private final DeliveryService deliveryService;

    @Value("${site.url}")
    private String siteUrl;

    @GetMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.addAttribute("userRegisterDto", UserMapper.userToUserRegisterDto(new User()));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterDto userRegisterDto, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        User user = userService.save(userRegisterDto);
        if (user != null) {
            mailService.sendMail(userRegisterDto.getEmail(), "Welcome",
                    "Hi " + userRegisterDto.getName() +
                            " Welcome please verify your account by clicking " + siteUrl + "/user/verify?email=" + user.getEmail() + "&token=" + user.getToken()
            );
        }
        return "redirect:/customLogin";
    }

    @GetMapping()
    public String currentUserPage(ModelMap modelmap,
                                  @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("userUpdateDto", new UserUpdateDto());
        modelmap.addAttribute("updatePasswordDto", new UpdatePasswordDto());
        modelmap.addAttribute("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        return "singleUserPage";
    }



    @PostMapping("/updatePassword")
    public String updatePassword(@Valid @ModelAttribute UpdatePasswordDto updatePasswordDto,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        userService.updatePassword(UserMapper.currentUserToUser(currentUser), updatePasswordDto);
        return "redirect:/user";
    }


    @PostMapping("/updateUserData")
    public String updateCurrentUser(@Valid @ModelAttribute UserUpdateDto userUpdateDto,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        userService.updateUser(multipartFile, UserMapper.userUpdateDtoToUser(userUpdateDto), currentUser);
        return "redirect:/user";
    }

    @GetMapping("/notifications/{userId}")
    public String notificationPage(ModelMap modelmap,
                                   @PathVariable("userId") int id,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("notifications", notificationService.findAllByUserId(id));
        modelmap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        return "notifications";
    }


    @GetMapping("remove")
    public String removeUser(@RequestParam("id") int id) {
        userService.removeById(id);
        return "redirect:/user/admin/all";
    }

    @GetMapping("update")
    public String updateUserPage(@RequestParam("id") int id,
                                 ModelMap modelMap) {
        modelMap.addAttribute("user", UserMapper.userToUserDto(userService.findById(id)));
        return "updateUser";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        return "admin";
    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
        return "reset-password";
    }

    @GetMapping("/admin/all")
    public String allUsersPage(ModelMap modelMap,
                               @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        List<User> all = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : all) {
            userDtos.add(UserMapper.userToUserDto(user));
        }
        modelMap.addAttribute("users", userDtos);
        return "allUsers";
    }


    @GetMapping("/order")
    public String userOrderPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        List<Order> allByUserId = orderService.findAllByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute(OrderMapper.listOrderToListOrderDto(allByUserId));
        return "account-orders";
    }

    @GetMapping("/payment")
    public String userPaymentPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        return "account-payment";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("email") String email, @RequestParam("token") UUID token) {
        boolean isVerified = userService.verifyUserByEmail(email, token);
        if (isVerified) {
            return "redirect:/customLogin";
        }
        return "redirect:/";
    }

    @GetMapping("/delivery/customer")
    public String deliveryUserPage(ModelMap modelmap,
                                   @RequestParam("id") int id) {
        modelmap.addAttribute("user", userService.findByIdWithAddresses(id));
        return "singleDeliveryUserPage";
    }

    @GetMapping("/delivery/order")
    public String deliveryOrderPage(ModelMap modelmap,
                                   @AuthenticationPrincipal CurrentUser currentUser,
                                   @RequestParam("id") int id) {
        modelmap.addAttribute("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        modelmap.addAttribute("order",orderService.findById(id).orElse(null));
        return "singleDeliveryOrderPage";
    }
    @GetMapping("/delivery")
    public String deliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByOrderStatus(Status.APPROVED, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries1", result);
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries2",
                deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable).getContent());
        return "account-delivery";
    }

    @GetMapping("/delivery/custom")
    public String customDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                     @RequestParam("delivery_id") int id,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     ModelMap modelMap) {
        deliveryService.chooseDelivery(UserMapper.currentUserToUser(currentUser), id,Status.IN_PROCESS);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByOrderStatus(Status.APPROVED, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries1", result);
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries2",
                deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable).getContent());
        return "account-delivery";
    }


    @GetMapping("/delivery/inProcess")
    public String inProcessDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries2", result);


        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries1",
                deliveryService.findAllByOrderStatus(Status.APPROVED, pageable).getContent());
        return "account-delivery-custom";
    }

    @GetMapping("/delivery/inProcess/custom")
    public String customInProcessDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                              @RequestParam("delivery_id") int id,
                                              @RequestParam("page") Optional<Integer> page,
                                              @RequestParam("size") Optional<Integer> size,
                                              ModelMap modelMap) {
        deliveryService.chooseDelivery(UserMapper.currentUserToUser(currentUser), id,Status.DELIVERED);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries2", result);


        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries1",
                deliveryService.findAllByOrderStatus(Status.APPROVED, pageable).getContent());
        return "account-delivery-custom";
    }
}