/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.model;

import java.util.Objects;

/**
 *
 * @author JMBJ
 */
public class UsuarioAdmin {
    private String usuario;
    private String contraseña;
    public UsuarioAdmin(String user, String pass) {
        this.usuario = user;
        this.contraseña = pass;
    }
    
     public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String user) {
        this.usuario = user;
    }

    public String getApellidos() {
        return contraseña;
    }

    public void setContraseña(String pass) {
        this.contraseña = pass;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioAdmin other = (UsuarioAdmin) obj;
       
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }
        return true;
    }
}
