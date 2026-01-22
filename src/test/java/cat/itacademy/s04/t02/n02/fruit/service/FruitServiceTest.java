package cat.itacademy.s04.t02.n02.fruit.service;


import cat.itacademy.s04.t02.n02.fruit.exception.BusinessRuleException;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.FruitValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceTest {
    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private FruitValidator fruitValidator;

    @InjectMocks
    private FruitServiceImpl fruitService;


    @Test
    void createFruit_shouldThrowException_whenProviderNotFound() {
        Provider provider = new Provider("Fresh Fruit", "Spain");
        Fruit fruit = new Fruit("Apple", 5, provider);

        when(fruitRepository.existsByProviderId(fruit.getProvider().getId()))
                .thenReturn(false);

        assertThrows(ProviderNotFoundException.class,
                () -> fruitService.createFruit(fruit));
    }

    @Test
    void createFruit_shouldThrowException_whenFruitAlreadyExist() {
        Provider provider = new Provider("Fresh Fruit", "Spain");

        Fruit fruit = new Fruit("Apple", 5, provider);

        when(fruitRepository.existsByName("Apple"))
                .thenReturn(true);

        Fruit result = fruit;
        assertThrows(BusinessRuleException.class,
                () -> fruitService.createFruit(result));
    }

    @Test
    void createFruit_shouldReturnFruit_whenDataIsValid() {
        Provider provider = new Provider("Fresh Fruit", "Spain");

        Fruit fruit = new Fruit("Apple", 5, provider);

        when(fruitRepository.existsByName("Apple"))
                .thenReturn(false);
        when(fruitRepository.existsByProviderId(fruit.getProvider().getId()))
                .thenReturn(true);
        when(fruitRepository.save(fruit))
                .thenReturn(fruit);

        Fruit result = fruitService.createFruit(fruit);
        ;
        assertEquals("Apple", result.getName());
        assertEquals(5, result.getWeightInKilos());
        assertNotNull(fruitRepository);
    }

    @Test
    void getAllFruit_shouldReturnListOfFruit() {
        Provider provider = new Provider("Fresh Fruit", "Spain");
        Provider provider1 = new Provider("Eco Agricole", "France");

        List<Fruit> fruits = List.of(
                new Fruit("Apple", 5, provider),
                new Fruit("Pear", 5, provider),
                new Fruit("Oragne", 6, provider1));

        when(fruitRepository.findAll())
                .thenReturn(fruits);
        List<Fruit> result = fruitService.getAllFruits();
        assertEquals(3, result.size());
    }

    @Test
    void getFruitById_shouldReturnFruitWithId() {
        Provider provider = new Provider(1L);
        Fruit fruit = new Fruit(1L, "Apple", 5, provider);

        when(fruitRepository.findById(1L))
                .thenReturn(Optional.of(fruit));

        Fruit result = fruitService.getFruitById(1L);

        assertEquals(5, result.getWeightInKilos());
        assertEquals("Apple", result.getName());
        assertEquals(1L, result.getProvider().getId());
    }

    @Test
    void getFruitById_shouldThrowException_whenFruitNotFound() {
        Provider provider = new Provider("Fresh Fruits", "Spain");
        Fruit fruit = new Fruit("Apple", 5, provider);

        when(fruitRepository.findById(2L)).thenReturn(Optional.empty());

        Fruit result = fruit;

        assertThrows(FruitNotFoundException.class, () -> fruitService.getFruitById(2L));
    }

    @Test
    void updateFruit_shouldSuccess_whenFruitIsValid() {
        Provider provider = new Provider(1L);
        Fruit existing = new Fruit(1L, "Apple", 5, provider);
        Fruit newData = new Fruit(1L, "Green Apple", 3, provider);

        doNothing().when(fruitValidator).validate(any(Fruit.class));
        when(fruitRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(fruitRepository.save(existing))
                .thenReturn(newData);


        Fruit result = fruitService.updateFruit(1L, newData);

        assertEquals(1L, result.getId());
        assertEquals("Green Apple", result.getName());
        assertEquals(3, result.getWeightInKilos());
        assertEquals(1L, result.getProvider().getId());
    }

    @Test
    void updateFruit_shouldThrowException_whenFruitNotFound() {
        Provider provider = new Provider("Fresh Fruit", "Spain");

        Fruit newData = new Fruit("Apple", 4, provider);

        when(fruitRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.updateFruit(1L, newData));
    }

    @Test
    void deleteFruit_shouldSuccess_whenFruitFound() {

        when(fruitRepository.existsById(1L))
                .thenReturn(true);
        fruitService.deleteFruit(1L);
        verify(fruitRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFruit_shouldThrowException_whenFruitNotFound() {

        when(fruitRepository.existsById(2L))
                .thenReturn(false);

        assertThrows(FruitNotFoundException.class, () -> fruitService.deleteFruit(2L));

    }
}
