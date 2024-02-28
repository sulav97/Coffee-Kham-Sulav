package com.example.coffee.Repo;


import com.example.coffee.Entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepo extends JpaRepository<Gallery, Integer> {

    Optional<Gallery> findGalleryByName(String name);
}

