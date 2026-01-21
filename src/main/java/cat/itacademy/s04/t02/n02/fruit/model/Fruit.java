package cat.itacademy.s04.t02.n02.fruit.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "fruits")
@Getter
@Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int weightInKilos;
    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private Long providerId;


    public Fruit(String name, int weightInKilos, Long providerId) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.providerId = providerId;
    }
}
