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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    private ProviderRepository providerRepository;
    private ProviderValidator providerValidator;
    private ProviderMapper mapper;
    private FruitRepository fruitRepository;

        public ProviderServiceImpl (ProviderRepository providerRepository, ProviderValidator providerValidator, ProviderMapper mapper, FruitRepository fruitRepository){
            this.providerRepository = providerRepository;
            this.providerValidator = providerValidator;
            this.mapper = mapper;
            this.fruitRepository = fruitRepository;
        }

    @Override
    public Provider createProvider(ProviderRequestDTO providerRequestDTO) {
        Provider provider = mapper.toEntity(providerRequestDTO);
        providerValidator.validate(provider);
        providerRepository.findByName(provider.getName())
                .ifPresent(p -> {
                    throw new DuplicateResourceException("Provider with name " + providerRequestDTO.getName() + " already exist");
                });
        return providerRepository.save(provider);
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public Provider getProviderById(Long id) {
        return providerRepository.findById(id).orElseThrow(() ->
                new ProviderNotFoundException("Provider with id " + id +" not found"));
    }

    @Override
    public Provider updateProvider(Long id, Provider newData) {
        Provider existing = getProviderById(id);
        providerValidator.validate(newData);
        existing.setName(newData.getName());
        existing.setCountry(newData.getCountry());
        return providerRepository.save(existing);
    }

    @Override
    public void deleteProvider (Long id ){
        if (!providerRepository.existsById(id)){
            throw new ProviderNotFoundException("Provider with id " + id +" not found");
        }
        if (fruitRepository.existsByProviderId(id)){
            throw new BusinessRuleException("Cannot be delete Provider with the Id " + id + " because it has fruit associated");
        }
        providerRepository.deleteById(id);
    }
}