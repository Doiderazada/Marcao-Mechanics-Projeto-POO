package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.exceptions.ErroDeAuthenticacaoException;
import br.java.projeto.poo.DAO.FuncionarioDao;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.models.VO.TelefoneVO;

public class FuncionarioBO {
    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private EnderecoBO enderecoBO = new EnderecoBO();
    private TelefoneBO telefoneBO = new TelefoneBO();

    public ArrayList<FuncionarioVO> listar() throws Exception {
        try {
            ArrayList<FuncionarioVO> funcionarios = new ArrayList<FuncionarioVO>();
            ResultSet selectFuncionarios = funcionarioDao.listar();
            while(selectFuncionarios.next()) {
                EnderecoVO selectEndereco = enderecoBO.buscarPorFuncionario(selectFuncionarios.getString("cpf"));
                TelefoneVO selectTelefone = telefoneBO.buscarPorFuncionario(selectFuncionarios.getString("cpf"));

                funcionarios.add(new FuncionarioVO(
                    selectFuncionarios.getLong("id"), 
                    selectFuncionarios.getString("nome"),
                    selectFuncionarios.getString("cpf"),
                    selectFuncionarios.getDouble("salario"),
                    selectFuncionarios.getString("dataDeAdmissao"),
                    selectFuncionarios.getInt("nivel"),
                    selectEndereco,
                    selectTelefone,
                    selectFuncionarios.getString("senha")));
            }
            
            return funcionarios;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Erro ao acessar o banco");
        }
    }

    public ArrayList<FuncionarioVO> buscarPorCPF(String cpf) throws Exception {
        try {
            FuncionarioVO vo = new FuncionarioVO();
            vo.setCpf(cpf);
            ResultSet funcionario = funcionarioDao.buscarPorCPF(vo);
            ArrayList<FuncionarioVO> funcionarios = new ArrayList<>();
            while (funcionario.next()) {
                EnderecoVO selectEndereco = enderecoBO.buscarPorFuncionario(funcionario.getString("cpf"));
                TelefoneVO selectTelefone = telefoneBO.buscarPorFuncionario(funcionario.getString("cpf"));

                funcionarios.add(new FuncionarioVO(funcionario.getLong("id"),
                funcionario.getString("nome"),
                funcionario.getString("cpf"),
                funcionario.getFloat("salario"),
                funcionario.getString("dataDeAdmissao"),
                funcionario.getInt("nivel"),
                selectEndereco,
                selectTelefone,
                funcionario.getString("senha")));
            }

            return funcionarios;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro ao buscar funcionario");
        }
    }

    public ArrayList<FuncionarioVO> buscarPorNome(String nome) throws Exception {
        try {
            FuncionarioVO vo = new FuncionarioVO();
            vo.setNome(nome);
            ResultSet funcionario = funcionarioDao.buscarPorNome(vo);

            ArrayList<FuncionarioVO> funcionarios = new ArrayList<>();
            while (funcionario.next()) {
                EnderecoVO selectEndereco = enderecoBO.buscarPorFuncionario(funcionario.getString("cpf"));
                TelefoneVO selectTelefone = telefoneBO.buscarPorFuncionario(funcionario.getString("cpf"));

                funcionarios.add(new FuncionarioVO(funcionario.getLong("id"),
                funcionario.getString("nome"),
                funcionario.getString("cpf"),
                funcionario.getFloat("salario"),
                funcionario.getString("dataDeAdmissao"),
                funcionario.getInt("nivel"),
                selectEndereco,
                selectTelefone,
                funcionario.getString("senha")));
            }

            return funcionarios;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro ao buscar funcionario");
        }
    }

    public boolean inserir(FuncionarioVO vo) throws Exception {
        try {
            return funcionarioDao.inserir(vo);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Esse CPF já pertence a outro usuario");
            }
            System.out.println(e.getMessage());
            //throw new Exception("falha ao atualizar funcionario");
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean atualizar(FuncionarioVO vo) throws Exception {
        try {
            return funcionarioDao.atualizar(vo);
        } 
        catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Esse CPF já pertence a outro usuario");
            }
            throw new Exception(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Boolean deletar(FuncionarioVO vo) {
        try {
            return funcionarioDao.deletar(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public FuncionarioVO authenticar(FuncionarioVO vo) throws ErroDeAuthenticacaoException {
        try {
            ResultSet funcionario = funcionarioDao.buscarPorCPF(vo);
            if (funcionario == null) {
                throw new ErroDeAuthenticacaoException("Usuario não encontrado1");
            }
            if (funcionario.next()) { // se existir retorna os dados do usuario
                System.out.println(funcionario.getString("senha").equals(vo.getSenha()));
                if (funcionario.getString("senha").equals(vo.getSenha())) {

                    return new FuncionarioVO(funcionario.getInt("id"), 
                        funcionario.getString("nome"), 
                        funcionario.getString("cpf"), 
                        funcionario.getDouble("salario"), 
                        funcionario.getString("dataDeAdmissao"), 
                        funcionario.getInt("nivel"));
                }
            }

            throw new ErroDeAuthenticacaoException("Usuario não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroDeAuthenticacaoException(e.getMessage());
        }
    }
}
