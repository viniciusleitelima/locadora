package br.com.locadora.locadora.exception;

import br.com.locadora.locadora.enums.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControlExceptionHandler {


    public static final String X_RD_TRACEID = "X-rd-traceid";
    public static final String CONSTRAINT_VALIDATION_FAILED = "Constraint validation failed";


    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);
        log.info("Customer BusinessException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode() != null ? ex.getHttpStatusCode().toString() : null, ex.getMessage(), ex.getDescription());
        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

    }

    /**
     * @param request
     * @return
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exMethod,
                                                                   WebRequest request) {

        String error = exMethod.getName() + " should be " + exMethod.getRequiredType().getName();

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(CONSTRAINT_VALIDATION_FAILED)
                .description(error)
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);
        log.info("Customer failed MethodArgumentTypeMismatchException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    /**
     * @param exMethod
     * @param request
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exMethod, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exMethod.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(CONSTRAINT_VALIDATION_FAILED)
                .description(errors.toString())
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed ConstraintViolationException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    /**
     * @param exMethod
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod) {

        BindingResult bindingResult = exMethod.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> fieldErrorDtos = fieldErrors.stream()
                .map(f -> "{'".concat(f.getField()).concat("':'").concat(f.getDefaultMessage()).concat("'}")).map(String::new)
                .collect(Collectors.toList());

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(CONSTRAINT_VALIDATION_FAILED)
                .description(fieldErrorDtos.toString())
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    /**
     * @param exMethod
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> validationError(HttpMessageNotReadableException exMethod) {


        String message = exMethod.getMostSpecificCause().getMessage();

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(CONSTRAINT_VALIDATION_FAILED)
                .description(message)
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed HttpMessageNotReadableException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(Message.ILLEGAL_ARGUMENT_EXCEPTION.getMessage())
                .description(ExceptionResolver.getRootException(illegalArgumentException))
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed IllegalArgumentException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(Message.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION.getStatus())
                .message(Message.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION.getMessage())
                .description(ExceptionResolver.getRootException(httpMediaTypeNotSupportedException))
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed httpMediaTypeNotSupportedException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(Message.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION.getStatus())
                .message(Message.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION.getMessage())
                .description(ExceptionResolver.getRootException(httpRequestMethodNotSupportedException))
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed httpRequestMethodNotSupportedException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<Object> handleBindException(BindException bindException) {
        BusinessException ex = BusinessException.builder()
                .httpStatusCode(Message.BIND_EXCEPTION.getStatus())
                .message(Message.BIND_EXCEPTION.getMessage())
                .description(ExceptionResolver.getRootException(bindException))
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(X_RD_TRACEID, null);

        log.info("Customer failed BindException httpStatusCode={}, message={}, description={}", ex.getHttpStatusCode().toString(), ex.getMessage(), ex.getDescription());

        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

}


