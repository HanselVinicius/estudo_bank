package br.com.alura.bytebank.test;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.conta.Conta;
import br.com.alura.bytebank.domain.conta.ContaDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;


public class TestaQuery {

       ConnectionFactory conn;
       ContaDAO contaDao;

    @BeforeEach
    public void init() {
        this.conn = new ConnectionFactory();
        this.contaDao = new ContaDAO(conn.recuperaConexao());
    }

    @Test
    public void testaPegaConta(){

        Assertions.assertEquals(this.contaDao.pegaConta(90909).getClass(), Conta.class);
        Assertions.assertThrows(RuntimeException.class,() -> {
            this.contaDao.listar();
        });
    }
    @Test
    public void testaListagem(){
        Set<Conta> contas = this.contaDao.listar();
        // o banco nunca estara vazio pois contem um valor de teste portanto se vier vazio é porque houve alguma falha
        Assertions.assertFalse(contas.isEmpty());
        Assertions.assertThrows(RuntimeException.class,() -> {
            this.contaDao.listar();
        });
    }







}
