package br.unisinos.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.entity.Categoria;
import br.unisinos.library.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity salvar(@Valid @RequestBody Categoria categoria) {
        Categoria categoriaSalvo = categoriaService.salvar(categoria);
        return ResponseEntity.ok(categoriaSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        Categoria categoriaAtualizado = categoriaService.atualizar(id, categoria);
        return ResponseEntity.ok(categoriaAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
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