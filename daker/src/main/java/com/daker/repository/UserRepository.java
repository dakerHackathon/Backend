package com.daker.repository;

import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.password = :password")
    User findIdByLoginIdandPassword(@Param("loginId") String loginId, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByLoginId(String loginId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.point > :point")
    int getRank(@Param("point") int point);

    @Query("SELECT u FROM User u WHERE u.nickname LIKE %:query%")
    List<User> search(@Param("query") String query);

    @Query("SELECT u FROM User u ORDER BY u.temperature desc LIMIT 10")
    List<User> findTop10ByOrderByTemperatureDesc();

    @Query("SELECT COUNT(u) FROM User u WHERE u.temperature > :user")
    int getTempRank(@Param("user") User user);
}
