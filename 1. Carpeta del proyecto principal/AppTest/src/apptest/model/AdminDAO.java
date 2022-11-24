/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.model;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author JMBJ
 */
public class AdminDAO {
    
    /**
     * Login 
     * @param user usuario administrador
     * @param password y su contraseña
     * @return devuelve un int si es 1 existe ese usuario en la base de datos
     * devuelve 0 esque ese usuario no hay en la tabla admin
     * devuelve -1 problemas al conectar con la BD
     */
     public int login (String user, String password){
        
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;
        //HUBO UN ERROR.
        int state = -1;

        try{
            
            
            connection = ConnectionPoolMySQL.getInstance().getConnection();
            
            if(connection!=null){
                /**
                 * Utilizamos BINARY para que sea byte por byte
                 * asi sera mas precisa
                 */
                String sql = "SELECT * FROM admin WHERE BINARY username=? AND password=?";
                
                pst = connection.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, password);
                
                rs = pst.executeQuery();
                
                if(rs.next()){
                    state = 1;
                }else{
                    state = 0;
                }
                
            }else{
                mostrarMensajeError("Error BD" , " al conectarse con la BD");
            }
            
        }catch(HeadlessException | SQLException ex){
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n" , ex.getMessage());

           
        }finally{
            
            try{
                if(connection != null){
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);            
                }            
            }catch(SQLException ex){
                System.err.println(ex.getMessage());            
            }

        }
        
        
        return state;
        
    }
     
       private void mostrarMensajeInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }
    
}
