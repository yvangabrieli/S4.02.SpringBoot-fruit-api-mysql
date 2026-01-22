package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.mapper.FruitMapper;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.service.FruitService;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {
    private final FruitService service;
    private final FruitMapper mapper;
    private final ProviderService providerService;

    public FruitController(FruitService service, FruitMapper mapper, ProviderService providerService) {
        this.service = service;
        this.mapper = mapper;
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<FruitResponseDTO> createFruit(@Valid @RequestBody FruitRequestDTO dto) {
        Provider provider = providerService.getProviderById(dto.getProviderId());
        Fruit saved = service.createFruit(mapper.toEntity(dto, provider));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDTO(saved));
    }

    @GetMapping
    public List<FruitResponseDTO> getALl() {
        return service.getAllFruits()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public FruitResponseDTO getById(@PathVariable Long id) {
        Fruit fruit = service.getFruitById(id);
        return mapper.toDTO(fruit);
    }

    @PutMapping("/{id}")
    public FruitResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody FruitRequestDTO dto) {
        Provider provider = providerService.getProviderById(dto.getProviderId());

        Fruit updated = service.updateFruit(
                id,
                mapper.toEntity(dto, provider));
        return mapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}