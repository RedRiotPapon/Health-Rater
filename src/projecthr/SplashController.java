/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;
import javafx.animation.RotateTransition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.transitions.JFXFillTransition;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class SplashController implements Initializable {

    /**
     * Initializes the controller class.    
     */
       @FXML
    private Circle c1;

    @FXML
    private Circle c2;
     public static Circle cc2;

    @FXML
    private Circle c3;
    @FXML
    private ProgressBar progressBar;
    public static ProgressBar statProgressBar;

    @FXML
    private Label progress;
public static Label label;
    @FXML
    private JFXButton btn;
    int i =0;
   /* void showsplash()throws SQLException{
        
        int rating =100;
        JFXFillTransition transition = new JFXFillTransition();
        transition.setDuration(Duration.millis(2000));
        transition.setRegion(btn);
        transition.setFromValue(Color.WHITESMOKE);
        transition.setToValue(Color.rgb(0, 230, 118));
        transition.play();


    }*/
   /* public void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDuration(Duration.millis(duration));
        rt.setRate(3);
        rt.setCycleCount(10);
        rt.play();
    }*/
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
       label = progress ;
       statProgressBar = progressBar;
       //cc2 = c2;
        //setRotate(c1, true, 360, 600);
       

    }    

}
