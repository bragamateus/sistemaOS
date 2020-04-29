package com.algaworks.osworks.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepsitory;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import com.algaworks.osworks.domain.service.dto.OrdemServicoDTO;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepsitory ordemServicoReposiroty;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDTO criar(@Valid @RequestBody OrdemServico ordemServico) {
		
		return toDto(gestaoOrdemServico.criar(ordemServico));
		
	}
	
	@GetMapping
	public List<OrdemServicoDTO> listar(){
		return toDtos(ordemServicoReposiroty.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long id){
		Optional<OrdemServico> ordemServico = ordemServicoReposiroty.findById(id);
		
		if(ordemServico.isPresent()) {
			OrdemServicoDTO ordemServicoDto = toDto(ordemServico.get());
			return ResponseEntity.ok(ordemServicoDto);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	private OrdemServicoDTO toDto(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDTO.class);
	}
	
	private List<OrdemServicoDTO> toDtos(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(ordemServico -> toDto(ordemServico))
				.collect(Collectors.toList());
	}
}
