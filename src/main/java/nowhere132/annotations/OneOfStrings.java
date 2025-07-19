package nowhere132.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nowhere132.validators.OneOfStringsValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = OneOfStringsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OneOfStrings {
    String message();
    String[] values();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
