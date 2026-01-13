package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

@Test
    void createProvider_shouldThrowException_whenNameAlreadyExist(){
        Provider existingProvider = new Provider();
        existingProvider.setName("FreshFruit");

        when(providerRepository.findByName("FreshFruit"))
                .thenReturn(Optional.of(existingProvider));

        ProviderRequestDTO requestDTO = new ProviderRequestDTO ("FreshFruit", "Spain");

        assertThrows(
                DuplicateResourceException.class,
                ()->providerService.createProvider(requestDTO)
        );
    }
}
