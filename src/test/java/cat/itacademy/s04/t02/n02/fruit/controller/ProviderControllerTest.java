package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.BusinessRuleException;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.mapper.ProviderMapper;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.apache.bcel.classfile.Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProviderService providerService;

    @MockBean
    private ProviderMapper providerMapper;


    @Test
    void createProvider_shouldReturn201_whenValid() throws Exception {
        ProviderRequestDTO request = new ProviderRequestDTO("FreshFruit", "Spain");
        Provider provider = new Provider(1L, "FreshFruit", "Spain");
        ProviderResponseDTO response = new ProviderResponseDTO(1L, "FreshFruit", "Spain");

        when(providerMapper.toEntity(any(ProviderRequestDTO.class))).thenReturn(provider);
        when(providerService.createProvider(any(ProviderRequestDTO.class))).thenReturn(provider);
        when(providerMapper.toDTO(any(Provider.class))).thenReturn(response);

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("FreshFruit"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void createProvider_shouldReturn400_whenDuplicateName() throws Exception {
        ProviderRequestDTO request = new ProviderRequestDTO("FreshFruit", "Spain");

        when(providerService.createProvider(any(ProviderRequestDTO.class)))
                .thenThrow(new BusinessRuleException("Provider already exists"));

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }


    @Test
    void getAllProviders_shouldReturn200() throws Exception {
        List<Provider> providers = List.of(
                new Provider(1L, "FreshFruit", "Spain"),
                new Provider(2L, "BioFarm", "France")
        );

        when(providerService.getAllProviders()).thenReturn(providers);
        when(providerMapper.toDTO(any(Provider.class)))
                .thenAnswer(inv -> {
                    Provider p = inv.getArgument(0);
                    return new ProviderResponseDTO(p.getId(), p.getName(), p.getCountry());
                });

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }


    @Test
    void updateProvider_shouldReturn200_whenValid() throws Exception {
        ProviderRequestDTO request = new ProviderRequestDTO("FreshFruitUpdated", "Italy");
        Provider updated = new Provider(1L, "FreshFruitUpdated", "Italy");
        ProviderResponseDTO response = new ProviderResponseDTO(1L, "FreshFruitUpdated", "Italy");

        when(providerMapper.toEntity(any(ProviderRequestDTO.class))).thenReturn(updated);
        when(providerService.updateProvider(eq(1L), any(Provider.class))).thenReturn(updated);
        when(providerMapper.toDTO(any(Provider.class))).thenReturn(response);

        mockMvc.perform(put("/providers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FreshFruitUpdated"))
                .andExpect(jsonPath("$.country").value("Italy"));
    }

    @Test
    void updateProvider_shouldReturn404_whenNotFound() throws Exception {
        ProviderRequestDTO request = new ProviderRequestDTO("FreshFruit", "Spain");

        when(providerService.updateProvider(any(), any()))
                .thenThrow(new ProviderNotFoundException("Not found"));

        mockMvc.perform(put("/providers/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteProvider_shouldReturn204_whenSuccess() throws Exception {
        doNothing().when(providerService).deleteProvider(1L);

        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProvider_shouldReturn409_whenHasFruits() throws Exception {
        doThrow(new BusinessRuleException("Provider has fruits"))
                .when(providerService)
                .deleteProvider(1L);

        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteProvider_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new ProviderNotFoundException("Provider Not found"))
                .when(providerService).deleteProvider(99L);


        mockMvc.perform(delete("/providers/99"))
                .andExpect(status().isNotFound());
    }
}
