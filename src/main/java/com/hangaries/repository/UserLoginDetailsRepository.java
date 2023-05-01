package com.hangaries.repository;

import com.hangaries.model.UserLoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, Long> {
    UserLoginDetails findByUserLoginIdAndId(String loginId, Integer userLoginDetailId);
}
