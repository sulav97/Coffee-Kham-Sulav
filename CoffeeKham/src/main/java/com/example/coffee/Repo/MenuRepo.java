package com.example.coffee.Repo;

import com.example.coffee.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Integer> {
    Optional<Menu> findMenuByName(String name);

    @Query (value="SELECT * FROM menu ORDER BY date DESC LIMIT 4", nativeQuery = true)
    List<Menu> findNew();

    @Query (value="UPDATE menu set  item_quantity=? where id=?2", nativeQuery = true)
    String updateQuantity(double newQuantity, Integer id);
}
