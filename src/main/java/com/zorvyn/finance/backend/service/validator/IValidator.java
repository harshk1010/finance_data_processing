package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.service.exception.ValidationException;

public interface IValidator<Input> {

    void validate(Input I) throws ValidationException;

}
