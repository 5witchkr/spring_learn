package com.example.jpa.manytoone;


import com.example.jpa.manytoone.doubleway.Customer;
import com.example.jpa.manytoone.doubleway.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class CatService {

    private final CustomerRepository customerRepository;
    private final CatCafeRepository catCafeRepository;

    public void createByRelationMaster(){
        CatCafe catCafe = new CatCafe();
        catCafeRepository.save(catCafe);

        Customer customer1 = new Customer();
        customer1.setName("name1");
        customer1.setCatCafe(catCafe);

        Customer customer2 = new Customer();
        customer2.setName("name2");
        customer2.setCatCafe(catCafe);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    public void createByRelationSlave(){
        CatCafe catCafe = new CatCafe();

        Customer customer1 = new Customer();
        customer1.setName("name1");

        Customer customer2 = new Customer();
        customer2.setName("name2");

        catCafe.getCustomers().add(customer1);
        catCafe.getCustomers().add(customer2);
        catCafeRepository.save(catCafe);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    public void deleteCustomerBySlave(){
        List<CatCafe> catCafes = catCafeRepository.findAll();
        CatCafe catCafe = catCafes.get(0);
        catCafe.getCustomers().clear();
    }

    public List<String> findAllCustomerNamesByCatCafe(){
        List<CatCafe> catCafes = catCafeRepository.findAll();
        return catCafes.get(0)
                .getCustomers()
                .stream()
                .map(Customer::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(Customer::getName)
                .collect(Collectors.toList());
    }

    public List<CatCafe> findCatCafesByCustomer(){
        return customerRepository.findAll()
                .stream()
                .map(Customer::getCatCafe)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
