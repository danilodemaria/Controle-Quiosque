/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 *
 * @author Administração
 */
public class Database {

    public static Connection Connect() {
        String url = "jdbc:postgresql://localhost/controlebd";
        String usuario = "controle";
        String senha = "1234";

        String driverName = "org.postgresql.Driver";

        Connection conn = null;
        try {
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao tentar conectar com o banco de dados, contate o suporte ");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Problemas ao tentar conectar com o banco de dados, contate o suporte");
        }
        return conn;
    }

    public boolean inserirProduto(String nome, float preco) {
        PreparedStatement pst;
        String stm = "INSERT INTO produto (nome,preco) values(?, ?)";
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            pst.setString(1, nome);
            pst.setFloat(2, preco);
            pst.execute();
            conn.close();

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return false;
    }

    public boolean insereComanda(int codigo, int quantidade, float valor_total, String nome, String data) throws ParseException {
        PreparedStatement pst;
        String dataTemp[] = data.split("/");
        String dataTemp2 = dataTemp[2] + "-" + dataTemp[1] + "-" + dataTemp[0];

        Date date = Date.valueOf(dataTemp2);
        String stm = "INSERT INTO lancamento (id_produto, quantidade, valor_total, nome, data_venda) values(?, ?, ?, ?, ?)";
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            pst.setInt(1, codigo);
            pst.setInt(2, quantidade);
            pst.setFloat(3, valor_total);
            pst.setString(4, nome);
            pst.setDate(5, date);
            pst.execute();
            conn.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return false;
    }

    public ResultSet buscaProduto(String codigo) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        String stm = null;

        stm = "SELECT * from produto where id = ? order by nome";
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            pst.setInt(1, Integer.valueOf(codigo));
            rs = pst.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return rs;

    }

    public ResultSet buscaTodosProdutos() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        String stm = null;

        stm = "SELECT * from produto";
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            rs = pst.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return rs;

    }

    public ResultSet buscaRelatorio(String dataInicio, String dataFinal)throws ParseException {
        ResultSet rs = null;
        PreparedStatement pst = null;
        String stm = null;
        String dataTemp[] = dataInicio.split("/");
        String dataTemp2 = dataTemp[2] + "-" + dataTemp[1] + "-" + dataTemp[0];
        
        String dataTemp1[] = dataFinal.split("/");
        String dataTemp3 = dataTemp1[2] + "-" + dataTemp1[1] + "-" + dataTemp1[0];

        Date date = Date.valueOf(dataTemp2);
        Date date1 = Date.valueOf(dataTemp3);
        
        stm = "select * FROM lancamento WHERE (data_venda BETWEEN '"+dataTemp2+"' AND '"+dataTemp3+"')";
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            rs = pst.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return rs;
    }

    public ResultSet buscaProdutoRelatorio(String dataInicio, String dataFinal, String codigo) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        String stm = null;
        String dataTemp[] = dataInicio.split("/");
        String dataTemp2 = dataTemp[2] + "-" + dataTemp[1] + "-" + dataTemp[0];
        
        String dataTemp1[] = dataFinal.split("/");
        String dataTemp3 = dataTemp1[2] + "-" + dataTemp1[1] + "-" + dataTemp1[0];

        Date date = Date.valueOf(dataTemp2);
        Date date1 = Date.valueOf(dataTemp3);
        
        stm = "select   t.id_produto,t.nome,sum(t.quantidade) as qtd, sum(t.valor_total) as valor "
                + "from lancamento t where t.id_produto="+codigo+" and (data_venda::date >= '"+dataTemp2+"' AND data_venda::date <='"+dataTemp3+"') "
                + "group by t.id_produto,t.nome;";
        System.out.println("stm"+stm);
        try {
            Connection conn = Database.Connect();
            pst = conn.prepareStatement(stm);
            rs = pst.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas ao conectar ao banco, contate o suporte");
        }
        return rs;
        
        
    }

}
