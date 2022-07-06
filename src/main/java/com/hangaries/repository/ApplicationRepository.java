package com.hangaries.repository;

import com.hangaries.model.AppDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<AppDetails, String> {

}
