package com.hangaries.repository;

import com.hangaries.model.TaxMaster;
import com.hangaries.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from USER_MASTER where user_first_name=:firstName", nativeQuery = true)
    User getUserByFirstName(@Param("firstName") String firstName);
}
