package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.ServicoVO;

public class ServicoDao <VO extends ServicoVO> extends BaseDao <VO>{
    Connection db;

    public ServicoDao() {
        db = this.getConnection();
    }

    public boolean inserir(VO Servico) throws SQLException {
        String query = "INSERT INTO servicos (nome, preco) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, Servico.getNome());
            ps.setDouble(2, Servico.getValor());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(VO Servico) throws SQLException {
        String query = "DELETE FROM servicos WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, Servico.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean atualizar(VO Servico) throws SQLException {
        String query = "UPDATE servicos SET nome = ?, preco = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(3, Servico.getId());
            ps.setString(1, Servico.getNome());
            ps.setDouble(2, Servico.getValor());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(VO Servico) throws SQLException {
        String query = "Select * from servicos where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, Servico.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorNome(VO servico) throws SQLException {
        String query = "select * from servicos where nome like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, servico.getNome());
            return ps.executeQuery();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from servicos";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }
}
