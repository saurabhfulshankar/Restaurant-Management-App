package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.dto.UserDto;
import com.alchemist.yoru.dto.enums.RoleEnum;
import com.alchemist.yoru.dto.response.MailResponse;
import com.alchemist.yoru.dto.response.RegisterResponse;
import com.alchemist.yoru.entity.Role;
import com.alchemist.yoru.entity.User;
import com.alchemist.yoru.exceptions.EmailAlreadyExistException;
import com.alchemist.yoru.repo.RoleRepo;
import com.alchemist.yoru.repo.UserDetailRepository;
import com.alchemist.yoru.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;

    private final JavaMailSender mailSender;


    private String baseUrl="https://recipes.timesofindia.com/recipes/onion-capsicum-pizza/rs59632728.cms";

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Override
    public RegisterResponse register(UserDto userData) {
        Optional<User> userEmail = userDetailRepository.findByEmail(userData.getEmail());
        Optional<User> userUsername = userDetailRepository.findByUsername(userData.getUsername());
        if (userEmail.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists");
        }
        if (userUsername.isPresent()) {
            throw new EmailAlreadyExistException("Username already exists");
        }else {
            User user = new User();
            BeanUtils.copyProperties(userData, user);
            Role role = roleRepo.findByName(RoleEnum.ROLE_admin.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmationToken(UUID.randomUUID().toString());
            userDetailRepository.save(user);
            return new RegisterResponse("User has been registered.");
        }
    }




    //User CONFIRMATION AND Verification
    public String forgotPassword(String email) {

        Optional<User> userOptional = userDetailRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

//        MailResponse response = new MailResponse();
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Registration Confirmation");
//        mailMessage.setText("Please confirm your email address by clicking the link below:\n"
//                + baseUrl + "/confirm?token=" + user.getToken());
//        mailSender.send(mailMessage);

        user = userDetailRepository.save(user);

        return user.getToken();
    }

    public String resetPassword(String token, String password) {

        Optional<User> userOptional = Optional
                .ofNullable(userDetailRepository.findByToken(token));

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        User user = userOptional.get();

        user.setPassword(passwordEncoder.encode(password));
        user.setToken(null);
        user.setTokenCreationDate(null);

        userDetailRepository.save(user);

        return "Your password successfully updated.";
    }


    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }


    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

//    public void registerUser(User user) {
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        user.setConfirmationToken(UUID.randomUUID().toString());
//        userDetailRepository.save(user);
//        sendConfirmationEmail(user);
//    }

    public void confirmUser(String confirmationToken) {
        User user = userDetailRepository.findByConfirmationToken(confirmationToken);
        if (user == null) {
            throw new RuntimeException("Invalid confirmation token");
        }
        user.setConfirmationToken(null);
        user.setConfirmed(true);
        userDetailRepository.save(user);
    }

    private void sendConfirmationEmail(User user) {
        MailResponse response = new MailResponse();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Registration Confirmation");
        mailMessage.setText("Please confirm your email address by clicking the link below:\n"
                + baseUrl + "/confirm?token=" + user.getConfirmationToken());
        mailSender.send(mailMessage);
        response.setMessage("Confirmation link is send to your mail pls check it");

    }

    public void updateUser(User user) {
        User existingUser =userDetailRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        existingUser.setConfirmationToken(UUID.randomUUID().toString());
        userDetailRepository.save(existingUser);
    }
}
