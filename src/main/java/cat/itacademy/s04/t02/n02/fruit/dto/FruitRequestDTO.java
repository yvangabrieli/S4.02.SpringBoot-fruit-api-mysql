package cat.itacademy.s04.t02.n02.fruit.dto;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FruitRequestDTO {
    @NotBlank(message = "Fruit name cannot be blank")
    private String name;
    @Positive(message = "Fruit weight must be positive")
    private int weightInKilos;
    @NotNull(message = "Fruit must have a provider")
    private Long providerId;
}
