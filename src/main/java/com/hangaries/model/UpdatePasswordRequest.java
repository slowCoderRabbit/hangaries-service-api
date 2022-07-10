package com.hangaries.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class UpdatePasswordRequest {

    private @NotBlank String loginId;
    private @NotBlank String loginPassword;
}
