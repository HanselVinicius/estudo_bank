package br.com.alura.bytebank.test;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.conta.ContaDAO;


public class TestaQuery {

    public static void main(String[] args) {
        testaPegaConta();
    }

    public static void testaPegaConta(){
        ConnectionFactory conn = new ConnectionFactory();

        ContaDAO contaDao = new ContaDAO(conn.recuperaConexao());
        System.out.println(contaDao.pegaConta(90909));

    }


}
