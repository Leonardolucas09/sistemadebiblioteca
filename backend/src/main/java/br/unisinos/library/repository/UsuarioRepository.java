package br.unisinos.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unisinos.library.entity.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{

    

}
