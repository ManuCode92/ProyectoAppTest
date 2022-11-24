/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;


/**
 *
 * @author JMBJ
 */
public class DataBaseHandler {
    private static final String connection_string="jdbc:mysql://localhost:3306/usuario";
    private static final String Jdbc_driver="com.mysql.cj.jdbc.Driver";
    private static Connection con=null;
    private static Statement stmt=null;
    private static final String CONTRASENNA = "12345";
    private static final String USUARIO = "admin";

     private static final String preguntaT="CREATE TABLE if not exists test (" +
"qid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
"pregunta VARCHAR NOT NULL," +
"option1 VARCHAR NOT NULL," +
"option2 VARCHAR NOT NULL," +
"option3 VARCHAR NOT NULL," +
"option4 VARCHAR NOT NULL," +
"respuesta VARCHAR NOT NULL" +
");";
 private static final String respuestaT="CREATE TABLE if not exists respuestatest (username VARCHAR NOT NULL REFERENCES users (username),"
         + "qno INT NOT NULL,response VARCHAR NOT NULL,PRIMARY KEY (username,qno))";
    private static final String users="create table if not exists users(username varchar ,password varchar,attempt varchar,PRIMARY KEY(username))";
    private static final String admin="create table if not exists admin(username VARCHAR,password VARCHAR,PRIMARY KEY(username))";
    
    
    public static void connect()
    {
        try {
            Class.forName(Jdbc_driver);
            con=DriverManager.getConnection(connection_string,USUARIO,CONTRASENNA);
            stmt=con.createStatement();
        } catch (ClassNotFoundException|SQLException ex) {
            mostrarMensajeError("Error al conectar...",  ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
    }
    
     
    public static void create_tables()
    {
        connect();
        executeUpdate(users);
        executeUpdate(admin);
        executeUpdate(preguntaT);
        executeUpdate(respuestaT);
       
        disconnect();
       
        System.out.println("Sistema preparado...");
    }
    public static void disconnect()
    {
        if(con!=null)
        {
            try {
                stmt.close();
                con.close();
                stmt=null;
                con=null;
            } catch (SQLException ex) {
               mostrarMensajeError("Error al desconectar...",  ex.getMessage());

                System.out.println(ex.getMessage());
            }
        }
    }
    public static ResultSet executeQuery(String sql)
    {
        ResultSet rs=null;
        try{
            connect();
             rs=stmt.executeQuery(sql);
        }catch(SQLException ex)
        {
            mostrarMensajeError("DB#:No funciona...",  ex.getMessage());

            System.out.println("DB#:No funciona...");
        }
        return rs;
    }
    public static int executeUpdate(String sql)
    {
        int i=0;
        try{
            connect();
            Statement stmt=con.createStatement();
             i=stmt.executeUpdate(sql);
             disconnect();
        }catch(SQLException ex)
        {
            mostrarMensajeError("Problemas con la BD",  ex.getMessage());
        }
        return i;
    }
    
    private static void mostrarMensajeInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }

    private static void mostrarMensajeError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }    
}
