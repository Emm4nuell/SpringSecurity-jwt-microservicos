package br.com.processo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.processo.dto.UsuarioDto;
import br.com.processo.service.UsuarioInterface;

@RestController
@RequestMapping("/acompanhamento-processo")
public class UsuarioController {

	@Autowired
	private UsuarioInterface usuarioInterface;

	@GetMapping
	public ResponseEntity<List<UsuarioDto>> findAll() {
		return ResponseEntity.ok().body(usuarioInterface.findAll());
	}

	@PostMapping
	public ResponseEntity<UsuarioDto> salvar(@Valid @RequestBody UsuarioDto dto) {
		usuarioInterface.save(dto);
		URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest().query("cpf={cpf}")
				.buildAndExpand(dto.getCpf()).toUri();

//		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		return ResponseEntity.created(headerLocation).build();
	}

	@PutMapping("{cpf}")
	public ResponseEntity<UsuarioDto> findByCpf(@PathVariable("cpf") String cpf) {
		UsuarioDto dto = usuarioInterface.findByCpf(cpf);
		return ResponseEntity.ok().body(dto);
	}

}
