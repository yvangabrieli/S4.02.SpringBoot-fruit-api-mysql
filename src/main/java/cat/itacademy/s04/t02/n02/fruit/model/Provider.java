package cat.itacademy.s04.t02.n02.fruit.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Entity
@Table (name = "providers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "names")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


public class Provider {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String country;


    public Provider (String name, String country){
        this.name = name;
        this.country = country;
    }

    public Provider (Long id){
        this.id = id ;
    }
}