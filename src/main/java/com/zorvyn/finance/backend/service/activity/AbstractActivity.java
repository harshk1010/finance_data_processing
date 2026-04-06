package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.exception.DependencyFailure;
import com.zorvyn.finance.backend.service.exception.TransformationException;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;
import com.zorvyn.finance.backend.service.exception.ConfigNotFoundException;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class AbstractActivity<Input, Output> {

    IValidator<Input> validator;

    IProcessor<Input, Output> processor;

    public Output execute(Input input) throws ValidationException, TransformationException,
            DependencyFailure, DataAccessException, ConfigNotFoundException {
        try {
            validator.validate(input);
            return processor.process(input);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
