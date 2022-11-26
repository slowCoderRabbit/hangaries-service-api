package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RecipeWithName extends Recipe implements Serializable {

    private String itemName;

}
