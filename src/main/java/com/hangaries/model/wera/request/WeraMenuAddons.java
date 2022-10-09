package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraMenuAddons {
    private String addon_id;
    private String addon_name;
    private float price;
}
