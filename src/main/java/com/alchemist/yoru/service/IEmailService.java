package com.alchemist.yoru.service;

import com.alchemist.yoru.dto.MailRequestDto;
import com.alchemist.yoru.dto.response.MailResponse;

import java.util.Map;

public interface IEmailService {
    MailResponse sendEmail(MailRequestDto request, Map<String, Object> model);
}
