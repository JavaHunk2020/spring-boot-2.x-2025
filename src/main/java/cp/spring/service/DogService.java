package cp.spring.service;

import java.util.List;

import cp.spring.rest.dto.DogDTO;

public interface DogService {

	void deleteById(String name);
	void persist(DogDTO dogDTO);
	List<DogDTO> findAll();
	DogDTO findByName(String name);

}
