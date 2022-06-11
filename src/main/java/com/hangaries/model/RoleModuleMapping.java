package com.hangaries.model;

import com.hangaries.model.keys.RoleModuleMappingId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(RoleModuleMappingId.class)
@Table(name = "ROLE_MODULE_MAPPING_MASTER")
public class RoleModuleMapping {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Id
    @Column(name = "role_mapping_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleMappingId;

    @Id
    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Id
    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Id
    @Column(name = "role_category")
    private @NotBlank String roleCategory;

    @Id
    @Column(name = "module_id")
    private Integer moduleId;

    @Column(name = "access_flag")
    private @NotBlank String accessFlag;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
