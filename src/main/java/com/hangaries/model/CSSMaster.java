package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CSS_MASTER")
public class CSSMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "restaurant_id")
    private String restaurantId;
    @Column(name = "store_Id")
    private String storeId;
    @Column(name = "category")
    private String category;
    @Column(name = "sub_category")
    private String subCategory;
    @Column(name = "sorting")
    private int sorting;
    @Column(name = "folder_name")
    private String folderName;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "status")
    private String status;
    @Column(name = "Created_by")
    private String createdBy = SYSTEM;
    @Column(name = "Created_date")
    private Date createdDate = new Date();
    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "Updated_date")
    private Date updatedDate = new Date();
}
