package com.hangaries.model;

import com.hangaries.model.keys.RoleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.ACTIVE;
import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(RoleId.class)
@Table(name = "ROLE_MASTER")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Id
    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Id
    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Id
    @Column(name = "role_category")
    private @NotBlank String roleCategory;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "admin_flag")
    private String adminFlag;

    @Column(name = "role_status")
    private String roleStatus = ACTIVE;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
