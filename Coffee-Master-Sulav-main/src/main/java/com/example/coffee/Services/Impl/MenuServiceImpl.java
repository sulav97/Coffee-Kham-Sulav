package com.example.coffee.Services.Impl;


import com.example.coffee.Entity.Menu;
import com.example.coffee.Pojo.MenuPojo;
import com.example.coffee.Repo.MenuRepo;
import com.example.coffee.Services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepo menuRepo;
//    private final CartRepo cartRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/Menus";

    @Override
    public MenuPojo saveMenu(MenuPojo menuPojo) throws IOException {
        Menu menu;
        if (menuPojo.getId() != null) {
            menu = menuRepo.findById(menuPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            menu = new Menu();
        }
        menu.setName(menuPojo.getName());
        menu.setItem_description(menuPojo.getItem_description());
        menu.setItem_quantity(menuPojo.getItem_quantity());
        menu.setPrice(menuPojo.getPrice());
        menu.setDate(menuPojo.getDate());

        if(menuPojo.getImage()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, menuPojo.getImage().getOriginalFilename());
            fileNames.append(menuPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, menuPojo.getImage().getBytes());

          menu.setImage(menuPojo.getImage().getOriginalFilename());
        }
       menuRepo.save(menu);
        return new MenuPojo(menu);
    }



    @Override
    public List<Menu> fetchAll() {
        return listMapping(menuRepo.findAll());
    }






    @Override
    public void deleteById(Integer id) {
        menuRepo.deleteById(id);
    }



    @Override
    public Menu getSingle(Integer id) {
        Menu menu = menuRepo.findById(id).orElseThrow();
        menu = Menu.builder()
                .id(menu.getId())
                .item_quantity(menu.getItem_quantity())
                .image(getImageBase64(menu.getImage()))
                .name(menu.getName())
                .price(menu.getPrice())
                .date(menu.getDate())
                .item_description(menu.getItem_description())

                .build();
        return menu;
    }

//    @Override
//    public List<Product> fetchByCategory(Integer id) {
//        return listMapping(productRepo.findByProduct_category(id));
//    }

    @Override
    public List<Menu> fetchNew() {
        return listMapping(menuRepo.findNew());
    }



//    @Override
//    public List<Menu> trending() {
//        List<Menu> trendingItems = new ArrayList<>();
//        for (Integer i : cartRepo.fetchTrending().orElseThrow()){
//            trendingItems.add(productRepo.findById(i).orElseThrow());
//        }
//        return listMapping(trendingItems);
//    }



    @Override
    public Map<Integer, Double> comparePrice(List<Menu> menus) {
        return null;
    }

    public List<Menu> listMapping(List<Menu> list){
        Stream<Menu> allMenusWithImage=list.stream().map(menu ->
                Menu.builder()
                        .id(menu.getId())
                        .item_quantity(menu.getItem_quantity())
                        .item_description(menu.getItem_quantity())
                        .image(getImageBase64(menu.getImage()))
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .date(menu.getDate())

                        .menu_imageBase64(getImageBase64(menu.getImage()))
                        .build()
        );
        list = allMenusWithImage.toList();
        return new ArrayList<>(list);
    }

//    public Map<Integer, Double> comparePrice(List<Product> products){
//        List<Sale> comparePrice = saleRepo.saleProducts();
//        Map<Integer, Double> priceDiscount = new HashMap<>();
//
//        for (Product product : products) {
//            for (Sale sale : comparePrice) {
//                if (Objects.equals(product.getId(), sale.getProduct().getId())) {
//                    priceDiscount.put(product.getId(), sale.getDiscountPercent());
//                }
//            }
//        }
//        return priceDiscount;
//    }

    public static String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir") + "/Menu/";
            File file = new File(filePath + fileName);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }
}









