/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class SignupController implements Initializable {
    String gender;
    @FXML
    private TextField uname;
    @FXML
    private PasswordField pass;
    @FXML
    private Button reg;
    @FXML
    private Button backlogin;
     @FXML
    private JFXDatePicker dp;
     @FXML
    void showdate(ActionEvent event) {

    }
    @FXML
    private TextField byear;

    @FXML
    private ChoiceBox<String> myChoiceBox1;
    
     String[] gen = {"Male", "Female"};
     Year yearx = Year.now();
    @FXML
    void register(ActionEvent event) throws SQLException, IOException {
        String name = uname.getText();
        String password = pass.getText();
        gender = myChoiceBox1.getValue();
        int year = Integer.parseInt(byear.getText());
        if (name.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Field is Blank!!");
        } 
        else if(year<1900 || year > yearx.getValue()){
            
           JOptionPane.showMessageDialog(null, "Invalid Year!!"); //restricting agelimit
        }
        
        else {
            user us = new user(name, password);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
            Statement statement = conn.createStatement();
        String s1 = "insert into central (name,chronicno,nonchronicno,systolic,diastolic,heartrate,bmi,rating,weight,year,height) " + " values('" + name + "',0,0,0,0,0,0.0,0,0," + year + ",0.0 )";
        ResultSet rs = statement.executeQuery("SELECT * FROM logininfo where Name = '" + name + "'"); //checking if value alrady exists in the column or not
        if (!rs.next()) {  // if value is unique then insert
            String s = "insert into logininfo" + " values( '" + name + "','" + us.getPassword() + "','" + gender + "'," + year + " )";
            statement.executeUpdate(s);
            statement.executeUpdate(s1);
            JOptionPane.showMessageDialog(null, "Registered!");
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Rater");
        stage.show();
        }
        else{
            JOptionPane.showMessageDialog(null, "Try another username!");
        }
    }
    }
@FXML
    void backtolog(ActionEvent event) throws IOException
    {   
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Rater");
        stage.show();
                
    }
    
    
    @Override
        public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox1.getItems().addAll(gen);
        // restricting byear textfield to nymbers only
    byear.textProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, 
        String newValue) {
        if (!newValue.matches("\\d*")) {
            byear.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
});
    }    
    
}
