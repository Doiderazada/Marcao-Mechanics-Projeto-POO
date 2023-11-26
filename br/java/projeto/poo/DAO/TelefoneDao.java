package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.models.VO.TelefoneVO;

public class TelefoneDao extends BaseDao <TelefoneVO>{
    Connection db;

    public TelefoneDao() {
        db = this.getConnection();
    }

    public boolean inserir(TelefoneVO telefone) throws SQLException {
        String query = "INSERT INTO telefones (cpfCliente, cpfFuncionario, numero) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, telefone.getCpfCliente());
            ps.setString(2, telefone.getCpfFuncionario());
            ps.setString(3, telefone.getNumero());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(TelefoneVO TelefoneVO) throws SQLException {
        String query = "DELETE FROM telefones WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, TelefoneVO.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean atualizar(TelefoneVO telefone) throws SQLException {
        String query = "UPDATE telefones SET cpfCliente = ?, cpfFuncionario = ?, numero = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(4, telefone.getId());
            ps.setString(1, telefone.getCpfCliente());
            ps.setString(2, telefone.getCpfFuncionario());
            ps.setString(3, telefone.getNumero());
            ps.executeUpdate();
            return true; 

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(TelefoneVO telefone) throws SQLException {
        String query = "Select * from telefones where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, telefone.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorCpfCliente(TelefoneVO telefone) throws SQLException {
        String query = "Select * from telefones where cpfCliente = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1,  telefone.getCpfCliente());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorCpfFuncionario(TelefoneVO telefone) throws SQLException {
        String query = "Select * from telefones where cpfFuncionario = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1,  telefone.getCpfFuncionario());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from telefones";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean deletarPorCPFFuncionario(FuncionarioVO funcionarioVO) throws SQLException {
        String query = "DELETE FROM telefones WHERE cpfFuncionario = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, funcionarioVO.getCpf());
            return ps.execute();

        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }
    
}
