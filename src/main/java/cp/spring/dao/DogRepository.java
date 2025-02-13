package cp.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cp.spring.entity.Dog;

//this is used to create instance of dao layer!
@Repository
public interface DogRepository extends JpaRepository<Dog, String> {
}
