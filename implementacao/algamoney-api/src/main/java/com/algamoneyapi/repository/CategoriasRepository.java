package com.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoneyapi.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Long> {

}
