package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.DAO.OrcamentoDao;
import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.models.VO.PecaVo;
import br.java.projeto.poo.models.VO.ServicoVO;

public class OrcamentoBO {
    OrcamentoDao orcamentoDao = new OrcamentoDao();

    public ArrayList<OrcamentoVO> listar() throws Exception {
        try {
            ResultSet orcamentos = orcamentoDao.listar();
            ArrayList<OrcamentoVO> listarOrcamentos = new ArrayList<OrcamentoVO>();
            while(orcamentos.next()) {
                OrcamentoVO orcamento = new OrcamentoVO();
                orcamento.setId(orcamentos.getLong("id"));
                orcamento.setPlacaVeiculo(orcamentos.getString("placaVeiculo"));
                orcamento.setValor(orcamentos.getDouble("valor"));
                orcamento.setDataDeCriação(orcamentos.getDate("dataDeCriacao"));
                orcamento.setDataDeEncerramento(orcamentos.getDate("dataDeEncerramento"));
                orcamento.setCpfCliente(orcamentos.getString("cpfCliente"));
                orcamento.setCpfFuncionario(orcamentos.getString("cpfResponsavel"));
                listarOrcamentos.add(orcamento);
            }

            return listarOrcamentos;

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean inserir(OrcamentoVO vo) throws Exception {
        try {
            return this.orcamentoDao.inserir(vo); 
        } catch (Exception e) {
            throw e;
        }
    }

    public OrcamentoVO atualizar(OrcamentoVO VO) throws Exception {
        try {
           return orcamentoDao.atualizar(VO);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deletar(long id) throws Exception {
        try {
            OrcamentoVO vo = new OrcamentoVO(); 
            vo.setId(id);
            return orcamentoDao.deletar(vo);
        } catch (Exception e) {
            throw e;
        }
    }

    public OrcamentoVO buscarPorId(long id) throws Exception {
        try {
            OrcamentoVO orcamentoVO = new OrcamentoVO();
            orcamentoVO.setId(id);
            ResultSet orcamentoBuscado = orcamentoDao.buscarPorId(orcamentoVO);
            ResultSet pecasBuscadas, servicosBuscados;
            ArrayList<PecaVo> pecas = new ArrayList<>();
            ArrayList<ServicoVO> servicos = new ArrayList<>();
            if(orcamentoBuscado.next()) {
                orcamentoVO.setId(orcamentoBuscado.getLong("id"));
                orcamentoVO.setCpfCliente(orcamentoBuscado.getString("cpfCliente"));
                orcamentoVO.setCpfFuncionario(orcamentoBuscado.getString("cpfResponsavel"));
                orcamentoVO.setPlacaVeiculo(orcamentoBuscado.getString("placaVeiculo"));
                orcamentoVO.setValor(orcamentoBuscado.getDouble("valor"));
                pecasBuscadas = orcamentoDao.listarPecas(orcamentoVO);
                servicosBuscados = orcamentoDao.listarServicos(orcamentoVO);

                while(pecasBuscadas.next()) {
                    PecaVo peca = new PecaVo(pecasBuscadas.getLong("id"),
                    pecasBuscadas.getString("nome"),
                    pecasBuscadas.getString("fabricante"),
                    pecasBuscadas.getDouble("preco"),
                    0);
                    peca.setQuantidade(orcamentoDao.contarPecas(peca, id));
                    pecas.add(peca);
                }

                while(servicosBuscados.next()) {
                    servicos.add(new ServicoVO(servicosBuscados.getLong("id"),
                    servicosBuscados.getString("nome"),
                    servicosBuscados.getDouble("preco")));
                }

                orcamentoVO.setPecas(pecas);
                orcamentoVO.setServicos(servicos);
            }
            return orcamentoVO;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<OrcamentoVO> buscarPorVeiculo(String placa) throws SQLException {
        return null;
    }

    public ArrayList<OrcamentoVO> buscarPorCPFCliente(String cpf) throws SQLException {
        return null;
    }
}
