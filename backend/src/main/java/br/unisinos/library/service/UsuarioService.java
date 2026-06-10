package br.unisinos.library.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.dto.UsuarioRequestDTO;
import br.unisinos.library.dto.UsuarioResponseDTO;
import br.unisinos.library.entity.Usuario;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO salvar(UsuarioRequestDTO usuarioDTO) {

        Usuario usuario = Usuario.builder()
                        .nome(usuarioDTO.nome())
                        .build();
        
        return toResponseDTO(usuarioRepository.save(usuario));
    }

    private UsuarioResponseDTO toResponseDTO (Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome());
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RegraLibraryException("Usuário não encontrado"));
        
        usuario.setNome(usuarioAtualizado.getNome());

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

}
