package com.saber.site.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank,CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence instanceof String){
           return  ((String)charSequence).trim().length()>0;
        }
        return charSequence.toString().trim().length()>0;
    }
}
