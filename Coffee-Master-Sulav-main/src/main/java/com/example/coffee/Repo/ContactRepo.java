package com.example.coffee.Repo;


import com.example.coffee.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

    Optional<Contact> findContactByEmail(String email);
}
