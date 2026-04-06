package com.zorvyn.finance.backend.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFinancialRecordResponse extends BaseResponse {

    private String message;

}
