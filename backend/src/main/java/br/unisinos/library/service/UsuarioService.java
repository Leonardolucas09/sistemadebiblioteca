package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.entity.Usuario;
import br.unisinos.library.repository.UsuarioRepository;
import br.unisinos.library.exception.UsuarioNaoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.buscarComFiltros(usuario.getEmail(), usuario.getNome());
        if (usuarioExistente != null) {
            throw new RuntimeException("Usuário já existe com o mesmo email e nome");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        // Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        
        /* Está dando erro na linha acima, por isso coloquei a RuntimeException, mas o certo é a UsuarioNaoEncontradoException */
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + id));

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());

        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario() {
        usuarioRepository.deleteAll();
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

}
