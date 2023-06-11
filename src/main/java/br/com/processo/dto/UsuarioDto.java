package br.com.processo.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import br.com.processo.entity.Usuario;
import br.com.processo.enuns.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
	
	@NotBlank(message = "Campo nome é obrigatório!")
	private String nome;
	@NotBlank(message = "Campo cpf é obrigatório!")
//	@CPF(message = "Cpf inválido!")
	private String cpf;
	@Email(message = "Email inválido!")
	private String email;
	private Date dataNascimento;
	private LocalDateTime dataCadastro;
	private String processo;
	private String mensagem;
	private String senha;
	private RolesEnum roles;
	
	public Usuario toUsuario(UsuarioDto dto) {
		Usuario usuario = new Usuario(
				null,
				dto.nome, 
				dto.cpf, 
				dto.email,
				dto.dataNascimento, 
				dto.dataCadastro, 
				dto.processo, 
				dto.mensagem, 
				dto.senha, 
				dto.roles, 
				null);
		return usuario;
	}

	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		
	}
}
