package com.saber.site.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target(value = {ElementType.ANNOTATION_TYPE,ElementType.TYPE,
        ElementType.METHOD,ElementType.PARAMETER,
        ElementType.FIELD,ElementType.CONSTRUCTOR})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = NotBlankValidator.class)
public @interface NotBlank {
    String message() default "is Require";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target(value = {ElementType.ANNOTATION_TYPE,ElementType.TYPE,
            ElementType.METHOD,ElementType.PARAMETER,
            ElementType.FIELD,ElementType.CONSTRUCTOR})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        NotBlank[] value();
    }
}
