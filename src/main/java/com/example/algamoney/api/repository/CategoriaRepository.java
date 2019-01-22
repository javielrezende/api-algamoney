package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Categoria;

// Esta extendendo outra interface com varios metodos prontos como findAll, save, delete, getOne, etc...
// como parametros recebera primeiro a entidade que estamos trabalhando (estara trabalhando com Categoria) e por ultimo o tipo da chave primaria
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
