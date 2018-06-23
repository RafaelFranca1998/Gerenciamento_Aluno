package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import br.model.Curso;
import br.model.Departamento;

public class DAODepartamento {
	private static Datasource dataSource;
	public Connection connection;

	/**
	 * @param Datasource
	 *            ds. Cria nova instância da Classe DAODepartamento.
	 */
	public DAODepartamento(Datasource ds) {
		DAODepartamento.dataSource = ds;
	}

	public static void create(Departamento U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		Random R = new Random();
		int id = R.nextInt((200 - 100) + 1) + 100;
		try {
			stmp = con.prepareStatement("INSERT INTO tbldepartamento(idDepartamento,nome,descricao) VALUES (?,?,?)");
			stmp.setInt(1, id);
			stmp.setString(2, U.getNome());
			stmp.setString(3, U.getDescricao());
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
	 * @param Departamento
	 *            D. Remove um departamento.
	 */
	public void delete(Departamento D) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("DELETE FROM tbldepartamento WHERE idDepartamento = ?");
			stmp.setInt(1, D.getIddepartamento());
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
	 * @return lista com Departamentos.
	 */
	public static ArrayList<Departamento> listarDepartamento() {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tbldepartamento");
			ResultSet rs = ps.executeQuery();
			ArrayList<Departamento> Lista = new ArrayList<Departamento>();
			while (rs.next()) {
				Departamento cs = new Departamento();
				cs.setIddepartamento(rs.getInt("idDepartamento"));
				cs.setNome(rs.getString("nome"));
				cs.setDescricao(rs.getString("descricao"));
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
	 * @param idCurso
	 * @return lista com alunos do curso.
	 */
	public static ArrayList<Curso> listarAlunosCurso(int idCurso) {
		try {

			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT c.nome, a.nome FROM tblcurso AS c JOIN tblaluno "
							+ "AS a ON (c.idCurso = a.idCurso) WHERE c.idCurso = ?");
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Curso> Lista = new ArrayList<Curso>();
			while (rs.next()) {
				Curso cs = new Curso();
				cs.setIdCurso(rs.getInt("idCurso"));
				cs.setNome(rs.getString("nome"));
				cs.setIdDepartamento(rs.getInt("idDepartamento"));
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
}