package br.com.processo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroProcesso;
	private String orgJulgador;
	private String classe;
	private String assunto;
	private Date dataDistribuicao;
	private String valorDaAcao;
	private String status;
	private String segredoJustica;
	private String transitoJulgado;
	private String assistenciaGratuita;
	private String localizador;
	private String assuntosSecundario;
	private String processoVinculado;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "processo")
	private List<Movimentacao> movimentacoes;
	

}
