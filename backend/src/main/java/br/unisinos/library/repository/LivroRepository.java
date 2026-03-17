package br.unisinos.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisinos.library.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

}
