/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class BMIController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    String loginName;
    double bm=0;
    @FXML
    private Label lblnam;
    public void shownam (String s)
    {   loginName = s;
        //lblnam.setText(s);
    }
    @FXML
    private Button back;
    @FXML
    private Label showbmi;
    @FXML
    private Button calc;
    Double[] foot = {1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00, 11.00, 12.00};
    Double[] inch = {0.0,1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00, 11.00};
    Double f,in;
    double hei;
    int wei;
    @FXML
    private TextField kg;
    @FXML
    void updateBmi(ActionEvent event) throws IOException, SQLException {
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "update central set BMI = ? ,Height = ? ,Weight = ? where name = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setDouble    (1,bm);
      preparedStmt.setDouble(2,hei);
      preparedStmt.setInt(3,wei);
      preparedStmt.setString(4,loginName);
      preparedStmt.executeUpdate();
      preparedStmt.close();
         conn.close();
         String st = ("Profile Updated!");
            JLabel label = new JLabel(st);
            label.setFont(new Font("comic sans", Font.BOLD, 18));
             JOptionPane.showMessageDialog(null, label, "Health tips", JOptionPane.INFORMATION_MESSAGE);
    }
    @FXML
    void b2h(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));	
		Parent root = loader.load();	
		HomePageController homepageController = loader.getController();
		homepageController.showname(loginName);	
                 homepageController.hidekabel();
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
                stage.setTitle("Health Rater");
		stage.show();
    }
    
    
    @FXML
    private ChoiceBox<Double> myChoiceBox1;
    @FXML
    private ChoiceBox<Double> myChoiceBox2;
    @FXML
    void calcBmi(ActionEvent event){
        
        Double height = 0.3048*f + 0.0254*in;
        Double weight = Double.parseDouble(kg.getText());
        Double b = weight/(height*height);
        hei=f+0.1*in;
        wei=weight.intValue();
          
        b = Double.parseDouble(String.format("%.2f", b));
        bm=b;
        String bmi=String.valueOf(b);  
        showbmi.setText(bmi);
        if(b>=25.0){
            JOptionPane.showMessageDialog(null, "Warning! Loosen up some fat");
        } 
        else if (b<18.5)
        {
            JOptionPane.showMessageDialog(null, "Warning! Gain some wait");
        }
        else{
            JOptionPane.showMessageDialog(null, "Congrats! You are in good shape");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox1.getItems().addAll(foot);
        myChoiceBox1.setOnAction(this::getFoot);
        myChoiceBox2.getItems().addAll(inch);
        myChoiceBox2.setOnAction(this::getInch);
    }

    public void getFoot(ActionEvent event) {

        Double myFoot = myChoiceBox1.getValue();
        f=myFoot;
    }

    public void getInch(ActionEvent event) {

        Double myInch = myChoiceBox2.getValue();
         in =myInch;

    }

}
