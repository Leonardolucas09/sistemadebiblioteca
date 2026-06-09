package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.dto.AutorRequestDTO;
import br.unisinos.library.dto.AutorResponseDTO;
import br.unisinos.library.entity.Autor;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
import br.unisinos.library.repository.AutorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorResponseDTO salvar (AutorRequestDTO autorDTO) {
        Autor autor = Autor.builder()
                    .nome(autorDTO.nome())
                    .build();

        return toResponseDTO(autorRepository.save(autor));
    }

    private AutorResponseDTO toResponseDTO(Autor autor) {
        return new AutorResponseDTO(
            autor.getId(),
            autor.getNome());
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional <Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor atualizar (Long id, Autor autorAtualizado) {
        Autor autor = buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado pelo id " + id));
        autor.setNome(autorAtualizado.getNome());
        return autorRepository.save(autor);
    }

    public void excluir (Long id) {
        Autor autor = buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado pelo id " + id));
        autorRepository.delete(autor);
    }

    public List<Autor> listarTodas() {
        return autorRepository.findAll();
    }

}
