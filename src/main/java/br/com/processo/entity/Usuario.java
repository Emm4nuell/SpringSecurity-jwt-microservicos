package br.com.processo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.processo.enuns.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
//	@Column(name = "cpf", length = 11, unique = true)
	private String cpf;
	private String email;
	private Date dataNascimento;
	private LocalDateTime dataCadastro;
	private String processo;
	private String mensagem;
	private String senha;
	@Enumerated(EnumType.STRING)
	private RolesEnum roles;

	@OneToMany(mappedBy = "usuario")
	private List<Processo> processos;

}
