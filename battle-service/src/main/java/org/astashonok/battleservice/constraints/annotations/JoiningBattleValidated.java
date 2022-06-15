package org.astashonok.battleservice.constraints.annotations;

import org.apache.commons.lang3.StringUtils;
import org.astashonok.battleservice.constraints.validators.JoiningBattleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = JoiningBattleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoiningBattleValidated {

    String message() default StringUtils.EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
