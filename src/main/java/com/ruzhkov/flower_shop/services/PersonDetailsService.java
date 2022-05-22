package com.ruzhkov.flower_shop.services;

import com.ruzhkov.flower_shop.entity.Flower;
import com.ruzhkov.flower_shop.entity.Person;
import com.ruzhkov.flower_shop.repositories.PeopleRepository;
import com.ruzhkov.flower_shop.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonDetailsService( PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(s);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
    public Person getPerson(String username) {
        return peopleRepository.findByName(username).get();
    }

    public void update(Person user, Flower flower) {

        List<Flower> flowerList = peopleRepository.findByName(user.getName()).get().getFlowerList();



        flowerList.add(flower);


        user.setFlowerList(flowerList);

        peopleRepository.save(user);
    }

    public void delete(Person user, Flower flower) {

        List<Flower> flowerList = peopleRepository.findByName(user.getName()).get().getFlowerList();
        flowerList.removeIf(x-> x.getId() == flower.getId());
        user.setFlowerList(flowerList);
        peopleRepository.save(user);
    }

    public List<Person> getAllPerson(){
        List<Person> listPerson = peopleRepository.findAll();
        return listPerson;
    }



}
