package com.hangaries.model.keys;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleId implements Serializable {
    private Integer roleId;
    private String restaurantId;
    private String storeId;
    private String roleCategory;

}
