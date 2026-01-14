package cat.itacademy.s04.t02.n02.fruit.exception;

public class ProviderNotFoundException extends RuntimeException{
    public ProviderNotFoundException(String message){
        super(message);
    }
}
