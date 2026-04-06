package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.service.exception.DependencyFailure;

public interface IProcessor<Input, Output> {

    Output process(Input I) throws DependencyFailure, DataAccessException;
}
