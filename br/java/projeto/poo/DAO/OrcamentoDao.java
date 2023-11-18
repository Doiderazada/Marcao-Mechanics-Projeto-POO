package br.java.projeto.poo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.models.VO.PecaVo;
import br.java.projeto.poo.models.VO.ServicoVO;

public class OrcamentoDao extends BaseDao <OrcamentoVO>{
    Connection db;

    public OrcamentoDao() {
        db = this.getConnection();
    }

    public boolean inserir(OrcamentoVO orcamento) throws SQLException {
        String query = "INSERT INTO orcamentos (placaVeiculo, valor, cpfCliente, dataDeCriacao) VALUES (?, ?, ?, ?)";
        String queryPecas = "INSERT INTO pecas_orcamentos (idOrcamento, idPeca, quantidade) VALUES (?, ?, ?)";
        String queryServicos = "INSERT INTO servicos_orcamentos (idOrcamento, idServico) values (?, ?)";
        PreparedStatement ps = null;

        try {

            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime()); // pega a data atual
            
            ps = this.db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, orcamento.getPlacaVeiculo());
            ps.setDouble(2, orcamento.getValor());
            ps.setString(3, orcamento.getCpfCliente());
            ps.setDate(4, sqlDate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLException("Erro ao inserir orçamento.");
            }
            long idOrcamento = rs.getLong(1);

            List<PecaVo> pecas = orcamento.getPecas();
            List<ServicoVO> servicos = orcamento.getServicos();
            ps = this.db.prepareStatement(queryPecas);

            if(pecas != null) {
                for (PecaVo peca : pecas) {
                    try {
                        ps.setLong(1, idOrcamento);
                        ps.setLong(2, peca.getId());
                        ps.setLong(3, peca.getQuantidade());
                        ps.execute();
                    } catch (SQLException e) {
                        throw e;
                    }
                }
                ps.close();
            }
            ps = this.db.prepareStatement(queryServicos);
            
            if(servicos != null) {
                for (ServicoVO servico : servicos) {
                    try {
                        ps.setLong(1, idOrcamento);
                        ps.setLong(2, servico.getId());
                        ps.execute();
                    } catch (SQLException e) {
                        throw e;
                    }
                }
                ps.close();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deletar(OrcamentoVO orcamento) throws SQLException {
        String query = "DELETE FROM orcamentos WHERE id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, orcamento.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public OrcamentoVO atualizar(OrcamentoVO orcamento) throws SQLException {
        String query = "UPDATE orcamentos SET valor = ? WHERE id = ?";
        String queryPecas = "INSERT INTO pecas_orcamentos (idOrcamento, idPeca, quantidade) VALUES (?, ?, ?)";
        String queryServicos = "INSERT INTO servicos_orcamentos (idOrcamento, idServico) values (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(2, orcamento.getId());
            ps.setDouble(1, orcamento.getValor());
            ps.executeUpdate();
            ps.close();

            ArrayList<PecaVo> pecas = orcamento.getPecas();
            ArrayList<ServicoVO> servicos = orcamento.getServicos();

            ps = this.db.prepareStatement(queryPecas);
            if(pecas != null) {
                this.deletarPecas(orcamento);
                for (PecaVo peca : pecas) {
                    try {
                        ps.setLong(1, orcamento.getId());
                        ps.setLong(2, peca.getId());
                        ps.setLong(3, peca.getQuantidade());
                        ps.execute();
                    } catch (SQLException e) {
                        throw e;
                    }
                }
                ps.close();
            }

            ps = this.db.prepareStatement(queryServicos);
            if(servicos != null) {
                this.deletarServicos(orcamento);
                for (ServicoVO servico : servicos) {
                    try {
                        ps.setLong(1, orcamento.getId());
                        ps.setLong(2, servico.getId());
                        ps.execute();
                    } catch (SQLException e) {
                        throw e;
                    }
                }
                ps.close();
            }
            orcamento.setPecas(pecas);
            orcamento.setServicos(servicos);
            return orcamento;

        } catch (SQLException e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public ResultSet buscarPorId(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from orcamentos where id = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, orcamento.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet buscarPorVeiculo(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from orcamentos where placaVeiculo like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, orcamento.getPlacaVeiculo());
            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ResultSet buscarPorCPFCliente(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from orcamentos where cpfCLiente like '%'|| ? ||'%'";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setString(1, orcamento.getCpfCliente());
            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ResultSet buscarPorData(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from orcamentos where dataDeCriacao >= (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setDate(1, orcamento.getDataDeCriação());
            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ResultSet buscarPorStatusData(OrcamentoVO orcamento) throws SQLException {
        try {
            PreparedStatement ps = null;
            String query;
            if (orcamento.getStatus() == 0) {
                query = "Select * from orcamentos where status = 0 and dataDeCriacao >= (?)";
                ps = this.db.prepareStatement(query);
                ps.setDate(1, orcamento.getDataDeCriação());
            } else {
                if (orcamento.getStatus() == 1) {
                    query = "Select * from orcamentos where status = 1 and dataDeCriacao >= (?) and dataDeEncerramento <= (?)";
                    ps = this.db.prepareStatement(query);
                    ps.setDate(1, orcamento.getDataDeCriação());
                    ps.setDate(2, orcamento.getDataDeEncerramento());
                } else {
                    query = "Select * from orcamentos where dataDeCriacao >= (?) and (dataDeEncerramento <= (?) or dataDeEncerramento is null)";
                    ps = this.db.prepareStatement(query);
                    ps.setDate(1, orcamento.getDataDeCriação());
                    ps.setDate(2, orcamento.getDataDeEncerramento());
                }
            }
            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    @Deprecated
    public ResultSet listar() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    public ResultSet listarEmAberto() throws SQLException {
        String query = "Select * from relatorios where status = 0";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ResultSet listarTodos() throws SQLException {
        String query = "Select * from relatorios";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet listarPecas(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from pecas where id in (select idPeca from pecas_orcamentos where idOrcamento = ?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, orcamento.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public int contarPecas(PecaVo vo, long idOrcamento) throws SQLException {
        String query = "Select quantidade from pecas_orcamentos where idPeca = (?) and idOrcamento = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, vo.getId());
            ps.setLong(2, idOrcamento);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet listarServicos(OrcamentoVO orcamento) throws SQLException {
        String query = "Select * from servicos where id in (select idServico from servicos_orcamentos where idOrcamento = ?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, orcamento.getId());
            return ps.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean deletarPecas(OrcamentoVO vo) throws SQLException {
        String query = "DELETE FROM pecas_orcamentos WHERE idOrcamento = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, vo.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean deletarServicos(OrcamentoVO vo) throws SQLException {
        String query = "DELETE FROM servicos_orcamentos WHERE idOrcamento = (?)";
        PreparedStatement ps = null;
        try {
            ps = this.db.prepareStatement(query);
            ps.setLong(1, vo.getId());
            return ps.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean encerrarRelatorio(OrcamentoVO vo) {
        try {
            String query = "UPDATE orcamentos SET status = 1, dataDeEncerramento = ? WHERE id = ?";
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            PreparedStatement ps = this.db.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ps.setLong(2, vo.getId());
            return ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
