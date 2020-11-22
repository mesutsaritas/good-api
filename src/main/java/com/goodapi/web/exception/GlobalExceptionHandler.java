package com.goodapi.web.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.goodapi.web.resource.ErrorResource;

/**
 * @author msaritas
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResource> customerNotFoundException(CustomerNotFoundException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.NOT_FOUND);
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = UserNameAndPasswordException.class)
    public @ResponseBody ResponseEntity<ErrorResource> userNameAndPasswordException(UserNameAndPasswordException e)
            throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.FORBIDDEN);
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BookCodeExistException.class)
    public @ResponseBody ResponseEntity<ErrorResource> bookCodeExistException(BookCodeExistException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.ALREADY_REPORTED);
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = InsufficientStockOfBooksException.class)
    public @ResponseBody ResponseEntity<ErrorResource> insufficientStockOfBooksException(InsufficientStockOfBooksException e)
            throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.NOT_FOUND);
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BookNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResource> bookNotFoundException(BookNotFoundException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.NOT_FOUND);
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = OrderNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResource> orderNotFoundException(OrderNotFoundException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.NOT_FOUND);
    }

    /**
     * 
     * @param e
     * @return
     */

    @ExceptionHandler(value = UserNameAlreadyException.class)
    public @ResponseBody ResponseEntity<ErrorResource> userNameAlreadyException(UserNameAlreadyException e) {
        return new ResponseEntity<>(e.getResource(), HttpStatus.ALREADY_REPORTED);
    }

    /**
    * 
    */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    /**
     * 
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        List<String> validationErrors = exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).collect(Collectors.toList());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    /**
     * 
     * @param status
     * @param request
     * @param errors
     * @return
     */
    private ResponseEntity<Object> getExceptionResponseEntity(final HttpStatus status, WebRequest request, List<String> errors) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String errorsMessage = !CollectionUtils.isEmpty(errors)
                ? errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(","))
                : status.getReasonPhrase();
        final String path = request.getDescription(false);
        body.put("Errors", errorsMessage);
        body.put("Path", path);
        body.put("Message", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }

}