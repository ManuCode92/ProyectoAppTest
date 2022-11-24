/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import apptest.model.UserDAO;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author JMBJ
 */
//PARA INICIAR LA PRUEBA PARAMETRIZADA
@RunWith(value = Parameterized.class)

public class TestParametroJUnitTest {
    
    @Parameters
    public static Iterable<Object[]> getData(){
        
        return Arrays.asList(new Object[][] {
        {"JOSE","1234",1 },{"JUSTINIANO","1234",2 },{"BECERRA","1234",2 }  });
    
    }
    
    private final String  user;
    private final  String  pass;
    private final int resultado;
    
    public TestParametroJUnitTest(String users, String password, int result){
        this.user= users;
        this.pass = password;
        this.resultado= result;
        
    }
    @Test
    public void testAdd(){
        UserDAO model = new UserDAO();
        int resul= model.registro(user, pass);
        assertEquals(resultado, resul);
    }

   
}
