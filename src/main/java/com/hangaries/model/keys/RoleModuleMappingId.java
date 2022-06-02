package com.hangaries.model.keys;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleModuleMappingId implements Serializable {


    private Integer roleId;
    private Integer roleMappingId;
    private String restaurantId;
    private String storeId;
    private String roleCategory;
    private Integer moduleId;
}
