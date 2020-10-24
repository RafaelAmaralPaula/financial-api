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
import com.algamoneyapi.model.Pessoa;
import com.algamoneyapi.repository.PessoasRepository;
import com.algamoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoasResources {

	@Autowired
	private PessoasRepository pessoasRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaService pessoaServices;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoasRepository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoaEncontrada = pessoaServices.buscarPeloCodigo(codigo);

		return ResponseEntity.ok(pessoaEncontrada);
	}

	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaEncontrada = pessoasRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaEncontrada.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaEncontrada);

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
		pessoasRepository.deleteById(codigo);
		return ResponseEntity.noContent().build();

	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> alterar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaEncontrada = pessoaServices.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaEncontrada);

	}

	@PutMapping("/{codigo}/ativo")
	public ResponseEntity<Void> alterarPropriedadeAtivo(@PathVariable Long codigo, @Valid @RequestBody Boolean ativo) {
		pessoaServices.atualizarPropredadeAtivo(codigo, ativo);
		return ResponseEntity.noContent().build();

	}

}
