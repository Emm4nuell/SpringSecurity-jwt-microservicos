package br.com.processo.service;

import java.util.List;

import br.com.processo.dto.UsuarioDto;
import br.com.processo.entity.Usuario;

public interface UsuarioInterface {

	public Usuario save(UsuarioDto usuario);
	
	public UsuarioDto findByCpf(String cpf);
	
	public List<UsuarioDto> findAll();
}
