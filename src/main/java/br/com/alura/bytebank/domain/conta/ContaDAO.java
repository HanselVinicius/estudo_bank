package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection conn;

     public ContaDAO(Connection connection) {
            this.conn = connection;
     }

     public void salvar(DadosAberturaConta dadosDaConta){
         Cliente cliente = new Cliente(dadosDaConta.dadosCliente());
         Conta conta = new Conta(dadosDaConta.numero(),cliente,BigDecimal.ZERO);
         String sql =  "INSERT INTO conta(numero,saldo,cliente_nome,cliente_cpf,cliente_email)"+
                 "values(?,?,?,?,?)";


         try {
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             preparedStatement.setInt(1, conta.getNumero());
             preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
             preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
             preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
             preparedStatement.setString(5, dadosDaConta.dadosCliente().email());

             preparedStatement.execute();
             preparedStatement.close();
             conn.close();
         }catch (SQLException e){
             throw new RuntimeException(e);
         }
     }


     public Set<Conta> listar(){
         PreparedStatement ps;
         ResultSet rs;
         Set<Conta> contas = new HashSet<>();
         String sql = "SELECT * FROM conta";
         try {
              ps = conn.prepareStatement(sql);
              rs = ps.executeQuery();

            while(rs.next()){
                Integer numero = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                String nome = rs.getString(3);
                String cpf = rs.getString(4);
                String email = rs.getString(5);
                Cliente cliente = new Cliente(new DadosCadastroCliente(nome, cpf, email));
                contas.add(new Conta(numero,cliente,saldo));

            }
            rs.close();
            ps.close();
            conn.close();
         }catch (SQLException e){
             throw new RuntimeException(e);
         }
         return contas;

    }


    public Conta pegaConta(int numeroConta){
        PreparedStatement ps;
        ResultSet rs;
         Conta conta = null;
        String sql = "SELECT * FROM conta WHERE numero = "+numeroConta;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Integer numeroRecuperado = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                Cliente  cliente = new Cliente(new DadosCadastroCliente(rs.getString(3), rs.getString(4), rs.getString(5)));
                conta = new Conta(numeroRecuperado,cliente,saldo);
            }
            rs.close();
            ps.close();
            conn.close();
            return conta;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void alterar(Integer numero, BigDecimal valor){
         PreparedStatement ps;
         String sql= "UPDATE conta SET saldo = ? WHERE numero = ?";

         try{
             ps = conn.prepareStatement(sql);

             ps.setBigDecimal(1,valor);
             ps.setInt(2,numero);

             ps.execute();
             conn.commit();
             ps.close();
             conn.close();
         }catch (SQLException e){
             try{
                 conn.rollback();
             }catch (SQLException ex){
                 throw new RuntimeException(e);

             }
             throw new RuntimeException(e);
         }


    }



}
