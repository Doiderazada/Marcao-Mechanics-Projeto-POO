package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.ClienteVO;

public class ClienteDao extends BaseDao <ClienteVO>{
    Connection db;

    public ClienteDao() {
        db = this.getConnection();
    }

    public boolean inserir(ClienteVO cliente) throws SQLException {
        String query = "INSERT INTO clientes (nome, cpf) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.execute();

            if (cliente.getEndereco() != null) {
                EnderecoDao endereco = new EnderecoDao();
                cliente.getEndereco().setCpfCliente(cliente.getCpf());
                endereco.inserir(cliente.getEndereco());    
            }
            
            if (cliente.getTelefone() != null) {
                TelefoneDao telefone = new TelefoneDao();
                cliente.getTelefone().setCpfCliente(cliente.getCpf());
                telefone.inserir(cliente.getTelefone());    
            }

            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(ClienteVO cliente) throws SQLException {
        String query = "DELETE FROM clientes WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, cliente.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletarPorCPF(ClienteVO cliente) throws SQLException {
        String query = "DELETE FROM clientes WHERE cpf = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, cliente.getCpf());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean atualizar(ClienteVO cliente) throws SQLException {
        String query = "UPDATE clientes SET nome = ?, cpf = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(3, cliente.getId());
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());

            if (cliente.getEndereco() != null) {
                EnderecoDao endereco = new EnderecoDao();
                //cliente.getEndereco().setCpfCliente(cliente.getCpf());
                endereco.atualizar(cliente.getEndereco());    
            }
            
            if (cliente.getTelefone() != null) {
                TelefoneDao telefone = new TelefoneDao();
                //cliente.getTelefone().setCpfCliente(cliente.getCpf());
                telefone.atualizar(cliente.getTelefone());    
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.getMessage();
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(ClienteVO cliente) throws SQLException {
        String query = "Select * from clientes where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, cliente.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorCPF(ClienteVO cliente) throws SQLException {
        String query = "Select * from clientes where cpf like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, cliente.getCpf());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorNome(ClienteVO cliente) throws SQLException {
        String query = "Select * from clientes where nome like '%' || ? || '%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, cliente.getNome());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from clientes";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }
}
