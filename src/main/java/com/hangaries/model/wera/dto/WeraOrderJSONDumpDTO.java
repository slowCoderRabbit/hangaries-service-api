package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_JSON_DUMP")
public class WeraOrderJSONDumpDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "wera_order_id")
    private int order_id;

    @Column(name = "external_order_id")
    private String external_order_id;

    @Column(name = "wera_outlet_id")
    private int restaurant_id;

    @Column(name = "order_from")
    private String order_from;

    @Column(name = "order_date_time")
    private String order_date_time;

    @Column(name = "wera_order_json")
    private String wera_order_json;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
