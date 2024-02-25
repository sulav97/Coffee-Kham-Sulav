package com.example.coffee.Entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @SequenceGenerator(name = "hrs_user_seq_gen", sequenceName = "pms_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "hrs_user_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column()
    private String name;
    @Column()
    private String date;

    @Column()
    private String item_description;

    @Column()
    private String item_quantity;

    @Column()
    private String price;

    @Column()
    private String image;

    @Transient
    private String menu_imageBase64;
}

