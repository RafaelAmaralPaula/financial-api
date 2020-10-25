package com.algamoneyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoneyapi.model.Lancamento;
import com.algamoneyapi.repository.LancamentosRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentosRepository lancamentosRepository;

	public Lancamento buscarPeloCodigo(Long codigo) {
		Lancamento lancamentoEncontrado = lancamentosRepository.findByCodigo(codigo);

		if (lancamentoEncontrado == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return lancamentoEncontrado;
	}

}
