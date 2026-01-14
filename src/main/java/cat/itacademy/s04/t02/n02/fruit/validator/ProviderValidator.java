package cat.itacademy.s04.t02.n02.fruit.validator;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import org.springframework.stereotype.Component;

import java.util.IllegalFormatCodePointException;

@Component
public class ProviderValidator {

    public void validate (Provider provider){
        if (provider == null) {
        throw new IllegalArgumentException("Provider cannot be null");
    }
        if (provider.getName() == null || provider.getName().isBlank()){
            throw new IllegalArgumentException("Provider name cannot be empty");
            }
        if (provider.getCountry() == null || provider.getCountry().isBlank()){
            throw new IllegalArgumentException("Provider country cannot be empty");
        }
    }
}
