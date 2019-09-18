package com.lambdaschool.school.handlers;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.ErrorDetail;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

// Spring uses this custom handler file to 'give advice'
// if a controller's response entity encounters an exception, it will hit an endpoint
// Spring Boot has automatic exception handling,
// since we are working w endpoints, we have to add @EnableWebMvc to main app file

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    // ExceptionHandler bean handles the ResourceNotFoundException when thrown
    // we can add more specific system errors below
    @ExceptionHandler({ResourceNotFoundException.class})
    // no data found at requests endpoint
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    // endpoint does not exist
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle(ex.getRequestURL());
        errorDetail.setDetail(request.getDescription(true));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    // endpoint path data type is incorrect ex: int vs string
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle(ex.getPropertyName());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(request.getDescription(true));

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    // checks if request method is valid/supported. ex: GET/POST/PUT/DELETE
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle(ex.getMethod());
        errorDetail.setDetail(request.getDescription(true));
        errorDetail.setDeveloperMessage("HTTP Method Not Valid for Endpoint (check for valid URI and poroper HTTP Method");

        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }
}
