package com.alchemist.yoru.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MailRequest {

        private String email;
        private String subjectBody;
        private String body;

}
