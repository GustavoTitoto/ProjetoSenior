/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosenior.Dao;


import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetosenior.Model.CidadeModel;

/**
 *
 * @author gusta
 */
public class CidadeDao {

    private Connection connection;

    public CidadeDao() {
        this.connection = new ConnectionFactory().getConnection();

    }

    public List getListaCidadePaginada(int pagina, String ordenacao, String pesquisa, String campopesquisa) throws SQLException {

        int limite = 10;
        int offset = (pagina * limite) - limite;
        String sql = "";

        if (campopesquisa.equals("cidCodigo")) {
            if (pesquisa.equals("")) {
                sql = "select * from cidade where cidCodigo > 0 order by " + ordenacao + " LIMIT 10 OFFSET " + offset;
            } else {
                sql = "select * from cidade where cidCodigo = " + pesquisa + " order by " + ordenacao + " LIMIT 10 OFFSET " + offset;
            }
        } else {
            sql = "select * from cidade where " + campopesquisa + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 10 OFFSET " + offset;
        }

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<CidadeModel> listaCidade = new ArrayList<CidadeModel>();
        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                CidadeModel cidade = new CidadeModel();
                cidade.setCidDescricao(resultSet.getString("cidDescricao"));
                cidade.setCidCodigo(resultSet.getInt("cidCodigo"));
                cidade.setCidUf(resultSet.getString("cidUf"));    
                listaCidade.add(cidade);
            }
            return listaCidade;

        } catch (SQLException ex) {

            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     public List getListaCidadeCombo() throws SQLException {

        String sql = "";

        sql = "select * from cidade order by cidDescricao";

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<CidadeModel> listaCidade = new ArrayList<CidadeModel>();
        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                CidadeModel cidade = new CidadeModel();
                cidade.setCidDescricao(resultSet.getString("cidDescricao"));
                cidade.setCidCodigo(resultSet.getInt("cidCodigo"));

                listaCidade.add(cidade);
                
            }
            System.out.println(listaCidade);
            return listaCidade;

        } catch (SQLException ex) {

            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    //Metodo para retornar numeor total de registros para paginar
    public String totalRegistros(String pesquisa, String campopesquisa) throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = "";
        try {

            if (campopesquisa.equals("cidCodigo")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) AS contaRegistros from cidade where cidCodigo > 0";
                } else {
                    sqlConta = "select count(*) AS contaRegistros from cidade where cidCodigo = " + pesquisa;
                }

            } else {
                sqlConta = "select count(*) AS contaRegistros from cidade where " + campopesquisa + " like '%" + pesquisa + "%'";
            }

            psConta = connection.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");

            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            connection.close();
            psConta.close();
        }
        return "erro no total registro";
    }

    public boolean excluiCidade(CidadeModel cidade) throws SQLException {
        String sql = "delete from cidade where ibgeId=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cidade.getIbgeId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
        return false;
    }

    public void alteraCidade(CidadeModel cidade) throws SQLException {
        String sql = "update cidade SET cidDescricao=?,cidUf=? where cidCodigo=?";
        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, cidade.getCidDescricao());
            ps.setString(2, cidade.getCidUf());
            ps.setInt(3, cidade.getCidCodigo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    public void novoCidade(CidadeModel cidade) throws SQLException {
        String sql = "INSERT INTO public.cidade (cidDescricao,cidUf) VALUES (?,?)";

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, cidade.getCidDescricao());
            ps.setString(2, cidade.getCidUf());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }
}
