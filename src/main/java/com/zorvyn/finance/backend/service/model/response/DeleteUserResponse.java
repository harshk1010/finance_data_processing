package com.zorvyn.finance.backend.service.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteUserResponse extends BaseResponse {

    private String message;

}
