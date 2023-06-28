package br.com.processo.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.processo.service.UserServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	private UserServiceImpl userServiceImpl;
	
	public JwtAuthFilter(JwtService jwtService, UserServiceImpl userServiceImpl) {
		super();
		this.jwtService = jwtService;
		this.userServiceImpl = userServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		
		System.err.println("TOKEN: " + authorization);
		
		if(authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			
			if (jwtService.tokenValido(token)) {
				String loginUsuario = jwtService.obterLoginUsuario(token);
				UserDetails usuarDetails = userServiceImpl.loadUserByUsername(loginUsuario);
				var user = new UsernamePasswordAuthenticationToken(usuarDetails, null, usuarDetails.getAuthorities());
				
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
