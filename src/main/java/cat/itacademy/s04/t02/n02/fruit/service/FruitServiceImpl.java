package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.exception.BusinessRuleException;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.FruitValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService{
    private final FruitRepository repository;
    private final FruitValidator validator;


    public FruitServiceImpl(FruitRepository repository, FruitValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Fruit createFruit(Fruit fruit) {
        validator.validate(fruit);

        if (repository.existsByName(fruit.getName())) {
            throw new BusinessRuleException("A fruit with the name '" + fruit.getName() + "' already exists");
        }
        if(!repository.existsByProviderId(fruit.getProviderId())){
            throw new ProviderNotFoundException("Fruit doesn't have a provider");
        }
        return repository.save(fruit);
    }

    @Override
    public List<Fruit> getAllFruits() {
        return repository.findAll();
    }

    @Override
    public Fruit getFruitById(Long id) {
        return repository.findById(id).orElseThrow(() -> new FruitNotFoundException("Fruit with the id " + id + " do not exist"));
    }

    @Override
    public Fruit updateFruit(Long id, Fruit newData) {
        Fruit existing = getFruitById(id);
        validator.validate(newData);
        existing.setName(newData.getName());
        existing.setWeightInKilos(newData.getWeightInKilos());
        existing.setProviderId(newData.getProviderId());
        return repository.save(existing);
    }

    @Override
    public void deleteFruit(Long id) {
        if (!repository.existsById(id)) {
            throw new FruitNotFoundException("Fruit with the id " + id + " do not exist");
        }
        repository.deleteById(id);
    }
}
