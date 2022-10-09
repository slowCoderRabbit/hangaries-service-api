package com.hangaries.repository.wera;

import com.hangaries.model.wera.dto.WeraOrderAddonDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeraOrderAddonRepository extends JpaRepository<WeraOrderAddonDTO, Long> {

}
