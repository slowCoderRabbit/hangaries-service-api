package com.hangaries.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Report implements Serializable {

    private @NotBlank String restaurantId;
    private @NotBlank String storeId;
    private @NotBlank String fromDate;
    private @NotBlank String toDate;

}
