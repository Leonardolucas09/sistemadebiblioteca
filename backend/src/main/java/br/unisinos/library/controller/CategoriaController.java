package br.unisinos.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.dto.CategoriaRequestDTO;
import br.unisinos.library.dto.CategoriaResponseDTO;
import br.unisinos.library.entity.Categoria;
import br.unisinos.library.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO categoriaSalvo = categoriaService.salvar(categoriaDTO);
        return ResponseEntity.ok(categoriaSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        CategoriaResponseDTO categoriaAtualizado = categoriaService.atualizar(id, categoriaDTO);
        return ResponseEntity.ok(categoriaAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Long id) {

        Optional categoria = categoriaService.buscarPorId(id);

        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {

        Optional categoria = categoriaService.buscarPorId(id);

        if (categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
