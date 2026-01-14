package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.BusinessRuleException;
import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateResourceException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.mapper.ProviderMapper;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.ProviderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private ProviderMapper providerMapper;
    @Mock
    private ProviderValidator providerValidator;
   @Mock
   private FruitRepository fruitRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

@Test
    void createProvider_shouldThrowException_whenNameAlreadyExist(){
        Provider existingProvider = new Provider("FreshFruit", "Spain");
        ProviderRequestDTO providerRequestDTO = new ProviderRequestDTO("FreshFruit", "Spain");

        when(providerRepository.findByName("FreshFruit"))
                .thenReturn(Optional.of(existingProvider));

        when(providerMapper.toEntity(providerRequestDTO))
                .thenReturn(existingProvider);

        assertThrows(
                DuplicateResourceException.class,
                ()->providerService.createProvider(providerRequestDTO)
        );
    }

    @Test
    void createProvider_shouldSAveProvider_whenValid(){
        ProviderRequestDTO requestDTO = new ProviderRequestDTO("FreshFruit", "Spain");
        Provider provider = new Provider("FreshFruit", "Spain");

        when(providerMapper.toEntity(requestDTO))
            .thenReturn(provider);

        when(providerRepository.findByName("FreshFruit"))
                .thenReturn(Optional.empty());

        when(providerRepository.save(provider))
                .thenReturn(provider);

        Provider result = providerService.createProvider(requestDTO);

        assertNotNull(result);
        assertEquals("FreshFruit", result.getName());
    }
    @Test
    void getAllProviders_shouldReturnList(){
        List<Provider> providers = List.of(
                new Provider("FreshFruit", "Spain"),
                new Provider("BioFarm", "France"),
                new Provider("ComerEco", "Andorra")
        );

        when(providerRepository.findAll())
                .thenReturn(providers);

        List<Provider> result = providerService.getAllProviders();
        assertEquals(3, result.size());
    }

    @Test
    void getProviderById_shouldThrowException_whenNotFound(){
    when(providerRepository.findById(1L))
            .thenReturn(Optional.empty());

    assertThrows(ProviderNotFoundException.class,
            () -> providerService.getProviderById(1L));
    }

    @Test
    void UpdateProvider_shouldUpdateExistingProvider(){
    Provider existing = new Provider("FreshFruits", "Spain");
    Provider updated = new Provider("FreshFruit", "Spain");

    when(providerRepository.findById(1L))
            .thenReturn(Optional.of(existing));

    when(providerRepository.save(existing))
            .thenReturn(existing);

    Provider result = providerService.updateProvider(1L, updated);

    assertEquals("FreshFruit",result.getName());
    assertEquals("Spain", result.getCountry());
    }

    @Test
    void deleteProvider_shouldThrowException_whenProviderHasFruits(){
    when(providerRepository.existsById(1L))
            .thenReturn(true);

    when(fruitRepository.existsByProviderId(1L))
            .thenReturn(true);

    assertThrows(BusinessRuleException.class,
            () -> providerService.deleteProvider(1L));
    }
}
