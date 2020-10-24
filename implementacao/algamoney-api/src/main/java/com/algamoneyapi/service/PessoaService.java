package com.algamoneyapi.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoneyapi.model.Pessoa;
import com.algamoneyapi.repository.PessoasRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoasRepository pessoasRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaEncontrada = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaEncontrada , "codigo");
		return pessoasRepository.save(pessoaEncontrada);
	}

	public void atualizarPropredadeAtivo(Long codigo, @Valid Boolean ativo) {
		Pessoa pessoaEncontrada = buscarPeloCodigo(codigo);
		pessoaEncontrada.setAtivo(ativo);
		pessoasRepository.save(pessoaEncontrada);
	}

	public Pessoa buscarPeloCodigo(Long codigo) {
		Pessoa pessoaEncontrada = pessoasRepository.findByCodigo(codigo);

		if (pessoaEncontrada == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return pessoaEncontrada;
	}
}
