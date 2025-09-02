package com.alchemist.yoru.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String message) {
        super(message);
    }
}
