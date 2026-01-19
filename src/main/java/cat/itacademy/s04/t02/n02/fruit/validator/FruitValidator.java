package cat.itacademy.s04.t02.n02.fruit.validator;

import cat.itacademy.s04.t02.n02.fruit.exception.BusinessRuleException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;

public class FruitValidator {
    private FruitRepository repository;
    private static final int MAX_WEIGHT = 1000;
    private static final int MIN_NAME_LENGTH = 4;

    public void validate (Fruit fruit) {
        if (fruit == null){
            throw new IllegalArgumentException ("Fruit cannot be null");
        }
        if (fruit.getName() == null || fruit.getName().isBlank()){
            throw new IllegalArgumentException("Fruit name cannot be empty");
        }
        if (fruit.getWeightInKilos() > MAX_WEIGHT) {
            throw new BusinessRuleException("Fruit weight cannot exceed 1000 kilos");
        }
        if (fruit.getName().length() < MIN_NAME_LENGTH) {
            throw new BusinessRuleException("Fruit name must have at least 4 characters");
        }
        if(fruit.getProviderId() == null || fruit.getProviderId().describeConstable().isEmpty()) {
            throw new ProviderNotFoundException("Provider_Id cannot be null or empty");
        }
    }
}
