/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import apptest.model.UserDAO;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JMBJ
 */
public class TestCaseJUnitTest {

    @Test
    public void ComprobarSiUserARealizadoTest() {
        UserDAO model = new UserDAO();
        boolean esperado=true;
       boolean realizoTest=model.comprobarNoRealizoTest("1234", "123");
        assertEquals(esperado, realizoTest);
       

    }
    
    @Test
    public void ComprobarSiUserNoRealizoTest(){
          UserDAO model = new UserDAO();
        boolean esperado=false;
       boolean realizoTest=model.comprobarNoRealizoTest("manu", "123");
        assertEquals(esperado, realizoTest);
    }

    

}