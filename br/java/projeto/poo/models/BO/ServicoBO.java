package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.java.projeto.poo.DAO.ServicoDao;
import br.java.projeto.poo.models.VO.ServicoVO;


public class ServicoBO implements BaseInterfaceBO<ServicoVO>{

    ServicoDao<ServicoVO> servicoDao = new ServicoDao<ServicoVO>();

    @Override
    public ArrayList<ServicoVO> listar() throws Exception {
        ArrayList<ServicoVO> listaServicos = new ArrayList<ServicoVO>();
        ResultSet servicosNoBanco = servicoDao.listar();
        

        while(servicosNoBanco.next()){
            long idServicoBanco = servicosNoBanco.getLong("id");
            String nomeServicoBanco = servicosNoBanco.getString("nome"); 
            double precoServicoBanco = servicosNoBanco.getDouble("preco");
            listaServicos.add(new ServicoVO(idServicoBanco,nomeServicoBanco,precoServicoBanco));
        }

        return listaServicos;
    }

    @Override
    public Object buscarPorId(int id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    public ArrayList<ServicoVO> buscarPorNome(ServicoVO vo) throws Exception {
        
        try{
            ResultSet resultBusca = servicoDao.buscarPorNome(vo);
            ArrayList<ServicoVO> resultado = new ArrayList<ServicoVO>();

            while (resultBusca.next()) {
                long idResult = resultBusca.getLong("id");
                String nomeResult = resultBusca.getString("nome");
                double valorResult = resultBusca.getDouble("preco");
                resultado.add(new ServicoVO(idResult, nomeResult, valorResult));
            }

            return resultado;

        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Erro buscando o serviço.");
        }
    }

    @Override
    public boolean inserir(ServicoVO vo) throws Exception {
        try {servicoDao.inserir(vo);} 
        catch (Exception e) {e.printStackTrace();}
        
        return true;
    }

    @Override
    public boolean atualizar(ServicoVO vo) throws Exception {
        try{return servicoDao.atualizar(vo);}
        catch(Exception e){e.printStackTrace();}
        return false;
    }

    @Override
    public Boolean deletar(ServicoVO vo) {
        try {return servicoDao.deletar(vo);}
        catch(Exception e) {e.printStackTrace();}
        return false;
    }
    
}
