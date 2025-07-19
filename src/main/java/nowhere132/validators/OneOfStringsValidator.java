package nowhere132.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nowhere132.annotations.OneOfStrings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OneOfStringsValidator implements ConstraintValidator<OneOfStrings, String> {
    private Set<String> allowedValues;

    @Override
    public void initialize(OneOfStrings constraintAnnotation) {
        allowedValues = new HashSet<>(Arrays.asList(constraintAnnotation.values()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || allowedValues.contains(value);
    }
}
