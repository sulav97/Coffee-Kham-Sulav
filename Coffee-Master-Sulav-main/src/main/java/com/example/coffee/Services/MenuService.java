package com.example.coffee.Services;

import com.example.coffee.Entity.Menu;
import com.example.coffee.Pojo.MenuPojo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MenuService {
    MenuPojo saveMenu(MenuPojo menuPojo) throws IOException;




    void deleteById(Integer id);



    List<Menu> fetchNew();

    Map<Integer, Double> comparePrice(List<Menu> menus);


    List<Menu> fetchAll();
    Menu getSingle(Integer id);


}


