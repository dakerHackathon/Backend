package com.daker.repository;

import com.daker.domain.entity.Message;
import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.receiver = :user")
    List<Message> findAllByReceiver(@Param("user") User user);

    @Query("SELECT m FROM Message m WHERE m.receiver = :user AND m.isStar = true")
    List<Message> findStarByReceiver(@Param("user") User user);

    @Query("SELECT m FROM Message m WHERE m.receiver = :user AND m.isRead = false")
    List<Message> findUnreadByReceiver(@Param("user") User user);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.receiver = :user AND m.isRead = false")
    int countUnreadByReceiver(@Param("user") User user);
}
