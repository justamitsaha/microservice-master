package com.saha.amit.exception;

import com.saha.amit.dto.ErrorResponseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        List<String> lst = new ArrayList<>();
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            lst.add(((FieldError) error).getField() + ":" + error.getDefaultMessage());
        });
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setErrorMessage(lst.toString());
        errorResponseDTO.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customException(Exception exception,
                                                            WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setErrorMessage(exception.getMessage());
        errorResponseDTO.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNoFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNoFoundException(Exception exception,
                                                            WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setErrorMessage(exception.getMessage());
        errorResponseDTO.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        log.error("Inside handleGlobalException -->"+ exception.getMessage()  +" --"+exception.getClass());
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setErrorMessage(exception.getMessage());
        errorResponseDTO.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorResponseDto> badRequest(Exception exception,
                                                       WebRequest webRequest) {
        log.error("Inside badRequest -->"+ exception.getMessage()  +" --"+exception.getClass());
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setErrorMessage(exception.getMessage());
        errorResponseDTO.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
