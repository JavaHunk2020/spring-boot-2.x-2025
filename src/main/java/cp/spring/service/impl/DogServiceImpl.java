package cp.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cp.spring.dao.DogRepository;
import cp.spring.entity.Dog;
import cp.spring.rest.dto.DogDTO;
import cp.spring.service.DogService;

@Service
public class DogServiceImpl implements DogService{
	
	@Autowired
	private DogRepository dogRepository;
	
	@Override
	public void deleteById(String name) {
		dogRepository.deleteById(name);	
	}
	
	
	@Override
	public void persist(DogDTO dogDTO) {
		Dog dog=new Dog();
		dog.setColor(dogDTO.getColor());
		dog.setName(dogDTO.getName());
		dog.setTail(dogDTO.getTail());
		dogRepository.save(dog);
	}


	@Override
	public List<DogDTO> findAll() {
		List<Dog> dogs=dogRepository.findAll();
		List<DogDTO> dogDTOs=new ArrayList<DogDTO>();
		for(Dog d : dogs) {
			DogDTO dogDTO=new DogDTO();
			dogDTO.setColor(d.getColor());
			dogDTO.setName(d.getName());
			dogDTO.setTail(d.getTail());
			dogDTOs.add(dogDTO);
		}
		return dogDTOs;
		//List<Dog> into List<DogDTO>
		/*return dogs.stream().map((Dog d)->{
			DogDTO dogDTO=new DogDTO();
			dogDTO.setColor(d.getColor());
			dogDTO.setName(d.getName());
			dogDTO.setTail(d.getTail());
			return dogDTO;
		}).collect(Collectors.toList());*/
	}

   //Optional is immutable class
	@Override
	public DogDTO findByName(String name) {
		Optional<Dog> optional=dogRepository.findById(name);
		if(optional.isEmpty()) {
			throw new RuntimeException("Sorry data is not found name = "+name);
		}
		Dog d=optional.get();
		DogDTO dogDTO=new DogDTO();
		dogDTO.setColor(d.getColor());
		dogDTO.setName(d.getName());
		dogDTO.setTail(d.getTail());
		return dogDTO;
	}

}
