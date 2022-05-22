package com.ruzhkov.flower_shop.services;

import com.ruzhkov.flower_shop.entity.Flower;
import com.ruzhkov.flower_shop.entity.Person;
import com.ruzhkov.flower_shop.repositories.FlowerRepository;
import com.ruzhkov.flower_shop.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;

@Service
public class FlowerService {
    private final FlowerRepository flowerRepository;

    @Autowired
    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public Flower getFlower(int id){
        Optional<Flower> flower = flowerRepository.findById(id);
        return flower.get();
    }

    public Iterable<Flower> getAll(){
        return this.flowerRepository.findAll();
    }

    public void addProduct(Flower flower) {
        this.flowerRepository.save(flower);
    }

    public void delete(Flower flower, Person user) {
        List<Person> personList = flowerRepository.findById(flower.getId()).get().getPersonList();
        personList.removeIf(x-> x.getId() == user.getId());
        flower.setPersonList(personList);
        flowerRepository.save(flower);
    }


}
