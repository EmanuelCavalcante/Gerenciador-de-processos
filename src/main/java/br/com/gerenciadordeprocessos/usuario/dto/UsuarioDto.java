package br.com.gerenciadordeprocessos.usuario.dto;

import br.com.gerenciadordeprocessos.security.enums.PerfilEnum;
import br.com.gerenciadordeprocessos.usuario.modelos.Usuario;

public class UsuarioDto {

	public Integer id;
	public String login;
	public String email;
	public String telefone;
	public PerfilEnum perfil;
	public Boolean ativo;

	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
		this.perfil = usuario.getPerfil();
		this.ativo = usuario.getAtivo();
	}
}