/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaalta.view;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author JMBJ
 */
public class MostrarAltaController extends VBox{
    
    //Contraseña
    @FXML
    private PasswordField  passFContra;
    //Usuario
    @FXML
    private TextField txtFUser;
    @FXML
    //El titulo que se podra modificar.
    private Label tituloName;
    
    /**
     * Inicializamos el fxml
     */
    
    public MostrarAltaController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MostrarAltaFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    /**
     * CONTRASEÑA retorna la property() de la contraseña
     * @return devolvera la contraseña que se a introducido
     */
    
     public String getPassword() {
        return textPasswordProperty().get();
    }
    
    /**
     * CONTRASEÑA
     * @param value se le pasa un String para modificar la contraseña
     */
    public void setPassword(String value) {
        textPasswordProperty().set(value);
    }
    /**
     * CONTRASEÑA PROPERTY
     * @return devolvera la contraseña que se a introducido
     */
    public StringProperty textPasswordProperty() {
        return passFContra.textProperty();                
    }
    
    /**
     * USUARIO
     * @return devuelve el usuario
     */
    public String getUsuario() {
        return textUsuarioProperty().get();
    }
    
    /**
     * USUARIO modificar el usuario
     * @param value se le pasa un String
     */
    public void setUsuario(String value) {
        textUsuarioProperty().set(value);
    }
    /**
     * USUARIO
     * @return devuelve el usuario que se ha introducido
     */
    public StringProperty textUsuarioProperty() {
        return txtFUser.textProperty();                
    }
   
    /**
     * TITULO
     * @return devuelve un string
     */
    public String getTitulo() {
        return labelTituloProperty().get();
    }
    /**
     * Para modificar el titulo
     * @param value se le pasa un string para modificarlo
     */
    public void setTitulo(String value) {
        labelTituloProperty().set(value);
    }
    /**
     * Para el titulo.
     * @return Devuelve el titulo
     */
    public StringProperty labelTituloProperty() {
        return tituloName.textProperty();                
    }
    
    
}
