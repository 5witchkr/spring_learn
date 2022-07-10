package com.example.jpa.onetoone;

import com.example.jpa.onetoone.doulbeway.House;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString(exclude = "house")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private House house;
}