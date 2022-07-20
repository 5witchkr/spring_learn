package com.example.jpa.manytoone;


import com.example.jpa.manytoone.doubleway.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@ToString(exclude = "customers")
public class CatCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catCafe_id")
    private Long id;

    @OneToMany(mappedBy = "catCafe", cascade = CascadeType.ALL)
    private List<Customer> customers = new ArrayList<>();
}
