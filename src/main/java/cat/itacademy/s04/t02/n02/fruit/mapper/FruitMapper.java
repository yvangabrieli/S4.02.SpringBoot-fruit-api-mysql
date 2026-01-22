package cat.itacademy.s04.t02.n02.fruit.mapper;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import org.springframework.stereotype.Component;


@Component
public class FruitMapper {

    public Fruit toEntity(FruitRequestDTO dto, Provider provider) {
        return new Fruit(dto.getName(), dto.getWeightInKilos(),provider);
    }

    public FruitResponseDTO toDTO(Fruit fruit) {
        return new FruitResponseDTO(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider() != null ? fruit.getProvider().getId() : null
        );
    }
}

