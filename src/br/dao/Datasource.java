package br.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Rafael
 * Classe de Conex�o com o Banco de dados.
 */
public class Datasource {
	private String userName, hostName, dataBase, password;
	private int port;
	private Connection connection;

	public Datasource() {
		hostName = "localhost";
		port = 3306;
		dataBase = "db_gerenciamentodealuno";
		userName = "root";
		password = "admin";

		try {
			String url = "jdbc:mysql://" + hostName + ":" + port + "/" + dataBase + "?useSSL=false";
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("[Log] Conex�o Efetuada");
		} catch (SQLException e) {
			System.err.println("[ERRO!] N�o Foi Possivel Conectar ao Banco de dados: " + e);
		} catch (Exception e) {
			System.err.println("[ERRO!] N�o foi possivel conectar! ERRO GERAL: " + e);
		}

	}

	/**
	 * @return Instancia da Conex�o.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Fecha a Conex�o com o Banco.
	 */
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("[Log] Conex�o Encerrada!");
		} catch (SQLException e1) {
			System.err.println("[ERRO!] N�o fechou " + e1.getMessage());
		} catch (Exception e2) {
			System.err.println("[ERRO!] ERRO GERAL:  " + e2.getMessage());
		}

	}
}
