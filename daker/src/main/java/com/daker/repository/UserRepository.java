package com.daker.repository;

import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.password = :password")
    User findIdByLoginIdandPassword(@Param("loginId") String loginId, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT cnt(u) FROM User u WHERE u.point > :point")
    int getRank(@Param("point") int point);
}
