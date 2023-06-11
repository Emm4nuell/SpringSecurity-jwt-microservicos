package br.com.processo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.processo.dto.UsuarioDto;
import br.com.processo.entity.Usuario;
import br.com.processo.repository.UsuarioRepository;

@Service
public class UsuarioService implements UsuarioInterface {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	@Override
	public Usuario save(UsuarioDto usuarioDto) {
		UsuarioDto dto = new UsuarioDto();
		usuarioDto.setDataCadastro(LocalDateTime.now());
		Usuario usuario = dto.toUsuario(usuarioDto);
		return usuarioRepository.save(usuario);
	}

	@Override
	public UsuarioDto findByCpf(String cpf) {
		Optional<UsuarioDto> dto = usuarioRepository.findByCpf(cpf);
		return dto.get();
	}

	@Override
	public List<UsuarioDto> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDto> lista = usuarios.stream().map(list -> new UsuarioDto(list)).collect(Collectors.toList());
		return lista;
	}

}
