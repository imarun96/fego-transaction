package com.fego.transaction.common.exception;

import com.fego.transaction.common.config.ResponseMessage;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionResponseCreator exceptionResponseCreator;
    private final ResponseMessage responseMessage;

    @Autowired
    public BaseExceptionHandler(ExceptionResponseCreator exceptionResponseCreator, ResponseMessage responseMessage) {
        this.exceptionResponseCreator = exceptionResponseCreator;
        this.responseMessage = responseMessage;
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    protected final ResponseEntity<Object> handleException(final UnAuthorizedException unAuthorizedException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.UNAUTHORIZED,
                unAuthorizedException.getCode(), unAuthorizedException);
    }

    @ExceptionHandler(value = UserExistException.class)
    protected final ResponseEntity<Object> handleException(final UserExistException userExistException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.CONFLICT, userExistException.getCode(),
                userExistException);
    }

    @ExceptionHandler(value = IntegrityViolationException.class)
    protected final ResponseEntity<Object> handleException(
            final IntegrityViolationException integrityViolationException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                integrityViolationException.getCode(), integrityViolationException);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    protected final ResponseEntity<Object> handleException(final RecordNotFoundException recordNotFoundException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                recordNotFoundException.getCode(), recordNotFoundException);
    }

    @ExceptionHandler(value = OperationNotAllowed.class)
    protected final ResponseEntity<Object> handleException(final OperationNotAllowed operationNotAllowed) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                operationNotAllowed.getCode(), operationNotAllowed);
    }

    @ExceptionHandler(value = InvalidDataException.class)
    protected final ResponseEntity<Object> handleException(final InvalidDataException invalidDataException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                invalidDataException.getCode(), invalidDataException);
    }

    @ExceptionHandler(value = InvalidFileException.class)
    protected final ResponseEntity<Object> handleException(final InvalidFileException invalidFileException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                invalidFileException.getCode(), invalidFileException);
    }

    @ExceptionHandler(MultipartException.class)
    protected final ResponseEntity<Object> handleException(MultipartException exception,
                                                           RedirectAttributes redirectAttributes) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                ErrorCode.INVALID_FILE, exception);
    }

    @ExceptionHandler(value = RecordMismatchException.class)
    protected final ResponseEntity<Object> handleException(final RecordMismatchException recordMismatchException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.CONFLICT,
                recordMismatchException.getCode(), recordMismatchException);
    }

    @ExceptionHandler(value = Exception.class)
    protected final ResponseEntity<Object> handleException(final Exception exception) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.GENERAL_ERROR, exception);
    }

    @ExceptionHandler(value = BadRequestException.class)
    protected final ResponseEntity<Object> handleException(final BadRequestException badRequestException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST,
                badRequestException);
    }

    @ExceptionHandler(value = NotImplementedException.class)
    protected final ResponseEntity<Object> handleException(
            final NotImplementedException functionalityNotImplementedException) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.NOT_IMPLEMENTED, ErrorCode.GENERAL_ERROR,
                functionalityNotImplementedException);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected final ResponseEntity<Object> handleException(
            final ConstraintViolationException constraintViolationException) {
        StringJoiner errorMessage = new StringJoiner(Constants.COMMA);
        constraintViolationException.getConstraintViolations().forEach(constraintViolation -> errorMessage.add(constraintViolation.getPropertyPath() + Constants.COLON + constraintViolation.getMessage()));
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION, constraintViolationException, responseMessage
                        .getErrorMessage(ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.key(), errorMessage.toString()));
    }

    @ExceptionHandler(value = UnprocessableEntity.class)
    protected final ResponseEntity<Object> handleException(final UnprocessableEntity unprocessableEntity) {
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY,
                unprocessableEntity.getCode(), unprocessableEntity);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        StringJoiner errorMessage = new StringJoiner(Constants.COMMA);
        if (CollectionUtils.isEmpty(methodArgumentNotValidException.getBindingResult().getFieldErrors())) {
            if (!methodArgumentNotValidException.getBindingResult().getAllErrors().isEmpty()) {
                ObjectError error = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
                return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.PRECONDITION_FAILED,
                        ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION, methodArgumentNotValidException,
                        responseMessage.getErrorMessage(ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.key(),
                                error.getDefaultMessage()));
            }
        } else {
            for (FieldError error : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
                errorMessage.add(error.getField() + Constants.COLON + error.getDefaultMessage());
            }
        }
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION, methodArgumentNotValidException, responseMessage
                        .getErrorMessage(ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.key(), errorMessage.toString()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException httpMessageNotReadableException, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        EnumValidationServiceException enumValidationServiceException = (EnumValidationServiceException) httpMessageNotReadableException
                .getMostSpecificCause();
        String errorMessage = "";
        if (enumValidationServiceException.getEnumValue().isEmpty()) {
            errorMessage = enumValidationServiceException.getEnumName() + " must not be empty";
        } else {
            errorMessage = enumValidationServiceException.getEnumValue() + " is an invalid "
                    + enumValidationServiceException.getEnumName();
        }
        return exceptionResponseCreator.getExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION, httpMessageNotReadableException,
                responseMessage.getErrorMessage(ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.key(), errorMessage));
    }
}