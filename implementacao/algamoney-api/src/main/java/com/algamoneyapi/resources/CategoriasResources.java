package com.algamoneyapi.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoneyapi.model.Categoria;
import com.algamoneyapi.repository.CategoriasRepository;
import com.algamoneyapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriasResources {

	@Autowired
	private CategoriasRepository categoriasRepository;

	@Autowired
	private CategoriaService categoriasService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return categoriasRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriasRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriasService.buscarPeloCodigo(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(categoria);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
		categoriasRepository.deleteById(codigo);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> alterar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria) {
		Categoria categoriaEncontrada = categoriasService.atualizar(codigo, categoria);

		return ResponseEntity.ok(categoriaEncontrada);
	}

}