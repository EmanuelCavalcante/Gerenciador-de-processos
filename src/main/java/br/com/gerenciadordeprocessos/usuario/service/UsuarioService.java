package br.com.gerenciadordeprocessos.usuario.service;

import java.util.List;
import java.util.Optional;

import br.com.gerenciadordeprocessos.usuario.modelos.Usuario;

/**
 * @author emanuel
 *
 */
public interface UsuarioService {

	public Optional<Usuario> buscarPorLogin(String email);

	public Usuario atualizarOuSalvar(Usuario usuario);

	public Usuario excluirUsuario(Integer id);

	public List<Usuario> listarUsuarios();

	public List<Usuario> listarTodosUsuarios();
}