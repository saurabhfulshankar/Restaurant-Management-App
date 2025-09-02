package com.alchemist.yoru.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private final static String INCORRECT_REQUEST = "INCORRECT_REQUEST";
	private final static String BAD_REQUEST = "BAD_REQUEST";
	private final static String CONFLICT = "CONFLICT";

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException(RecordNotFoundException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MissingHeaderInfoException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MissingHeaderInfoException ex,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}



	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {
		List<String> details = ex.getConstraintViolations().parallelStream().map(e -> e.getMessage())
				.collect(Collectors.toList());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomDataIntegrityViolationException.class)
	public final ResponseEntity<ErrorResponse> dataIntegrityViolationException(CustomDataIntegrityViolationException ex,
			WebRequest request) {
		String [] detail = ex.getLocalizedMessage().split("Detail: Key ");
		ErrorResponse error = new ErrorResponse(CONFLICT, Arrays.asList(detail));
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MissingUserAttributeException.class)
	public ResponseEntity<String> handleMissingUserAttributeException(MissingUserAttributeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<String> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<>(message, HttpStatus.CONFLICT);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleInternalServerException(InternalServerException ex) {
//		String [] detail = ex.getLocalizedMessage().split("Detail: Key ");
//		ErrorResponse errorResponse = new ErrorResponse(CONFLICT,Arrays.asList(detail));
//		errorResponse.setMessage(ex.getMessage());
		String message="Something Went Wrong";
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex)
	{
		String message = ex.getMessage();
		return new ResponseEntity<>(message,HttpStatus.CONFLICT);
	}

	@ExceptionHandler(OrderAlreadyExistException.class)
	public ResponseEntity<String> handleOrderAlreadyExistException(OrderAlreadyExistException ex,WebRequest request)
	{
		String message =ex.getMessage();
		return new ResponseEntity<>(message,HttpStatus.CONFLICT);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex,WebRequest request)
	{
		String message =ex.getMessage();
		return new ResponseEntity<>(message,HttpStatus.CONFLICT);
	}
}
