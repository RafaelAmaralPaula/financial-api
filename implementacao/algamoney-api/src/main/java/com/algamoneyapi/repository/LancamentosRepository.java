package com.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoneyapi.model.Lancamento;

public interface LancamentosRepository  extends JpaRepository<Lancamento, Long>{
	
	Lancamento findByCodigo(Long codigo);
}
