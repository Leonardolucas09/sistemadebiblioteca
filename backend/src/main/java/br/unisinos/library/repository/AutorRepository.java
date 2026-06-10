package br.unisinos.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisinos.library.entity.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>{

}
