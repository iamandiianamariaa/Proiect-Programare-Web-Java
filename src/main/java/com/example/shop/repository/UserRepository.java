package com.example.shop.repository;

import com.example.shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT CASE WHEN (u.userType = 'ADMIN') THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM User u WHERE u.id = :adminId")
    Boolean checkIfUserIsAdmin(@Param("adminId") Long adminId);

    @Query("SELECT CASE WHEN (u.userType = 'USER') THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM User u WHERE u.id = :userId")
    Boolean checkIfUserIsUser(@Param("userId") Long userId);
}
