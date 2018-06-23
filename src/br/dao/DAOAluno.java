package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.model.Aluno;

/**
 * @author Rafael
 *
 */

public class DAOAluno {
	private static Datasource dataSource;
	public Connection connection;
	public PreparedStatement stmt;

// ------------------------------------------------------------------------------------------------
	/**
	 * Cria uma nova instância da Classe DaoAluno.
	 * 
	 * @param datasource
	 *            os dados da conexão.
	 */
	public DAOAluno(Datasource ds) {
		dataSource = ds;
	}

// ------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna um Arraylist contendo os dados da tabela no Banco de
	 * Dados.
	 * 
	 * @return Lista com ID, Nome, Tipo, endereco, arquivo e nome do arquivo.
	 */
	public static ArrayList<Aluno> listarAlunos() {
		try {
			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT a. idAluno , a.nome ,a.rg,a.cpf ,a.dataNascimento, a.idCurso, "
																		+ " c.nome FROM tblcurso AS c JOIN tblaluno AS a ON (c.idCurso = a.idCurso) ;");
			ResultSet rs = ps.executeQuery();
			ArrayList<Aluno> Lista = new ArrayList<Aluno>();
			while (rs.next()) {
				Aluno BD = new Aluno();
				BD.setId_aluno(rs.getInt("idAluno"));
				BD.setNome(rs.getString("nome"));
				BD.setRg(rs.getString("rg"));
				BD.setCpf(rs.getString("cpf"));
				BD.setDataNascimento(rs.getString("dataNascimento"));
				BD.setidCurso(rs.getInt("idCurso"));
				BD.setNomeCurso(rs.getString("c.nome"));
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
// ------------------------------------------------------------------------------------------------
	/**
	 * @param id
	 * @return Lista com o os cursos onde o aluno está matriculado.
	 */
	public static ArrayList<Aluno> listarAlunosNoCurso(int idCurso) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"SELECT a. idAluno , a.nome ,  c.nome FROM tblcurso AS c JOIN tblaluno AS a ON (c.idCurso = a.idCurso) WHERE c.idCurso = ?");
			System.out.println("[Log] ID Curso:  " + idCurso);
			stmp.setInt(1, idCurso);
			ResultSet rs = stmp.executeQuery();
			ArrayList<Aluno> Lista = new ArrayList<Aluno>();
			while (rs.next()) {
				Aluno BD = new Aluno();
				BD.setId_aluno(rs.getInt("idAluno"));
				BD.setNome(rs.getString("nome"));
				BD.setNomeCurso(rs.getString("c.nome"));
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
// ------------------------------------------------------------------------------------------------
	/**
	 * @param curso
	 * @return Retorna lista de alunos matriculados no curso selecionado.
	 */
	public ArrayList<Aluno> listarPeloCurso(int idCurso) {
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT a. idAluno , a.nome ,a.rg,a.cpf ,a.dataNascimento, a.idCurso,"
							+ "  c.nome FROM tblcurso AS c JOIN tblaluno AS a ON (c.idCurso = a.idCurso) WHERE c.idCurso = ?");
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();
			ArrayList<Aluno> list = new ArrayList<Aluno>();
			while (rs.next()) {
				Aluno A = new Aluno();
				A.setId_aluno(rs.getInt("idAluno"));
				A.setNome(rs.getString("nome"));
				A.setDataNascimento(rs.getString("dataNascimento"));
				A.setRg(rs.getString("rg"));
				A.setCpf(rs.getString("cpf"));
				A.setidCurso(rs.getInt("idCurso"));
				A.setNomeCurso(rs.getString("c.nome"));
				list.add(A);
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
// ------------------------------------------------------------------------------------------------
	/**
	 * @param name
	 * @return Retorna lista de alunos selecionados pelo nome.
	 */
	public ArrayList<Aluno> listarPeloNome(String name) {
		try {
			String nomelike = name + "%";
			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT a.idAluno , a.nome ,a.rg,a.cpf ,a.dataNascimento, a.idCurso, c.nome "
					+ "FROM tblaluno AS a JOIN tblcurso AS c ON (c.idCurso = a.idCurso) WHERE a.nome LIKE ?");
			ps.setString(1, nomelike);
			ResultSet rs = ps.executeQuery();
			ArrayList<Aluno> list = new ArrayList<Aluno>();
			while (rs.next()) {
				Aluno BD = new Aluno();
				BD.setId_aluno(rs.getInt("idAluno"));
				BD.setNome(rs.getString("nome"));
				BD.setRg(rs.getString("rg"));
				BD.setCpf(rs.getString("a.cpf"));
				BD.setDataNascimento(rs.getString("dataNascimento"));
				BD.setidCurso(rs.getInt("idCurso"));
				BD.setNomeCurso(rs.getString("c.nome"));
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
// ------------------------------------------------------------------------------------------------
	/**
	 * @param id
	 * @return Seleciona aluno pelo id.
	 */
	public ArrayList<Aluno> selecionarId(int id) {
		ArrayList<Aluno> list = new ArrayList<Aluno>();
		try {
			PreparedStatement ps = dataSource.getConnection()
					.prepareStatement("SELECT * FROM tblaluno WHERE idAluno = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Aluno BD = new Aluno();
				BD.setId_aluno(rs.getInt("idAluno"));
				BD.setNome(rs.getString("nome"));
				BD.setRg(rs.getString("rg"));
				BD.setCpf(rs.getString("cpf"));
				BD.setDataNascimento(rs.getString("dataNascimento"));
				BD.setidCurso(rs.getInt("idCurso"));
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
//-------------------------------------------------------------------------------------------------
	/**
	 * Insere dados no banco de dados.
	 * 
	 * @param U
	 *            os dados do aluno.
	 */
	public void criar(Aluno U) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"INSERT INTO tblaluno(idAluno,nome,rg,cpf,dataNascimento,idCurso) VALUES (?,?,?,?,?,?)");
			stmp.setInt(1, U.getId_aluno());
			stmp.setString(2, U.getNome());
			stmp.setString(3, U.getRg());
			stmp.setString(4, U.getCpf());
			stmp.setString(5, U.getDataNascimento());
			stmp.setInt(6, U.getidCurso());
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
// ------------------------------------------------------------------------------------------------
	/**
	 * @param aluno
	 * 
	 * Atualuza dados do aluno no banco de dados.
	 */
	public void atualizar(Aluno aluno) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"UPDATE tblaluno SET nome = ?, rg = ?, cpf = ?, dataNascimento = ?, idCurso = ? WHERE idAluno = ?");
			stmp.setString(1, aluno.getNome());
			stmp.setString(2, aluno.getRg());
			stmp.setString(3, aluno.getCpf());
			stmp.setString(4, aluno.getDataNascimento());
			stmp.setInt(5, aluno.getidCurso());
			stmp.setInt(6, aluno.getId_aluno());
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
// ------------------------------------------------------------------------------------------------
	/**
	 * Deleta dados baseado no ID passado como parâmetro.
	 * 
	 * @param aluno
	 *            ID a ser Deletado.
	 */
	public void deletar(Aluno aluno) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement("DELETE FROM tblaluno WHERE idAluno = ?");
			stmp.setInt(1, aluno.getId_aluno());
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
