package br.unisinos.library.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//especifica que a classe Livro representa uma entidade no banco de dados
@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Livro {
    
    //especifica que o Long id é uma chave identificadora (PK)
    @Id
    //especifica que a chave identificadora (PK) vai ser um número sequencial 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //especifica que nome é uma coluna da tabela, que não pode ser nulo e de tamanho máximo = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String nomeAutor;

    @Column(nullable = false, length = 50)
    private String editora;

    @Column(nullable = false)
    private String numeroPaginas;




}
