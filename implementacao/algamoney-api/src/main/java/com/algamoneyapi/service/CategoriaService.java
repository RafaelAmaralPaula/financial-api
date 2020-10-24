package com.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoneyapi.model.Categoria;
import com.algamoneyapi.repository.CategoriasRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriasRepository categoriasRepository;

	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaEncontrada = categoriasRepository.findByCodigo(codigo);

		if (categoriaEncontrada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(categoria, categoriaEncontrada,"codigo");
		return categoriasRepository.save(categoriaEncontrada);
	}
	
	public Categoria buscarPeloCodigo(Long codigo) {
		Categoria categoriaEncontrada = categoriasRepository.findByCodigo(codigo);
		
		if(categoriaEncontrada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoriaEncontrada;
	}

}
