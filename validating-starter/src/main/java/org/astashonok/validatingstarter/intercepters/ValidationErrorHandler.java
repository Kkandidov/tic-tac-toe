package org.astashonok.validatingstarter.intercepters;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.astashonok.validatingstarter.models.ValidationErrors;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrors onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return Optional.of(exception)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(bindingResult -> Pair.with(bindingResult, bindingResult.getAllErrors()))
                .filter(pair -> CollectionUtils.isNotEmpty(pair.getValue1()))
                .map(this::getValidationErrors)
                .orElseGet(ValidationErrors::empty);
    }

    private ValidationErrors getValidationErrors(Pair<BindingResult, List<ObjectError>> pair) {
        BindingResult bindingResult = pair.getValue0();

        return new ValidationErrors(
                bindingResult.getObjectName(),
                getViolationMessages(bindingResult, pair.getValue1())
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
}
