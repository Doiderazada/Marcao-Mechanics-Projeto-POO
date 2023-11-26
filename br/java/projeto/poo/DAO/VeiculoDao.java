package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.VeiculoVO;

public class VeiculoDao extends BaseDao <VeiculoVO>{
    Connection db;

    public VeiculoDao() {
        db = this.getConnection();
    }

    public boolean inserir(VeiculoVO veiculo) throws SQLException {
        String query = "INSERT INTO veiculos (cor, modelo, placa, cpfDono, tipo, ano, km) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, veiculo.getCor());
            ps.setString(2, veiculo.getModelo());
            ps.setString(3, veiculo.getPlaca());
            ps.setString(4, veiculo.getCpfDono());
            ps.setString(5, veiculo.getTipo());
            ps.setString(6, veiculo.getAno());
            ps.setDouble(7, veiculo.getKm());
            ps.execute();

            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(VeiculoVO VeiculoVO) throws SQLException {
        String query = "DELETE FROM veiculos WHERE placa = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, VeiculoVO.getPlaca());
            return ps.execute();

        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean atualizar(VeiculoVO veiculo) throws SQLException {
        String query = "UPDATE veiculos SET cor = ?, modelo = ?, placa = ?, cpfDono = ?, tipo = ?, ano = ?, km = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(8, veiculo.getId());
            ps.setString(1, veiculo.getCor());
            ps.setString(2, veiculo.getModelo());
            ps.setString(3, veiculo.getPlaca());
            ps.setString(4, veiculo.getCpfDono());
            ps.setString(5, veiculo.getTipo());
            ps.setString(6, veiculo.getAno());
            ps.setDouble(7, veiculo.getKm());
            ps.executeUpdate();
            return true; 

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(VeiculoVO veiculo) throws SQLException {
        String query = "Select * from veiculos where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, veiculo.getId());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorCPFDono(VeiculoVO veiculo) throws SQLException {
        String query = "Select * from veiculos where cpfDono like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, veiculo.getCpfDono());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorPlaca(VeiculoVO veiculo) throws SQLException {
        String query = "Select * from veiculos where placa like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, veiculo.getPlaca());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }
    
    public ResultSet listar() throws SQLException {
        String query = "Select * from veiculos";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }
}
