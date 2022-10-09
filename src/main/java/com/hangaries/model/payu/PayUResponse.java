package com.hangaries.model.payu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayUResponse {
    private String status;
    private String txnid;
    private String error_Message;
    private String hash;
    private String mode;
}
