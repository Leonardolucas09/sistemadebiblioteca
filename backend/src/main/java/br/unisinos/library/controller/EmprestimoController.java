package br.unisinos.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.dto.EmprestimoRequestDTO;
import br.unisinos.library.dto.EmprestimoResponseDTO;
import br.unisinos.library.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> listarTodos(
        @RequestParam(required = false) String busca,
        @RequestParam(required = false) Long categoriaId,
        @RequestParam(required = false) String editora) {
        return ResponseEntity.ok(emprestimoService.listarTodos(busca, categoriaId, editora));
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> emprestar(@Valid @RequestBody EmprestimoRequestDTO dto) {
        EmprestimoResponseDTO emprestimo = emprestimoService.emprestar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponseDTO> devolver(@PathVariable Long id) {
        EmprestimoResponseDTO emprestimo = emprestimoService.devolver(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(emprestimoService.listarPorUsuario(usuarioId));
    }

}
