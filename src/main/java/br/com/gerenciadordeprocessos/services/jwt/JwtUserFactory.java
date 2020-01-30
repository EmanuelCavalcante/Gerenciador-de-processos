/**
 * 
 */
package br.com.gerenciadordeprocessos.services.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.gerenciadordeprocessos.security.JwtUser;
import br.com.gerenciadordeprocessos.security.enums.PerfilEnum;
import br.com.gerenciadordeprocessos.usuario.modelos.Usuario;

/**
 * @author emanuel
 *
 */
public class JwtUserFactory {

	public JwtUserFactory() {

	}

	/**
	 * Converte e gera um JwtUser com base nos dados do usuario
	 * 
	 * @param usuario
	 * @return JwtUser
	 */
	public static JwtUser create(Usuario usuario) {
		return new JwtUser((long) usuario.getId(), usuario.getLogin(), usuario.getSenha(),
				mapToGrantedAuthority(usuario.getPerfil()));
	}

	/**
	 * Converte o perfil do usuario para o formato utilizado pelo Spring Security
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthority(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}
}
