package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.model.Curso;
import br.model.Disciplinas;
import br.model.StatusCd;

public class DAOCursos {
	private static Datasource dataSource;
	public Connection connection;

	/**
	 * @param ds
	 * 
	 * Cria instancia da Classe DAOCursos.
	 */
	public DAOCursos(Datasource ds) {
		DAOCursos.dataSource = ds;
	}

	/**
	 * @param curso
	 * 
	 * Insere dados do Curso no banco de dados.
	 */
	public void createCurso(Curso curso) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("INSERT INTO tblcurso(idCurso,nome,idDepartamento) VALUES (?,?,?)");
			stmp.setInt(1, curso.getIdCurso());
			stmp.setString(2, curso.getNome());
			stmp.setInt(3, curso.getIdDepartamento());
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
	 * @param id
	 * 
	 * Deleta o um curso no banco de dados.
	 */
	public static void deleteCurso(Curso curso) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("delete from tblstatuscd where idCurso = ?");
			stmp.setInt(1, curso.getIdCurso());
			stmp.executeUpdate();
			stmp =  null;
			stmp = con.prepareStatement("delete from tblcurso where idCurso = ?");
			stmp.setInt(1, curso.getIdCurso());
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
	 * @param Objeto StatusCD
	 * 
	 * Adiciona Informações a tabela StatusCD.
	 */
	public void createTblStatusCd(StatusCd cd) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"INSERT INTO tblstatuscd(idStatusCS,status,idDisciplina,idCurso) VALUES (?,?,?,?)");
			stmp.setInt(1, cd.getIdcurso());
			stmp.setString(2, cd.getStatus());
			stmp.setInt(3, cd.getIddisciplina());
			stmp.setInt(4, cd.getIdcurso());
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
	 * @return Lista Todos os Cursos.
	 */
	public static ArrayList<Curso> listarCursos() {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tblcurso");
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
	public static ArrayList<Curso> listarCursos(String curso) {
		try {
			Curso cs = new Curso();			
			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tblcurso where nome = ?");
			ps.setString(1, curso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Curso> Lista = new ArrayList<Curso>();
			while (rs.next()) {
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

	/**
	 * @param idCurso
	 * @return Alunos Matriculados no Curso.
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

	/**
	 * @param idCurso
	 * @return Lista disciplinas Obrigatórias.
	 */
	public static ArrayList<Disciplinas> listarDisciplinasObrigatorias(int idCurso) {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement(
					"SELECT d.nome FROM tblcurso AS c JOIN tblstatuscd AS s JOIN tbldisciplina AS d ON"
							+ "(s.idCurso = c.idCurso and s.idDisciplina = d.idDisciplina) WHERE s.status = 'O' AND c.idCurso = ?");
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Disciplinas> Lista = new ArrayList<Disciplinas>();
			while (rs.next()) {
				Disciplinas cs = new Disciplinas();
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
	 * @param idCurso
	 * @return Lista Disciplinas Optativas.
	 */
	public static ArrayList<Disciplinas> listarDisciplinasOptativas(int idCurso) {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement(
					"SELECT d.nome FROM tblcurso AS c JOIN tblstatuscd AS s JOIN tbldisciplina AS d ON "
							+ "(s.idCurso = c.idCurso and s.idDisciplina = d.idDisciplina) WHERE s.status = 'L' AND c.idCurso = ?;");
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Disciplinas> Lista = new ArrayList<Disciplinas>();
			while (rs.next()) {
				Disciplinas cs = new Disciplinas();
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

	public void search(int id) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("select * from tblaluno WHERE idAluno = ?");
			stmp.setInt(1, id);
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
}