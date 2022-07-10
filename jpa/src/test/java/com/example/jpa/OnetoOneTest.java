package com.example.jpa;


import com.example.jpa.onetoone.Person;
import com.example.jpa.onetoone.PersonRepository;
import com.example.jpa.onetoone.doulbeway.House;
import com.example.jpa.onetoone.doulbeway.HouseRepository;
import com.example.jpa.onetoone.singleway.Company;
import com.example.jpa.onetoone.singleway.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("one to one mapping test")
@SpringBootTest
@Transactional
public class OnetoOneTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HouseRepository houseRepository;

    @DisplayName("one to one single mapping Test")
    @Test
    void testCompany() {
        // given
        Person person = new Person();
        person.setName("Dori");

        Company company = new Company();
        company.setName("SAMSUNG");
        company.setPerson(person);

        // when
        companyRepository.save(company);

        // then
        assertThat(companyRepository.findAll()).isNotEmpty();
        assertThat(personRepository.findAll()).isNotEmpty();
    }

    @DisplayName("one to one double mapping Test")
    @Test
    void testHouse() {
        // given
        Person person = new Person();
        person.setName("Dori");

        House house = new House();
        house.setAddress("Jinju");
        house.setPerson(person);

        // when
        houseRepository.save(house);

        // then
        assertThat(houseRepository.findAll()).isNotEmpty();
        assertThat(personRepository.findAll()).isNotEmpty();
    }
}
