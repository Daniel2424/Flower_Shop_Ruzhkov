package com.ruzhkov.flower_shop.repositories;

import com.ruzhkov.flower_shop.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    Optional<Flower> findById(int id);

}

