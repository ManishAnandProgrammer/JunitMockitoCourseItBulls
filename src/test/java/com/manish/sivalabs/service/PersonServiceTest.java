package com.manish.sivalabs.service;

import com.manish.sivalabs.domain.Person;
import com.manish.sivalabs.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository personRepository = new PersonRepository();
        personService = new PersonService(personRepository);
    }

    @AfterEach
    void tearDown() {
        personService.deleteAll();
    }

    @Nested
    class CreatePersonTests {
        @Test
        void shouldCreatePersonSuccessfully() {
            Person person = personService.create(new Person(null, "Siva", "siva@gmail.com"));
            assertNotNull(person.getId());
            assertEquals("Siva", person.getName());
            assertEquals("siva@gmail.com", person.getEmail());
        }

        @Test
        void shouldFailToCreatePersonWithExistingEmail() {
            String email = UUID.randomUUID() +"@gmail.com";
            personService.create(new Person(null, "Siva", email));

            assertThatThrownBy(()-> personService.create(new Person(null, "Siva", email)))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Person with email '"+email+"' already exists");
        }
    }

    @Nested
    class FindPersonByEmailTests {
        String email;

        @BeforeEach
        void setUp() {
            email = UUID.randomUUID() +"@gmail.com";
            personService.create(new Person(null, "Siva", email));
        }

        @Test
        void shouldGetPersonByEmailWhenExists() {
            Optional<Person> optionalPerson = personService.findByEmail(email);
            assertThat(optionalPerson).isPresent();
        }

        @Test
        void shouldGetEmptyWhenPersonByEmailNotExists() {
            Optional<Person> optionalPerson = personService.findByEmail("random@mail.com");
            assertThat(optionalPerson).isEmpty();
        }
    }
}
