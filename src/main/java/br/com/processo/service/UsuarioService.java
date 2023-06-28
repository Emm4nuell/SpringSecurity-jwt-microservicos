package br.com.processo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.processo.dto.EmailDto;
import br.com.processo.dto.UsuarioDto;
import br.com.processo.entity.Usuario;
import br.com.processo.queue.EnviarEmail;
import br.com.processo.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EnviarEmail enviarEmail;

	@Transactional
	public Usuario save(UsuarioDto usuarioDto) {
		UsuarioDto dto = new UsuarioDto();
		usuarioDto.setDataCadastro(LocalDateTime.now());
		Usuario usuario = dto.toUsuario(usuarioDto);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	public Usuario findByCpf(String cpf) {
		Optional<Usuario> usuOptional = usuarioRepository.findByCpf(cpf);
		return usuOptional.get();
	}

	public List<UsuarioDto> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDto> lista = usuarios.stream().map(list -> new UsuarioDto(list)).collect(Collectors.toList());
		return lista;
	}
	
	public void sendEmail(EmailDto dto) throws JsonProcessingException {
		enviarEmail.enviarEmail(dto);
	}

}
