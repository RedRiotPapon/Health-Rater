/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author itxpa
 */
public class user {
   
    private String name;
    private String password;
    public user()
    {}

    public user(String name, String password) {
        this.name = name;
        this.password = password;
    }

    user(TextField uname, PasswordField pass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" + "name=" + name + ", password=" + password + '}';
    }
    
}
