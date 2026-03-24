package br.unisinos.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.entity.Livro;
import br.unisinos.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> salvar (@Valid @RequestBody Livro livro) {
        Livro livroSalvo = livroService.salvar(livro);
        return ResponseEntity.ok(livroSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar (@Valid @PathVariable Long id, @RequestBody Livro livro) {
        Livro livroAtualizado = livroService.atualizar(id, livro);
        
        return ResponseEntity.ok(livroAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorID(@PathVariable Long id) {
        
        Optional<Livro> livro = livroService.buscarPorId(id);

        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        }

        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        
        Optional<Livro> livro = livroService.buscarPorId(id);

        if (livro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        livroService.excluir(id);

        return ResponseEntity.noContent().build();
    }
    

}