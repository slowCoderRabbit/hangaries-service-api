package com.hangaries.repository.wera;

import com.hangaries.model.wera.dto.WeraOrderSizeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeraOrderSizeRepository extends JpaRepository<WeraOrderSizeDTO, Long> {

}
