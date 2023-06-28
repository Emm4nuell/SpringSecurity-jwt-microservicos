package br.com.processo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.processo.entity.Usuario;
import br.com.processo.exception.SenhaInvalidaException;
import br.com.processo.repository.UsuarioRepository;

@Service
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getCpf());
		
		/*Comparador de senha*/
		boolean comparador = encoder.matches(usuario.getSenha(), user.getPassword());
		if(comparador) {
			return user;
		}else {			
			throw new SenhaInvalidaException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByCpf(cpf)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados!"));
		
		return User.builder()
				.username(usuario.getCpf())
				.password(usuario.getSenha())
				.roles(usuario.getRoles().name())
				.build();
	}

}
