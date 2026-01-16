package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.model.Fruit;

import java.util.List;

public interface FruitService {
    Fruit createFruit (Fruit fruit);
    List<Fruit> getAllFruits();
    Fruit getFruitById(Long id);
    Fruit updateFruit(Long id, Fruit fruit);
    void deleteFruit(Long id);
}
