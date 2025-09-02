package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.dto.MailRequestDto;
import com.alchemist.yoru.dto.response.MailResponse;
import com.alchemist.yoru.service.IEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender sender;
    private final Configuration config;

    @Override
    public MailResponse sendEmail(MailRequestDto request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
            // helper.addAttachment("image.png", new ClassPathResource("image.png"));

            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getEmail());
            helper.setText(html, true);
            helper.setSubject(request.getSubjectBody());
            sender.send(message);

            response.setMessage("mail send to : " + request.getEmail());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+e.getClass());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }
}
