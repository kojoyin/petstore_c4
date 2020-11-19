package com.petstore.data.repository;

import com.petstore.data.model.Store;
import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
    }

    // Test that we can save a pet to the database
    @Test
//    @Transactional

    public void whenPetIsSaved_thenReturnAPetId(){

        // step 1: Create an instance of a pet
        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(3);
        pet.setBreed("Dog");
        pet.setColour("Black");
        pet.setPetSex(Gender.MALE);

        log.info("pet instance before saving -->{}", pet);


        //call repository save method
        petRepository.save(pet);


        assertThat(pet.getId()).isNotNull();
        log.info("pet instance after saving saving -->{}", pet);

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void whenStoreIsMappedToPet_thenForeignKeyIsPresent(){
        //create pet
        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(3);
        pet.setBreed("Dog");
        pet.setColour("Black");
        pet.setPetSex(Gender.MALE);

        log.info("Pet instance before saving --> {}", pet);


        //create store
        Store store = new Store();
        store.setName ("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("08020339504");


        //map pet to store
        pet.setStore(store);


        //save pet
        petRepository.save(pet);


        // assert
        assertThat(pet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(pet.getStore()).isNotNull();
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void whenIAddPetToStore_thenICanFetchAListOfPetsFromStore() {

        //create store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("08020339504");


        //create 2 pets
        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(3);
        jack.setBreed("Dog");
        jack.setColour("Black");
        jack.setPetSex(Gender.MALE);
        jack.setStore(store);


        Pet sally = new Pet();
        sally.setName("Jack");
        sally.setAge(3);
        sally.setBreed("Dog");
        sally.setColour("Black");
        sally.setPetSex(Gender.FEMALE);
        sally.setStore(store);


        log.info("Pet instances before saving --> {}", jack, sally);


        //map pet to store
        store.addPets(jack);
        store.addPets(sally);

        //save store
        storeRepository.save(store);
        log.info("Store instance after saving --> {}", store);

        // assert for pets id's
        assertThat(jack.getId()).isNotNull();
        assertThat(sally.getId()).isNotNull();

        // assert for store id's
        assertThat(store.getId()).isNotNull();

        //assert to make sure store has no pets
        assertThat(store.getPetList()).isNotEmpty();
    }

    @Test
    public void whenFindAllPetsIsCalled_thenReturnAllPetsInStore() {
        //find pets from store
        List<Pet> savedPets = petRepository.findAll();

        log.info("fetched pets list from db --> {}", savedPets);

        //assert that pets exists
        assertThat(savedPets).isNotEmpty();
        assertThat(savedPets.size()).isEqualTo(8);
    }

    @Test
    public void updateExistingPetDetailsTest(){
        //fetch a pet
        Pet mill = petRepository.findById(34).orElse(null);

        //assert the field
        assertThat(mill).isNotNull();
        assertThat(mill.getColour()).isEqualTo("green");

        //update pet field

        mill.setColour("brown");

        // save change

        petRepository.save(mill);
        log.info("After updating pet object -->{}",mill);

        //assert That Updated field has changed
        assertThat(mill.getColour()).isEqualTo("brown");
    }


    @Test
    public void whenIdeletePetFromDatabase_thenPetIsDelete(){
        //check if pet exist

        boolean result = petRepository.existsById(34);

        //assert that pet exist
        assertThat(result).isTrue();

        //delete pet
        petRepository.deleteById(34);

        // check if pet exist
        assertThat(petRepository.existsById(34)).isFalse();
    }
  }