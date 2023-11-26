package br.java.projeto.poo.models.BO;

import java.sql.ResultSet;
// import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.DAO.PecaDao;
import br.java.projeto.poo.models.VO.PecaVo;

public class PecaBO implements BaseInterfaceBO<PecaVo>{

    PecaDao<PecaVo> pecaDao = new PecaDao<PecaVo>();

    @Override
    public ArrayList<PecaVo> listar() throws Exception {
        ArrayList<PecaVo> listaDePecas = new ArrayList<PecaVo>();
        ResultSet pecasDoBanco = pecaDao.listar();
        

        while(pecasDoBanco.next()){
            long idPecaBanco = pecasDoBanco.getLong("id");
            String nomePecaBanco = pecasDoBanco.getString("nome");
            String nomeFabrBanco = pecasDoBanco.getString("fabricante");
            int quantPecaBanco = pecasDoBanco.getInt("quantidade"); 
            double valorPecaBanco = pecasDoBanco.getDouble("preco");
            listaDePecas.add(new PecaVo(idPecaBanco,nomePecaBanco,nomeFabrBanco,valorPecaBanco,quantPecaBanco));
        }

        return listaDePecas;
    }

    @Override
    @Deprecated
    public Object buscarPorId(int id) throws Exception{
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    public ArrayList<PecaVo> buscarPorFabricante(PecaVo vo) throws Exception {
        PecaDao<PecaVo> pecaDao = new PecaDao<PecaVo>();
        try{
            ResultSet resultBusca = pecaDao.buscarPorFabricante(vo);
            ArrayList<PecaVo> resultado = new ArrayList<PecaVo>();
            

            while (resultBusca.next()) {
                long idResult = resultBusca.getLong("id");
                String nomeResult = resultBusca.getString("nome");
                String fabResult = resultBusca.getString("fabricante");
                double valorResult = resultBusca.getDouble("preco");
                int quantResult = resultBusca.getInt("quantidade");

                resultado.add(new PecaVo(idResult, nomeResult, fabResult, valorResult, quantResult));
            }

            return resultado;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro ao buscar peça.");
        }
    }

    public ArrayList<PecaVo> buscarPorNome(PecaVo vo) throws Exception {
        PecaDao<PecaVo> pecaDao = new PecaDao<PecaVo>();
        try{
            ResultSet resultBusca = pecaDao.buscarPorNome(vo);
            ArrayList<PecaVo> resultado = new ArrayList<PecaVo>();
            

            while (resultBusca.next()) {
                long idResult = resultBusca.getLong("id");
                String nomeResult = resultBusca.getString("nome");
                String fabResult = resultBusca.getString("fabricante");
                double valorResult = resultBusca.getDouble("preco");
                int quantResult = resultBusca.getInt("quantidade");

                resultado.add(new PecaVo(idResult, nomeResult, fabResult, valorResult, quantResult));
            }

            return resultado;

        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao buscar peça.");
        }
    }

    @Override
    public boolean inserir(PecaVo vo) throws Exception{
        try {pecaDao.inserir(vo);} 
        catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean atualizar(PecaVo vo) throws Exception{
        try {
            PecaDao<PecaVo> pecaDao = new PecaDao<PecaVo>();
            return pecaDao.atualizar(vo);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deletar(PecaVo vo) {
        PecaDao<PecaVo> pecaDao = new PecaDao<PecaVo>();
        try {return pecaDao.deletar(vo);}
        catch(Exception e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
        return false;
    }
    
}
