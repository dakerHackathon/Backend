package com.daker.repository;

import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.password = :password")
    User findIdByLoginIdandPassword(@Param("loginId") String loginId, @Param("password") String password);
}
