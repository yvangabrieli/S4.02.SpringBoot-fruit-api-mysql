package cat.itacademy.s04.t02.n02.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponseDTO {
    private Long id;
    private String name ;
    private String country;
}
