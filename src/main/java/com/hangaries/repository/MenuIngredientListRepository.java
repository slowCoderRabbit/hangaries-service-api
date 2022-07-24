package com.hangaries.repository;

import com.hangaries.model.MenuIngredientList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuIngredientListRepository extends JpaRepository<MenuIngredientList, Long> {

}