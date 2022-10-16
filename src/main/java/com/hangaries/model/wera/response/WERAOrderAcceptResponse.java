package com.hangaries.model.wera.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WERAOrderAcceptResponse {
    private int code;
    private String msg;
}
