package cat.itacademy.s04.t02.n02.fruit.dto;


import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FruitResponseDTO {
    private Long id;
    private String name;
    private int weightInKilos;
    private Long providerId;
}
