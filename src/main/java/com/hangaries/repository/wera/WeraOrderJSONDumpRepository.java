package com.hangaries.repository.wera;

import com.hangaries.model.wera.dto.WeraOrderJSONDumpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeraOrderJSONDumpRepository extends JpaRepository<WeraOrderJSONDumpDTO, Long> {

}
