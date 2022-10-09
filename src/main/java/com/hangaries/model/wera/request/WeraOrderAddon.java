package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrderAddon {

    private String addon_id;
    private String name;
    private float price;
    private float cgst;
    private float sgst;
    private float cgst_percent;
    private float sgst_percent;

}
