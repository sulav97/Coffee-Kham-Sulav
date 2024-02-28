package com.example.coffee.Repo;


import com.example.coffee.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(value = "select * from USERS where email=?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

@Query(value= "UPDATE users SET password =?2",nativeQuery=true)
    void updatePassword(String updated_password, String email);
}

