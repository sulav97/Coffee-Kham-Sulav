package com.example.coffee.Services;


import com.example.coffee.Entity.Gallery;
import com.example.coffee.Pojo.GalleryPojo;

import java.io.IOException;
import java.util.List;

public interface GalleryServices {
    GalleryPojo save(GalleryPojo galleryPojo) throws IOException;
    List<Gallery> fetchAll();
    void deleteById(Integer id);

}

