package com.hangaries.repository.wera;

import com.hangaries.model.wera.dto.WeraOrderAddonDTO;
import com.hangaries.model.wera.dto.WeraOrderDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeraOrderDetailsRepository extends JpaRepository<WeraOrderDetailsDTO, Long> {

}
