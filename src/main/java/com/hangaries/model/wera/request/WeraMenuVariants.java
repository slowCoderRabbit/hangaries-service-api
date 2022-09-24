package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraMenuVariants {

    private String variant_id;
    private String variant_name;
    private Float price;

}
