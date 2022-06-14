package org.astashonok.validatingstarter.intercepters;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.astashonok.validatingstarter.models.ValidationErrors;
import org.astashonok.validatingstarter.models.ValidationErrorsHolder;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsHolder onConstraintViolationException(ConstraintViolationException exception) {
        return Optional.of(exception)
                .map(ConstraintViolationException::getConstraintViolations)
                .map(this::getTargetToErrorMessages)
                .map(this::getValidationErrors)
                .map(ValidationErrorsHolder::new)
                .orElseGet(ValidationErrorsHolder::empty);
    }

    private List<ValidationErrors> getValidationErrors(Map<String, List<String>> targetToErrorMessages) {
        return targetToErrorMessages.entrySet().stream()
                .map(targetAndErrorMessages ->
                        new ValidationErrors(targetAndErrorMessages.getKey(), targetAndErrorMessages.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, List<String>> getTargetToErrorMessages(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(violation -> Pair.with(violation.getRootBeanClass().getSimpleName(), getViolationMessage(violation)))
                .filter(pair -> StringUtils.isNotEmpty(pair.getValue1()))
                .collect(Collectors.groupingBy(Pair::getValue0, Collectors.mapping(Pair::getValue1, Collectors.toList())));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsHolder onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return Optional.of(exception)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(bindingResult -> Pair.with(bindingResult, bindingResult.getAllErrors()))
                .filter(pair -> CollectionUtils.isNotEmpty(pair.getValue1()))
                .map(this::getValidationErrors)
                .map(ValidationErrorsHolder::new)
                .orElseGet(ValidationErrorsHolder::empty);
    }

    private ValidationErrors getValidationErrors(Pair<BindingResult, List<ObjectError>> bindingResultAndError) {
        BindingResult bindingResult = bindingResultAndError.getValue0();

        return new ValidationErrors(
                bindingResult.getObjectName(),
                getViolationMessages(bindingResult, bindingResultAndError.getValue1())
        );
    }

    private List<String> getViolationMessages(BindingResult bindingResult, List<ObjectError> objectErrors) {
        return objectErrors.stream()
                .filter(Objects::nonNull)
                .map(error -> getViolationMessage(error, bindingResult))
                .collect(Collectors.toList());
    }

    private String getViolationMessage(ObjectError error, BindingResult bindingResult) {
        String message = StringUtils.isEmpty(message = error.getDefaultMessage()) ? StringUtils.EMPTY : message;

        if (error instanceof FieldError) {
            String fieldName = ((FieldError) error).getField();
            Object fieldValue = bindingResult.getRawFieldValue(fieldName);
            message = String.format(error.getDefaultMessage(), fieldValue);
        }

        return message;
    }

    private String getViolationMessage(ConstraintViolation<?> constraintViolation) {
        String messageTemplate = StringUtils.isEmpty(messageTemplate = constraintViolation.getMessageTemplate())
                ? StringUtils.EMPTY
                : messageTemplate;

        return String.format(messageTemplate, constraintViolation.getInvalidValue());
    }
}
