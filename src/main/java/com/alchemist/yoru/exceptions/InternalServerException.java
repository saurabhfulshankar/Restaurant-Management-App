package com.alchemist.yoru.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InternalServerException extends RuntimeException {
    public InternalServerException(String string) {
        super(string);
    }


}
