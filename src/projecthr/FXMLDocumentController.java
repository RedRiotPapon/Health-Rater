/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author itxpa
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField uname;
    @FXML
    private PasswordField pass;

    @FXML
    private Button signup;
    @FXML
    private Button login;

    @FXML
    void loginHandler(ActionEvent event) throws IOException, SQLException {
        String name = uname.getText();
        String password = pass.getText();

        if (name.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Field is Blank!!");
        } else {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM logininfo where name = '" + name + "'AND password ='" + password + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Success!!");
                uname.setText("");
                pass.setText("");
                //Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
                Parent root = loader.load();
                HomePageController homepageController = loader.getController();
                homepageController.showname(name);
                homepageController.hidekabel();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Health Rater");
                stage.show();

            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username of Password!");

            }


        }
    }

    @FXML
    void signupHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Rater");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
