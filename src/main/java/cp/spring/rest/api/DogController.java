package cp.spring.rest.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cp.spring.rest.dto.DogDTO;
import cp.spring.service.DogService;

//REST API + API ->> Which you can call using http protocol - >> Rest API
// GET , POST , PUT, PATCH , DELETE , OPTIONS
//URL -> URI
//URI IS SUPER SET OF URL
//https://us06web.zoom.us/j/83530144962?pwd=bcsytqTlmaM4g132LkogMsU7wg24Ew.1#success
//http://localhost:8080
@RestController
public class DogController {
	
	@Autowired
	private DogService dogService;
	
	//http://localhost:8080/dogs/910/9
	//did - 910
	//@PathVariable annotation is used read input data as part of URI
	@DeleteMapping("/dogs/{name}")
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("hasAuthority('SUPER ADMIN')")
	public Map<String,Object> deleteeDog(@PathVariable String name) {
		System.out.println("###########DELETING DOG WHIHC DID IS = "+name);
		dogService.deleteById(name);
		return Map.of("message","Dog is deleted","code",200);
	}
	
	//@DeleteMapping("/dogs");
	//@GetMapping("/dogs")
	/*
	{
			"name": "macky",
			"color": "Pink",
			"tail": 1
	}*/
	//JSON data  - > into Java Object - >>> Jackson library
	//JSON is just key and value pairs
	@PostMapping("/dogs")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String,Object> createDog(@RequestBody DogDTO dog) {
		System.out.println("###########"+dog);
		dogService.persist(dog);
		return Map.of("message","Dog is created","code",201);
	}
	
	
	////http://localhost:8080/ok
	/**
	 * 
	 * @return
	 */
	@GetMapping("/ok")
	public String callok() {
		return "Welcome in rest api";
	}
	
	//This is called passing input as part of URI
	//http://localhost:8080/dogs/Joggy
	@GetMapping("/dogs/{name}")
	public DogDTO getDogDetails(@PathVariable String name) {
		DogDTO dogDetails=dogService.findByName(name);
		return dogDetails;
	}
	

	//http://localhost:8080/dogs
	//EVERY RESOURCE IN REST IS MAPPED WITH UNIQUE URI
	@GetMapping("/dogs")
	public List<DogDTO> getDogs() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            System.out.println("Current user role = "+roles);
        }
		List<DogDTO> dogs=dogService.findAll();
		return dogs;
	}
}
