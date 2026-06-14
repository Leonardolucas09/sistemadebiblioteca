package br.unisinos.library.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.library.dto.PerfilUsuarioResponseDTO;
import br.unisinos.library.dto.UsuarioRequestDTO;
import br.unisinos.library.dto.UsuarioResponseDTO;
import br.unisinos.library.entity.Usuario;
import br.unisinos.library.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar (@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO usuarioSalvo = usuarioService.salvar(usuarioDTO);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id:\\d+}/perfil")
    public ResponseEntity<PerfilUsuarioResponseDTO> buscarPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPerfil(id));
    }
    

}
