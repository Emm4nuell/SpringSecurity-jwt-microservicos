package br.com.processo.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.processo.entity.Usuario;

@Service
public class JwtService {
	
	/*Gerar token com esses dados*/
	public String gerarToken(Usuario usuario) {
		return JWT.create()
				.withSubject(usuario.getCpf())
				.withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
				.sign(Algorithm.HMAC512("ZWR1YXJkb2VtbWFudWVsc2lsdmFkZWZhcmlhcw=="));
	}
	
	/*Descriptografa o token*/
	public DecodedJWT obterToken(String token) {
		DecodedJWT decoder = JWT
				.require(Algorithm.HMAC512("ZWR1YXJkb2VtbWFudWVsc2lsdmFkZWZhcmlhcw=="))
				.build()
				.verify(token);
		return decoder;
	}
	
	/*Verificar se o token é válido*/
	public boolean tokenValido(String token) {
		try {
			DecodedJWT decoder = obterToken(token);
			System.err.println(token);
			Date dataExpiracao = decoder.getExpiresAt();
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
			
		} catch (TokenExpiredException e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) {
		return obterToken(token).getSubject();
	}

}
