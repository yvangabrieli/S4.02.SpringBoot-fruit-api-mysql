package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.mapper.FruitMapper;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.service.FruitService;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest (FruitController.class)
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FruitService fruitService;
    @MockBean
    private FruitMapper fruitMapper;
    @MockBean
    private ProviderService providerService;

    @Test
    void createFruit_shouldReturn201_whenDataIsValid() throws Exception {
        Provider provider = new Provider(1L);
        FruitRequestDTO fruitRequestDTO = new FruitRequestDTO("Apple", 5, provider.getId());
        Fruit fruit = new Fruit(1L, "Apple", 5, provider);
        FruitResponseDTO fruitResponseDTO = new FruitResponseDTO(1L, "Apple", 5, provider.getId());

        when(providerService.getProviderById(provider.getId())).thenReturn(provider);

        when(fruitMapper.toEntity(any(FruitRequestDTO.class), any(Provider.class)))
                .thenReturn(fruit);
        when(fruitService.createFruit(fruit))
                .thenReturn(fruit);
        when(fruitMapper.toDTO(any(Fruit.class)))
                .thenReturn(fruitResponseDTO);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fruitRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(5));
    }

    @Test
    void createFruit_shouldReturn400_whenValidationFails() throws Exception {
        FruitRequestDTO invalid = new FruitRequestDTO("", -1, null);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFruit_shouldReturn404_whenProviderNotFound() throws Exception {
        Provider provider = new Provider(99L, "Fresh Fruits", "Spain");
        FruitRequestDTO request = new FruitRequestDTO("Apple", 5, provider.getId());
        Fruit fruit = new Fruit("Apple", 5,provider);

        when(providerService.getProviderById(provider.getId())).thenReturn(provider);

        when(fruitMapper.toEntity(any(FruitRequestDTO.class), any(Provider.class)))
                .thenReturn(fruit);

        when(fruitMapper.toEntity(any(FruitRequestDTO.class), any(Provider.class)))
                .thenThrow(new FruitNotFoundException("Provider not found"));

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFruit_shouldReturnUpdatedFruit() throws Exception {
        Provider provider = new Provider(1L);
        FruitRequestDTO request = new FruitRequestDTO("Green Apple", 4, provider.getId());
        Fruit updated = new Fruit(1L, "Green Apple", 4,provider);
        FruitResponseDTO response = new FruitResponseDTO(1L, "Green Apple", 4, provider.getId());

        when(providerService.getProviderById(provider.getId())).thenReturn(provider);
        when(fruitMapper.toEntity(any(FruitRequestDTO.class), eq( provider))).thenReturn(updated);
        when(fruitService.updateFruit(1L, updated)).thenReturn(updated);
        when(fruitMapper.toDTO(updated)).thenReturn(response);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Green Apple"));
    }

    @Test
    void updateFruit_shouldReturn404_whenNotFound() throws Exception {
        Provider provider = new Provider(1L);
        FruitRequestDTO request = new FruitRequestDTO("Apple", 4, provider.getId());

        when(providerService.getProviderById(provider.getId())).thenReturn(provider);
        when(fruitMapper.toEntity(any(FruitRequestDTO.class), eq(provider)))
                .thenThrow(new FruitNotFoundException("Not found"));

        mockMvc.perform(put("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFruit_shouldReturn204_whenSuccess() throws Exception {
        doNothing().when(fruitService).deleteFruit(1L);

        mockMvc.perform(delete("/fruits/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFruit_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new FruitNotFoundException("Fruit Not found"))
                .when(fruitService).deleteFruit(99L);

        mockMvc.perform(delete("/fruits/99"))
                .andExpect(status().isNotFound());
    }
}