package com.hangaries.model.wera.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraUploadMenu {

    private int merchant_id;
    private List<WeraMenu> menu;
    private List<WeraMenuVariants> variants;
    private List<WeraMenuAddons> addons;

}
