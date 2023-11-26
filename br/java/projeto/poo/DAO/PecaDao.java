package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.PecaVo;

public class PecaDao <VO extends PecaVo> extends BaseDao <VO>{
    Connection db;

    public PecaDao() {
        db = this.getConnection();
    }

    public boolean inserir(VO peca) throws SQLException {
        String query = "INSERT INTO pecas (nome, fabricante, preco, quantidade) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, peca.getNome());
            ps.setString(2, peca.getFabricante());
            ps.setDouble(3, peca.getValor());
            ps.setInt(4, peca.getQuantidade());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(VO peca) throws SQLException {
        String query = "DELETE FROM pecas WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, peca.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean atualizar(VO peca) throws SQLException {
        String query = "UPDATE pecas SET nome = ?, fabricante = ?, preco = ?, quantidade = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, peca.getNome());
            ps.setString(2, peca.getFabricante());
            ps.setDouble(3, peca.getValor());
            ps.setInt(4,peca.getQuantidade());
            ps.setLong(5, peca.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(VO peca) throws SQLException {
        String query = "Select * from pecas where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, peca.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ResultSet buscarPorNome(VO peca) throws SQLException {
        String query = "select * from pecas where nome like '%' || ? || '%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, peca.getNome());
            return ps.executeQuery();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorFabricante(VO peca) throws SQLException {
        String query = "Select * from pecas where fabricante like '%' || ? || '%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, peca.getFabricante());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from pecas";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }
}
