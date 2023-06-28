package br.com.processo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.processo.dto.EmailDto;
import br.com.processo.dto.TokenDto;
import br.com.processo.dto.UsuarioDto;
import br.com.processo.entity.Usuario;
import br.com.processo.exception.SenhaInvalidaException;
import br.com.processo.jwt.JwtService;
import br.com.processo.service.UserServiceImpl;
import br.com.processo.service.UsuarioService;

/*Controller*/
@RestController
@RequestMapping("/acompanhamento-processo")
@CrossOrigin(origins = "/*")
public class UsuarioController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<UsuarioDto>> findAll() {
		return ResponseEntity.ok().body(usuarioService.findAll());
	}

	@PostMapping
	public ResponseEntity<UsuarioDto> salvar(@Valid @RequestBody UsuarioDto dto) {
		usuarioService.save(dto);
		URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest().query("cpf={cpf}")
				.buildAndExpand(dto.getCpf()).toUri();

//		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		return ResponseEntity.created(headerLocation).build();
	}

	@GetMapping(params = "cpf")
	public ResponseEntity<Usuario> findByCpf(@RequestParam("cpf") String cpf) {
		Usuario dto = usuarioService.findByCpf(cpf);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping("auth")
	public ResponseEntity<TokenDto> login(@RequestBody Usuario usuario) {

		try {
			UserDetails userDetails = userServiceImpl.autenticar(usuario);
			String token = jwtService.gerarToken(usuario);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenDto(usuario, token));

		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("enviarEmail")
	public void enviarEmail(@RequestBody EmailDto email) throws JsonProcessingException {
		usuarioService.sendEmail(email);
	}

}
