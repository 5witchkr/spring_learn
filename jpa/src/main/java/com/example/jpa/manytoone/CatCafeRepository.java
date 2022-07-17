package com.example.jpa.manytoone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatCafeRepository extends JpaRepository<CatCafe, Long> {
}
