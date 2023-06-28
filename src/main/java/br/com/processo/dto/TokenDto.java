package br.com.processo.dto;

import br.com.processo.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
	
	private String cpf;
//	private String senha;
	private String token;
	
	public TokenDto(Usuario usuario, String token) {
		this.cpf = usuario.getCpf();
//		this.senha = usuario.getSenha();
		this.token = token;
	}

}
