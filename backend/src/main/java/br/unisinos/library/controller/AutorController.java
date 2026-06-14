package br.unisinos.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.dto.AutorRequestDTO;
import br.unisinos.library.dto.AutorResponseDTO;
import br.unisinos.library.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> salvar(@Valid @RequestBody AutorRequestDTO autorDTO) {
        AutorResponseDTO autorSalvo = autorService.salvar(autorDTO);
        return ResponseEntity.ok(autorSalvo);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id) {

        Optional autor = autorService.buscarPorId(id);

        if (autor.isPresent()) {
            return ResponseEntity.ok(autor.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {

        Optional autor = autorService.buscarPorId(id);

        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
