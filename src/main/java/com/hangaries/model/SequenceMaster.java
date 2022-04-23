package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SEQUENCE_MASTER")
public class SequenceMaster {


    @Id
    private long id;

    @Column(name = "order_sequence_number")
    private @NotBlank String orderSequenceNumber;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;
}
