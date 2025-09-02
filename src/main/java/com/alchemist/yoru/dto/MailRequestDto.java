package com.alchemist.yoru.dto;


import com.alchemist.yoru.entity.MailRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MailRequestDto extends MailRequest {
	private String email;
	private String subjectBody;
	private String body;

}
