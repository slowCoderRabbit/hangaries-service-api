package com.hangaries.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CashierSummeryCategory {
    List<CashierSummeryData> cashierSummeryData;
    private String category;
}
