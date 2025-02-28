package cp.spring.rest.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cp.spring.rest.dto.DogDTO;
import cp.spring.service.DogService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DogController.class)
@Import(TestSecurityConfig.class)  // Disable security in tests
public class DogControllerTest {
	
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @Autowired
    private ObjectMapper objectMapper;
    
    
    @Test
    public void testCallOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ok"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome in rest api"));
    }

    @Test
    public void testDeleteDog() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/Macky"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Dog is deleted"))
                .andExpect(jsonPath("$.code").value(200));

        verify(dogService, times(1)).deleteById("Macky");
    }

    @Test
    public void testDeleteDog_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/Macky"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateDog() throws Exception {
        DogDTO dog = new DogDTO("Macky", "Pink", 1);
        doNothing().when(dogService).persist(Mockito.any(DogDTO.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/dogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Dog is created"))
                .andExpect(jsonPath("$.code").value(201));

        verify(dogService, times(1)).persist(Mockito.any(DogDTO.class));
    }

    @Test
    public void testGetDogDetails() throws Exception {
        DogDTO dog = new DogDTO("Joggy", "Brown", 2);
        when(dogService.findByName("Joggy")).thenReturn(dog);

        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/Joggy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Joggy"))
                .andExpect(jsonPath("$.color").value("Brown"))
                .andExpect(jsonPath("$.tail").value(2));

        verify(dogService, times(1)).findByName("Joggy");
    }

    @Test
    public void testGetDogs() throws Exception {
        List<DogDTO> dogs = List.of(new DogDTO("Max", "Black", 1), new DogDTO("Buddy", "White", 2));
        when(dogService.findAll()).thenReturn(dogs);

        mockMvc.perform(MockMvcRequestBuilders.get("/dogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Max"))
                .andExpect(jsonPath("$[1].name").value("Buddy"));

        verify(dogService, times(1)).findAll();
    }

}
