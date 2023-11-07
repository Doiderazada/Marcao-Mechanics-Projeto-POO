package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.models.VO.FuncionarioVO;

public class FuncionarioDao extends BaseDao <FuncionarioVO> {
    Connection db;


    public FuncionarioDao() {
        db = this.getConnection();
    }

    public boolean inserir(FuncionarioVO funcionario) throws SQLException {
        String query = "INSERT INTO funcionarios (nome, cpf, salario, dataDeAdmissao, nivel, senha) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setDouble(3, funcionario.getSalario());
            ps.setString(4, funcionario.getDataDeAdimissao());
            ps.setInt(5, funcionario.getNivel());
            ps.setString(6, funcionario.getSenha());
            ps.execute();
            
            if (funcionario.getEndereco() != null) {
                EnderecoDao endereco = new EnderecoDao();
                funcionario.getEndereco().setCpfFuncionario(funcionario.getCpf());
                endereco.inserir(funcionario.getEndereco());    
            }

            if(funcionario.getTelefone() != null) {
                TelefoneDao telefone = new TelefoneDao();
                funcionario.getTelefone().setCpfFuncionario(funcionario.getCpf());
                telefone.inserir(funcionario.getTelefone());
            }

            return true;
        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public boolean deletar(FuncionarioVO vo) throws SQLException {
        String query = "DELETE FROM funcionarios WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, vo.getId());
            return ps.execute();

        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public FuncionarioVO atualizar(FuncionarioVO funcionario) throws SQLException {
        String query = "UPDATE funcionarios SET nome = ?, cpf = ?, salario = ?, dataDeAdmissao = ?, nivel = ?, senha = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(7, funcionario.getId());
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setDouble(3, funcionario.getSalario());
            ps.setString(4, funcionario.getDataDeAdimissao());
            ps.setInt(5, funcionario.getNivel());
            ps.setString(6, funcionario.getSenha());
            ps.executeUpdate();

            if (funcionario.getEndereco() != null) {
                EnderecoDao endereco = new EnderecoDao();
                endereco.atualizar(funcionario.getEndereco());
            }

            if (funcionario.getTelefone() != null) {
                TelefoneDao telefone = new TelefoneDao();
                telefone.atualizar(funcionario.getTelefone());
            }

            return funcionario;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(FuncionarioVO funcionario) throws SQLException {
        String query = "Select * from funcionarios where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, funcionario.getId());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorCPF(FuncionarioVO funcionario) throws SQLException {
        String query = "Select * from funcionarios where cpf like '%' || ? || '%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, funcionario.getCpf());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet buscarPorNome(FuncionarioVO funcionario) throws SQLException {
        String query = "Select * from funcionarios where nome like '%' || ? || '%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, funcionario.getNome());
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet listar() throws SQLException {
        String query = "Select * from funcionarios order by id desc";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (Exception e) {
            throw e;
        }
    }
    
}
