package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.java.projeto.poo.DAO.EnderecoDao;
import br.java.projeto.poo.models.VO.EnderecoVO;

public class EnderecoBO {
    private EnderecoDao enderecoDao = new EnderecoDao();

    public boolean inserir(EnderecoVO vo) throws Exception {
        try {
            return enderecoDao.inserir(vo);
        } catch (Exception e) {
            throw e;
        }
    } 
    
    public void atualizar(EnderecoVO vo) throws Exception {
        try {
            enderecoDao.atualizar(vo);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public EnderecoVO buscarPorFuncionario(String cpf) throws Exception {
        try {
            ResultSet selectEndereco = enderecoDao.buscarPorFuncionario(new EnderecoVO(null, cpf));
            if(selectEndereco.next()) {
                return new EnderecoVO(selectEndereco.getInt("id"), 
                    selectEndereco.getString("cpfCliente"),
                    selectEndereco.getString("cpfFuncionario"),
                    selectEndereco.getString("rua"), 
                    selectEndereco.getString("num_casa"), 
                    selectEndereco.getString("bairro"), 
                    selectEndereco.getString("cidade"));
            }

            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //throw new Exception("Erro ao acessar o banco");
            return null;
        }
    }

    public EnderecoVO buscarPorCliente(String cpf) throws Exception {
        try {
            ResultSet selectEndereco = enderecoDao.buscarPorCliente(new EnderecoVO(cpf, null));
            if(selectEndereco.next()) {
                return new EnderecoVO(selectEndereco.getInt("id"),
                    selectEndereco.getString("cpfCliente"),
                    selectEndereco.getString("cpfFuncionario"),
                    selectEndereco.getString("rua"), 
                    selectEndereco.getString("num_casa"), 
                    selectEndereco.getString("bairro"), 
                    selectEndereco.getString("cidade"));
            }

            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao acessar o banco");
        }
    }


    public boolean deletarPorCPFFuncionario(String cpf) throws SQLException {
        try {
           return enderecoDao.deletar(new EnderecoVO(null, cpf));
        } catch (Exception e) {
            throw e;
        }
    }
}
