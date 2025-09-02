package com.alchemist.yoru.controller;


import com.alchemist.yoru.dto.UserDto;
import com.alchemist.yoru.dto.response.RegisterResponse;
import com.alchemist.yoru.exceptions.MissingUserAttributeException;
import com.alchemist.yoru.repo.RoleRepo;
import com.alchemist.yoru.repo.UserDetailRepository;
import com.alchemist.yoru.service.IUserService;
import com.alchemist.yoru.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserResource {
    private final IUserService userService;
  public UserDetailRepository userDetailRepository;
  public RoleRepo roleRepo;

  public final UserServiceImpl userServiceimpl;

    @PostMapping
    public RegisterResponse registerUser(@RequestBody UserDto userData) {

        if (userData.getUsername() == null || userData.getPassword() == null || userData.getEmail() == null) {
            throw new MissingUserAttributeException("One or more attributes of the user object is missing");
        }

//        else {
//
//             throw new InternalServerException("SOmething Went Wrong");
//        }
         return userService.register(userData);
    }

    // Controllers for user verificationa and forgot password

    @GetMapping("/confirm")
    public void confirmUser(@RequestParam("token") String confirmationToken) {
        userServiceimpl.confirmUser(confirmationToken);
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {

        String response = userServiceimpl.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8082/user/reset-password?token=" + response;
        }
        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        return userServiceimpl.resetPassword(token, password);
    }
}
