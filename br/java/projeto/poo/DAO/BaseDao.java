package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDao <VOData> implements BasicInterfaceDao <VOData> {
    private final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private final String user = "postgres";
    private final String password = "1234";

    private Connection con;

    // inicializa a conexão com o banco de dados
    public Connection getConnection() {
        try {
            if (con == null) {
                con = DriverManager.getConnection(url, user, password);
                return con;
            }
            return con;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        con = null;
    }
}
