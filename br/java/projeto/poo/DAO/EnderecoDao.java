package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.FuncionarioVO;

public class EnderecoDao extends BaseDao <EnderecoVO>{
    
    Connection db;

    public EnderecoDao() {
        db = this.getConnection();
    }

    public boolean inserir(EnderecoVO endereco) throws SQLException {
        String query = "INSERT INTO enderecos (cpfCliente, cpfFuncionario, rua, bairro, cidade, num_casa) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, endereco.getCpfCliente());
            ps.setString(2, endereco.getCpfFuncionario());
            ps.setString(3, endereco.getRua());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getNumero());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(EnderecoVO EnderecoVO) throws SQLException {
        String query = "DELETE FROM enderecos WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, EnderecoVO.getId());
            return ps.execute();

        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletarPorCPFFuncionario(FuncionarioVO funcionarioVO) throws SQLException {
        String query = "DELETE FROM enderecos WHERE cpfFuncionario = (?)";
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

    public boolean atualizar(EnderecoVO endereco) throws SQLException {
        String query = "UPDATE enderecos SET cpfCliente = ?, cpfFuncionario = ?, rua = ?, bairro = ?, cidade = ?, num_casa = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(7, endereco.getId());
            ps.setString(1, endereco.getCpfCliente());
            ps.setString(2, endereco.getCpfFuncionario());
            ps.setString(3, endereco.getRua());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getNumero());
            ps.executeUpdate();
            return true; 

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(EnderecoVO endereco) throws SQLException {
        String query = "Select * from enderecos where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, endereco.getId());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorFuncionario(EnderecoVO endereco) throws SQLException {
        String query = "Select * from enderecos where cpfFuncionario = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, endereco.getCpfFuncionario());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorCliente(EnderecoVO endereco) throws SQLException {
        String query = "Select * from enderecos where cpfCliente = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, endereco.getCpfCliente());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from enderecos";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }
}
