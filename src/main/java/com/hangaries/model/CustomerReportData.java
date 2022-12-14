package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerReportData {

    private String mobile_number;
    private String name;
    private String customer_address_type;
    private String address;
    private String email_id;
    private String address_type_status;
    private Date data_created;

}
