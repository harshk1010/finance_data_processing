package com.zorvyn.finance.backend.service.model.response;

import lombok.*;

@Builder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFinancialRecordResponse extends BaseResponse {

    private String message;
}
