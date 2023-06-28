package br.com.processo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.processo.jwt.JwtAuthFilter;
import br.com.processo.jwt.JwtService;
import br.com.processo.service.UserServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserServiceImpl userServiceImpl;

	/* Criptografia */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, userServiceImpl);
	}

	/* Quem esta fazendo o serviço de autenticação é o JwtAuthFilter */
//	/* Autenticação */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
//	}

	/* Autorização */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/acompanhamento-processo/**").permitAll()
				.antMatchers(HttpMethod.POST, "/acompanhamento-processo/auth/**").permitAll()
				.anyRequest().permitAll().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
//		.antMatchers(HttpMethod.POST, "/acompanhamento-processo/auth/**").hasAnyRole("ADMINISTRADOR")

	}

}
