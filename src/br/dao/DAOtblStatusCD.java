package br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.model.StatusCd;

public class DAOtblStatusCD {
	static Datasource dataSource;
	public Connection connection;

	/**
	 * @param dados da conexão.
	 */
	public DAOtblStatusCD(Datasource ds) {
		dataSource = ds;
	}

	public void createStatus(StatusCd cd) {
		Connection con = dataSource.getConnection();
		PreparedStatement stmp = null;
		try {
			stmp = con.prepareStatement(
					"INSERT INTO tblstatuscd(idStatusCD,status,idDisciplina,idCurso) VALUES (?,?,?,?)");
			stmp.setInt(1, cd.getIdStatuscd());
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
	 * @return lista com departamentos.
	 */
	public static ArrayList<StatusCd> listarDepartamento() {
		try {

			PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT * FROM tblstatuscd");
			ResultSet rs = ps.executeQuery();
			ArrayList<StatusCd> Lista = new ArrayList<>();
			while (rs.next()) {
				StatusCd cs = new StatusCd();
				cs.setIdStatuscd(rs.getInt("idDepartamento"));
				cs.setIdcurso(rs.getInt("idDepartamento"));
				cs.setIddisciplina(rs.getInt("idDepartamento"));
				cs.setStatus(rs.getString("nome"));
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
