package cat.itacademy.s04.t02.n02.fruit.model;

import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "fruits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String weightByKilos;
    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private Provider provider;
}
