package com.hangaries.model.wera.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WERAOrderFoodReadyResponse {
    private int code;
    private String msg;
    private String error;
    private List<String> details;
}
