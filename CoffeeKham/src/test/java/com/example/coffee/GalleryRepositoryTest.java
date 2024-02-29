package com.example.coffee;

import com.example.coffee.Entity.Gallery;
import com.example.coffee.Repo.GalleryRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GalleryRepositoryTest {

    @Autowired
    private GalleryRepo galleryRepo;

    @Test
    @Order(1)
    public void saveContactTest() {
        Gallery gallery = Gallery.builder()
                .name("image")
                .build();

        galleryRepo.save(gallery);

        Assertions.assertThat(gallery.getId()).isGreaterThan(0); // Modified assertion
    }

    @Test
    @Order(2)
    public void getContactTest() {
        Gallery gallery = Gallery.builder()
                .name("image")
                .build();

        galleryRepo.save(gallery);

        Gallery galleryCreated = galleryRepo.findById(gallery.getId()).get();
        Assertions.assertThat(galleryCreated.getId()).isEqualTo(gallery.getId());
    }

    @Test
    @Order(3)
    public void getListOfContactTest() {
        Gallery gallery = Gallery.builder()
                .name("image")
                .build();

        galleryRepo.save(gallery);
        List<Gallery> galleries = galleryRepo.findAll();
        Assertions.assertThat(galleries.size()).isGreaterThan(0); // Modified assertion
    }

    @Test
    @Order(4)
    public void updateContactTest() {
        Gallery gallery = Gallery.builder()
                .name("image")
                .build();

        galleryRepo.save(gallery);

        Gallery gallery1 = galleryRepo.findById(gallery.getId()).get();
        gallery1.setName("b@g.com");

        Gallery galleryUpdated = galleryRepo.save(gallery1);

        Assertions.assertThat(galleryUpdated.getName()).isEqualTo("b@g.com");
    }

    @Test
    @Order(5)
    public void deleteContactTest() {
        Gallery gallery = Gallery.builder()
                .name("image")
                .build();

        galleryRepo.save(gallery);

        Gallery gallery1 = galleryRepo.findById(gallery.getId()).get();
        galleryRepo.delete(gallery1);

        Optional<Gallery> optionalGallery = galleryRepo.findById(gallery.getId());
        Assertions.assertThat(optionalGallery).isEmpty(); // Check if the gallery has been deleted
    }
}
