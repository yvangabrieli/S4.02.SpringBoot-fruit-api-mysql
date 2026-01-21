package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.mapper.ProviderMapper;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService service;
    private final ProviderMapper mapper;

    public ProviderController(ProviderService service, ProviderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProviderResponseDTO> create(@Valid @RequestBody ProviderRequestDTO dto) {
        Provider saved = service.createProvider(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(saved));
    }

    @GetMapping
    public List<ProviderResponseDTO> getAll() {
        return service.getAllProviders()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @PutMapping("/{id}")
    public ProviderResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequestDTO dto) {
        Provider updated = service.updateProvider(id, mapper.toEntity(dto));
        return mapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
