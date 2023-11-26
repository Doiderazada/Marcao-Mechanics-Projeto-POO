package br.java.projeto.poo.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BasicInterfaceDao <VOData> {
    public boolean inserir(VOData data) throws SQLException;
    public boolean deletar(VOData id) throws SQLException;
    public ResultSet buscarPorId(VOData id) throws SQLException;
    public ResultSet listar() throws SQLException;
    public boolean atualizar(VOData data) throws SQLException;
    
}
