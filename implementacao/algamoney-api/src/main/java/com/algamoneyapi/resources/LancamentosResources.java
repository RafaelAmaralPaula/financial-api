package com.algamoneyapi.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoneyapi.model.Lancamento;
import com.algamoneyapi.repository.LancamentosRepository;
import com.algamoneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentosResources {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;  
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> listar(){
		return ResponseEntity.status(HttpStatus.OK).body(lancamentosRepository.findAll());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamentoEncontrado = lancamentoService.buscarPeloCodigo(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(lancamentoEncontrado);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response){
		
		Lancamento lancamentoSalvo = lancamentosRepository.save(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

}
