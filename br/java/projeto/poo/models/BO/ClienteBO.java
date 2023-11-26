package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.DAO.ClienteDao;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.TelefoneVO;
import br.java.projeto.poo.models.VO.VeiculoVO;

public class ClienteBO {
    ClienteDao clienteDao = new ClienteDao();
    EnderecoBO enderecoBO = new EnderecoBO();
    TelefoneBO telefoneBO = new TelefoneBO();
    VeiculoBO veiculo = new VeiculoBO();
    public boolean inserir(ClienteVO cliente) throws Exception {
        try {
            return clienteDao.inserir(cliente);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Esse CPF já pertence a outro Cliente");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return false;
    }

    public ArrayList<ClienteVO> buscarPorCPF(String cpf) throws Exception {
        try {
            ClienteVO cl = new ClienteVO();
            cl.setCpf(cpf);
            ResultSet clientesBuscados = clienteDao.buscarPorCPF(cl);  
            ArrayList<ClienteVO> clientes = new ArrayList<>();

            EnderecoVO endereco;
            TelefoneVO telefone;
            while (clientesBuscados.next()) {
                endereco = enderecoBO.buscarPorCliente(clientesBuscados.getString("cpf"));
                telefone = telefoneBO.buscarPorCliente(clientesBuscados.getString("cpf"));
                ArrayList<VeiculoVO> listaVeiculos = veiculo.buscarPorDono(clientesBuscados.getString("cpf"));
                clientes.add(new ClienteVO(
                    clientesBuscados.getInt("id"), 
                    clientesBuscados.getString("nome"), 
                    clientesBuscados.getString("cpf"), 
                    endereco, listaVeiculos, telefone));
            }

            return clientes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }     
    }
    
    public ArrayList<ClienteVO> buscarPorNome(String nome) throws Exception {
        try {
            ClienteVO cl = new ClienteVO();
            cl.setNome(nome);
            ResultSet clientesBuscados = clienteDao.buscarPorNome(cl);  
            ArrayList<ClienteVO> clientes = new ArrayList<>();
            EnderecoVO endereco;
            TelefoneVO telefone;
            while (clientesBuscados.next()) {
                endereco = enderecoBO.buscarPorCliente(clientesBuscados.getString("cpf"));
                telefone = telefoneBO.buscarPorCliente(clientesBuscados.getString("cpf"));
                ArrayList<VeiculoVO> listaVeiculos = veiculo.buscarPorDono(clientesBuscados.getString("cpf"));
                clientes.add(new ClienteVO(
                    clientesBuscados.getLong("id"), 
                    clientesBuscados.getString("nome"), 
                    clientesBuscados.getString("cpf"), 
                    endereco, listaVeiculos, telefone));
            }

            return clientes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }     
    }

    public boolean atualizar(ClienteVO vo) throws Exception {
        try {
            
            ClienteDao cliente = new ClienteDao();

            return cliente.atualizar(vo);
        } 
        catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Esse CPF já pertence a outro usuario");
            }
            throw new Exception("falha ao atualizar cliente");
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public ArrayList<ClienteVO> listar() throws Exception {
        ArrayList<ClienteVO> listaDeClientes = new ArrayList<ClienteVO>();
        ResultSet clientesDoBanco = clienteDao.listar();;
        

        while(clientesDoBanco.next()){
            String nomeCliente = clientesDoBanco.getString("nome");
            String cpfCliente = clientesDoBanco.getString("cpf");
            Long idCliente = clientesDoBanco.getLong("id");
            TelefoneVO telCliente = telefoneBO.buscarPorCliente(clientesDoBanco.getString("cpf"));
            EnderecoVO endCliente = enderecoBO.buscarPorCliente(clientesDoBanco.getString("cpf"));
            ArrayList<VeiculoVO> listaVeiculos = veiculo.buscarPorDono(clientesDoBanco.getString("cpf"));
            
            listaDeClientes.add(new ClienteVO(idCliente,nomeCliente, cpfCliente, endCliente, listaVeiculos, telCliente));
        }

        return listaDeClientes;
    }

    public Boolean deletar(ClienteVO vo) {
        ClienteDao clienteDao = new ClienteDao();
        try {return clienteDao.deletar(vo);}
        catch(Exception e) {e.printStackTrace();}
        return false;
    }
}
