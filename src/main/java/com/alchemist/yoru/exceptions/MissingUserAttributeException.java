package com.alchemist.yoru.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissingUserAttributeException extends RuntimeException {
    public MissingUserAttributeException(String message) {
        super(message);
    }
}

