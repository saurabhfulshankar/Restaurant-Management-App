package com.alchemist.yoru.controller;

import com.alchemist.yoru.dto.MailRequestDto;
import com.alchemist.yoru.dto.response.MailResponse;
import com.alchemist.yoru.service.impl.EmailServiceImpl;
import com.alchemist.yoru.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailServiceImpl service;

    @PostMapping("/sending-email")
    public MailResponse sendEmail(@RequestBody MailRequestDto request) {
        Map<String, Object> model = new HashMap<>();
        model.put("Body",request.getBody());
        model.put("Subject",request.getSubjectBody());
        return service.sendEmail(request, model);
    }
}

