package com.example.jpa.manytoone.singleway;

import com.example.jpa.manytoone.CatCafe;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Getter
@Setter
@Entity
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @Column(name = "catName")
    private String catName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catCafe_id")
    private CatCafe catCafe;
}
