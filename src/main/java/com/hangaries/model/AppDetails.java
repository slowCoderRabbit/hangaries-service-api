package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APP_DETAILS")
public class AppDetails implements Serializable {

    @Id
    @Column(name = "app_environment")
    private String appEnvironment;

    @Column(name = "app_version")
    private String appVersion;

}
