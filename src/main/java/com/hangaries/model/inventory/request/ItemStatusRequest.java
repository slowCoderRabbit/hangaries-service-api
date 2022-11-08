package com.hangaries.model.inventory.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ItemStatusRequest {

    private long id;
    private String itemStatus;
    private String updatedBy;
}
