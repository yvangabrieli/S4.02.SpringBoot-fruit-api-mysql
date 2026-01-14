package cat.itacademy.s04.t02.n02.fruit.mapper;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Component;



@Component
public class ProviderMapper {
    private ProviderRequestDTO requestDTO;
    private ProviderResponseDTO responseDTO;
    private Provider provider;

    public ProviderMapper() {}

        public Provider toEntity (ProviderRequestDTO dto){
            return new Provider(dto.getName(), dto.getCountry());
        }

        public ProviderResponseDTO toDTO (Provider provider){
            return new ProviderResponseDTO(
                    provider.getId(),
                    provider.getName(),
                    provider.getCountry()
            );
        }
    }
