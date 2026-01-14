package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;


import java.util.List;

public interface ProviderService {
    Provider createProvider(ProviderRequestDTO providerRequestDTO);
    List<Provider> getAllProviders();
    Provider getProviderById (Long id);
    Provider updateProvider (Long id, Provider provider);
    void deleteProvider (Long id);
}
