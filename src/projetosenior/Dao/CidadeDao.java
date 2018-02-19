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
    //Metodo para retornar numeor total de registros para paginar
    public String totalRegistros(String pesquisa, String campopesquisa) throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = "";
        try {

            if (campopesquisa.equals("ibgeId")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) AS contaRegistros from cidade where ibgeId > 0";
                } else {
                    sqlConta = "select count(*) AS contaRegistros from cidade where ibgeId = " + pesquisa;
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
        String sql = "update cidade SET capital=?,lat=?,lon=?,uf=?,noAccents=?,nomeAlternativo=?,microRegiao=?"
                + ",macroRegiao where ibgeId=?";
        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(sql);
            ps.setInt(1, cidade.getIbgeId());
            ps.setString(2, cidade.getCapital());
            ps.setInt(3, cidade.getLat());
            ps.setInt(4, cidade.getLon());
            ps.setString(5, cidade.getUf());
            ps.setInt(6, cidade.getNoAccents());
            ps.setString(7, cidade.getNomeAlternativo());
            ps.setString(8, cidade.getMicroRegiao());
            ps.setString(9, cidade.getMacroRegiao());
            
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    public void novaCidade(CidadeModel cidade) throws SQLException {
        String sql = "INSERT INTO public.cidade (ibgeId,capital,lat,lon,uf,noAccents,nomeAlternativo,microRegiao,macroRegiao)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);           
            ps.setInt(1, cidade.getIbgeId());
            ps.setString(2, cidade.getCapital());
            ps.setInt(3, cidade.getLat());
            ps.setInt(4, cidade.getLon());
            ps.setString(5, cidade.getUf());
            ps.setInt(6, cidade.getNoAccents());
            ps.setString(7, cidade.getNomeAlternativo());
            ps.setString(8, cidade.getMicroRegiao());
            ps.setString(9, cidade.getMacroRegiao());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }
}
