package br.unisinos.library.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.unisinos.library.dto.EmprestimoResponseDTO;
import br.unisinos.library.dto.PerfilUsuarioResponseDTO;
import br.unisinos.library.dto.UsuarioRequestDTO;
import br.unisinos.library.dto.UsuarioResponseDTO;
import br.unisinos.library.entity.Emprestimo;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.entity.Usuario;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.EmprestimoRepository;
import br.unisinos.library.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoRepository emprestimoRepository;

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

    public PerfilUsuarioResponseDTO buscarPerfil(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("UsuÃ¡rio nÃ£o encontrado com o id: " + id));

        EmprestimoResponseDTO ultimoEmprestimo = emprestimoRepository
            .findByUsuarioIdOrderByDataEmprestimoDescIdDesc(id)
            .stream()
            .findFirst()
            .map(this::toEmprestimoResponseDTO)
            .orElse(null);

        return new PerfilUsuarioResponseDTO(usuario.getId(), usuario.getNome(), ultimoEmprestimo);
    }

    private EmprestimoResponseDTO toEmprestimoResponseDTO(Emprestimo emprestimo) {
        Livro livro = emprestimo.getLivro();

        return new EmprestimoResponseDTO(
            emprestimo.getId(),
            emprestimo.getUsuario().getId(),
            emprestimo.getUsuario().getNome(),
            livro.getId(),
            livro.getTitulo(),
            livro.getIsbn(),
            livro.getAutor() != null ? livro.getAutor().getNome() : null,
            livro.getEditora(),
            livro.getCategoria() != null ? livro.getCategoria().getId() : null,
            livro.getCategoria() != null ? livro.getCategoria().getNome() : null,
            emprestimo.getDataEmprestimo(),
            emprestimo.getDataPrevistaDevolucao(),
            emprestimo.getDataDevolucao(),
            emprestimo.getDevolvido());
    }

}
