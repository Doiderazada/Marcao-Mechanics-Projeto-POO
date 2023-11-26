package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import br.java.projeto.poo.exceptions.InvalidCpfException;
import br.java.projeto.poo.exceptions.UsuarioNaoEncontradoException;
import br.java.projeto.poo.DAO.VeiculoDao;
import br.java.projeto.poo.models.VO.VeiculoVO;

public class VeiculoBO {
    VeiculoDao veiculoDao = new VeiculoDao();

    public ArrayList<VeiculoVO> listar() throws Exception {
        try {
            ResultSet veiculosBuscados = veiculoDao.listar();
            ArrayList<VeiculoVO> veiculos = new ArrayList<>();
            while(veiculosBuscados.next()) {
                veiculos.add(new VeiculoVO(veiculosBuscados.getLong("id"),
                veiculosBuscados.getString("placa"),
                veiculosBuscados.getString("cor"),
                veiculosBuscados.getString("modelo"),
                veiculosBuscados.getString("cpfDono"),
                veiculosBuscados.getString("tipo"),
                veiculosBuscados.getString("ano"),
                veiculosBuscados.getDouble("km")));
            }

            return veiculos;
        } catch (Exception e) {
            throw new Exception("erro ao buscar veiculos");
        }
    }

    public ArrayList<VeiculoVO> buscarPorPlaca(String placa) throws Exception {
        try {
            ResultSet veiculosBuscados = veiculoDao.buscarPorPlaca(new VeiculoVO(0, placa, null, null, null, null, null, 0));
            ArrayList<VeiculoVO> veiculos = new ArrayList<>();
            while(veiculosBuscados.next()) {
                veiculos.add(new VeiculoVO(veiculosBuscados.getLong("id"),
                veiculosBuscados.getString("placa"),
                veiculosBuscados.getString("cor"),
                veiculosBuscados.getString("modelo"),
                veiculosBuscados.getString("cpfDono"),
                veiculosBuscados.getString("tipo"),
                veiculosBuscados.getString("ano"),
                veiculosBuscados.getDouble("km")));
            }

            return veiculos;
        } catch (Exception e) {
            throw new Exception("erro ao buscar veiculos");
        }
    }

    public ArrayList<VeiculoVO> buscarPorDono(String cpf) throws Exception {
        try {
            ResultSet veiculosBuscados = veiculoDao.buscarPorCPFDono(new VeiculoVO(0, null, null, null, cpf, null, null, 0));
            ArrayList<VeiculoVO> veiculos = new ArrayList<>();
            while(veiculosBuscados.next()) {
                veiculos.add(new VeiculoVO(veiculosBuscados.getLong("id"),
                veiculosBuscados.getString("placa"),
                veiculosBuscados.getString("cor"),
                veiculosBuscados.getString("modelo"),
                veiculosBuscados.getString("cpfDono"),
                veiculosBuscados.getString("tipo"),
                veiculosBuscados.getString("ano"),
                veiculosBuscados.getDouble("km")));
            }

            return veiculos;
        } catch (Exception e) {
            throw new Exception("erro ao buscar veiculos");
        }
    }

    public boolean inserir(VeiculoVO veiculo) throws Exception {
        try {
            return veiculoDao.inserir(veiculo);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Essa placa já pertence a outro veiculo");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return false;
    }

    public Boolean atualizar(VeiculoVO vo) throws Exception {
        try {
            ResultSet verificarVeiculo = veiculoDao.buscarPorId(vo);

            if (!verificarVeiculo.next() || vo.getId() == 0) {
                throw new UsuarioNaoEncontradoException("Usuario não encontrado");
            }

            return veiculoDao.atualizar(vo);
        } 
        catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new Exception("Essa placa já pertence a outro veiculo");
            }
            throw new Exception("falha ao atualizar veiculo");
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean deletar(String placa) throws Exception {
        try {
            VeiculoVO vo = new VeiculoVO(0, placa, null, null, null, null, null, 0);
            return veiculoDao.deletar(vo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro ao deletar usuario");
        }
    }

}
