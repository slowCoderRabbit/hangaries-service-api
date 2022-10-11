package com.hangaries.model.inventory.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SupplierStatusRequest {

    private long id;
    private String supplierStatus;
    private String updatedBy;
}
