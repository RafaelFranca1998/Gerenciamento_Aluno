package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import br.model.Disciplinas;

public class DAODisciplinas {
	private static Datasource dataSource;
	public Connection connection;
	public PreparedStatement stmt;

	// -------------------------------------------------------------------
	/**
	 * Cria uma nova instância da classe DAODisciplinas.
	 * 
	 * @param datasource
	 *            os dados da conexão.
	 */
	public DAODisciplinas(Datasource ds) {
		dataSource = ds;
	}

	// -------------------------------------------------------------------
	/**
	 * @param id
	 * @return lista com disciplinas.
	 */
	public static ArrayList<Disciplinas> procurarDisciplinas(int id) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("select * from tbldisciplina WHERE idDisciplina = ?");
			stmp.setInt(1, id);
			ResultSet rs = stmp.executeQuery();
			ArrayList<Disciplinas> Lista = new ArrayList<Disciplinas>();
			while (rs.next()) {
				Disciplinas cs = new Disciplinas();
				cs.setIdDisciplinas(rs.getInt("idDisciplina"));
				cs.setNome(rs.getString("nome"));
				Lista.add(cs);
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

	/**
	 * @return lista com disciplinas.
	 *
	 */
	public static ArrayList<Disciplinas> listarDisciplinas() {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tbldisciplina");
			ResultSet rs = ps.executeQuery();
			ArrayList<Disciplinas> Lista = new ArrayList<Disciplinas>();
			while (rs.next()) {
				Disciplinas cs = new Disciplinas();
				cs.setIdDisciplinas(rs.getInt("idDisciplina"));
				cs.setNome(rs.getString("nome"));
				Lista.add(cs);
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

	/**
	 * @param U
	 * Insere dados no banco de dados.
	 */
	public void create(Disciplinas U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		Random R = new Random();
		int id = R.nextInt(100);
		try {
			stmp = con.prepareStatement("INSERT INTO tbldisciplina(idDisciplina,nome) VALUES (?,?)");
			stmp.setInt(1, id);
			stmp.setString(2, U.getNome());
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
	 * Atualiza dados.
	 */
	public void Update(Disciplinas U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("UPDATE alunos SET nome = ? WHERE idDisciplina = ?");
			stmp.setString(1, U.getNome());
			stmp.setInt(2, U.getIdDisciplinas());
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
	 * @param D
	 *            ID a ser Deletado.
	 */
	public void delete(Disciplinas D) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("DELETE FROM alunos WHERE idDisciplina = ?");
			stmp.setInt(1, D.getIdDisciplinas());
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
}
