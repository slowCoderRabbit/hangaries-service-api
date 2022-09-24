package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraMenu {
    private String id;
    private String item_name;
    private float price;
    private boolean active;
    private float packaging;
    private float cgst;
    private float sgst;
    private int type;
}
