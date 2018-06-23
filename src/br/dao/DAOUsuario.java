package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.model.Usuario;

public class DAOUsuario {
	private static Datasource dataSource;
	public Connection connection;
	public PreparedStatement stmt;

	// -------------------------------------------------------------------
	/**
	 * Cria uma nova instância da Classe DAOUsuario.
	 * 
	 * @param datasource
	 *            os dados da conexão.
	 */
	public DAOUsuario(Datasource ds) {
		dataSource = ds;
	}

	// -------------------------------------------------------------------
	/**
	 * Metodo que retorna um Arraylist contendo os dados da tabela no Banco de
	 * Dados.
	 * 
	 * @return Lista de alunos.
	 */
	public static ArrayList<Usuario> listAlunos() {
		try {
			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tblusuario");
			ResultSet rs = ps.executeQuery();
			ArrayList<Usuario> Lista = new ArrayList<Usuario>();
			while (rs.next()) {
				Usuario BD = new Usuario();
				BD.setIdUsuario(rs.getInt("id"));
				BD.setNome(rs.getString("nome"));
				BD.setPerfil(rs.getString("perfil"));
				BD.setUsuario(rs.getString("usuario"));
				BD.setSenha(rs.getString("senha"));
				Lista.add(BD);
			}
			return Lista;
		} catch (SQLException e) {
			System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERRO!] ERRO GERAL: " + e.getMessage());
		} finally {
			dataSource.closeConnection();
		}
		return null;
	}
	// -------------------------------------------------------------------

	/**
	 * @param curso
	 * @return lista de alunos por curso.
	 */
	public ArrayList<Usuario> shortByCurso(String curso) {
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT * FROM tblusuario WHERE idCurso = ?");
			ps.setString(1, curso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Usuario> list = new ArrayList<Usuario>();
			while (rs.next()) {
				Usuario BD = new Usuario();
				BD.setIdUsuario(rs.getInt("id"));
				BD.setNome(rs.getString("nome"));
				BD.setPerfil(rs.getString("perfil"));
				BD.setUsuario(rs.getString("usuario"));
				BD.setSenha(rs.getString("senha"));
				list.add(BD);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERRO!] ERRO GERAL: " + e.getMessage());
		} finally {
			dataSource.closeConnection();
		}
		return null;
	}

	/**
	 * @param name
	 * @return lista de alunos com o nome informado.
	 */
	public ArrayList<Usuario> shortByName(String name) {
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT * FROM tblusuario WHERE nome = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			ArrayList<Usuario> list = new ArrayList<Usuario>();
			while (rs.next()) {
				Usuario BD = new Usuario();
				BD.setIdUsuario(rs.getInt("id"));
				BD.setNome(rs.getString("nome"));
				BD.setPerfil(rs.getString("perfil"));
				BD.setUsuario(rs.getString("usuario"));
				BD.setSenha(rs.getString("senha"));
				list.add(BD);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERRO!] ERRO GERAL: " + e.getMessage());
		} finally {
			dataSource.closeConnection();
		}
		return null;
	}

	/**
	 * @param id
	 * @return lista com o id selecionado.
	 */
	public ArrayList<Usuario> selecionarId(int id) {
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT * FROM tblusuario WHERE idAluno = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Usuario> list = new ArrayList<Usuario>();
			while (rs.next()) {
				Usuario BD = new Usuario();
				BD.setIdUsuario(rs.getInt("id"));
				BD.setNome(rs.getString("nome"));
				BD.setPerfil(rs.getString("perfil"));
				BD.setUsuario(rs.getString("usuario"));
				BD.setSenha(rs.getString("senha"));
				list.add(BD);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERRO!] ERRO GERAL: " + e.getMessage());
		} finally {
			dataSource.closeConnection();
		}
		return null;
	}

	/**
	 * Insere dados no banco de dados.
	 * 
	 * @param U
	 *            os dados do usuário.
	 */
	public void create(Usuario U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con
					.prepareStatement("INSERT INTO tblusuario(idUsuario,nome,perfil,usuario,senha) VALUES (?,?,?,?,?)");
			stmp.setInt(1, U.getIdUsuario());
			stmp.setString(2, U.getNome());
			stmp.setString(3, U.getPerfil());
			stmp.setString(4, U.getUsuario());
			stmp.setString(5, U.getSenha());
			stmp.executeUpdate();
			System.out.println("[Log] Sucesso!");
		} catch (SQLException u) {
			throw new RuntimeException(u);
		} finally {
			try {
				dataSource.closeConnection();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
			}
		}
	}

	/**
	 * @param U
	 * Atualiza Dados no banco de dados.
	 */
	public void Update(Usuario U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"UPDATE tblusuario SET nome = ?, perfil = ?, usuario = ?, senha = ? WHERE idUsuario = ?");
			stmp.setString(1, U.getNome());
			stmp.setString(2, U.getPerfil());
			stmp.setString(3, U.getUsuario());
			stmp.setString(4, U.getSenha());
			stmp.setInt(5, U.getIdUsuario());
			stmp.executeUpdate();
			System.out.println("[Log] Sucesso!");
		} catch (SQLException u) {
			throw new RuntimeException(u);
		} finally {
			try {
				dataSource.closeConnection();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
			}
		}

	}

	// -------------------------------------------------------------------
	/**
	 * Deleta dados baseado no ID passado como parâmetro.
	 * 
	 * @param U
	 *            ID a ser Deletado.
	 */
	public void delete(Usuario U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("DELETE FROM tblusuario WHERE idAluno = ?");
			stmp.setInt(1, U.getIdUsuario());
			stmp.executeUpdate();
			System.out.println("[Log] Sucesso!");
		} catch (SQLException u) {
			throw new RuntimeException(u);
		} finally {
			try {
				dataSource.closeConnection();
				con.close();
			} catch (SQLException e) {
				System.err.println("[ERRO!] Erro na Listagem " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param login
	 * @param senha
	 * @return Checa se o usuario existe no banco de dados.
	 */
	public Boolean checkLogin(String login, String senha) {
		Boolean check = false;
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT * FROM tblusuario WHERE usuario = ? AND senha = ?");
			ps.setString(1, login);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				check = true;
			}
		} catch (SQLException e) {
			System.err.println("Erro na Listagem " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro Geral " + e.getMessage());
		}
		return check;
	}
}
