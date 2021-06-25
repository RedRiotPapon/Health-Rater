/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class StatController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private AnchorPane anchor1;
    String loginName;
    private int systol, diastol, heartrat, rate, chron, nonchron, wei, age;
    private Double hei, b;

    @FXML
    private Label bm;
    @FXML
    private ProgressIndicator pi;
    @FXML
    private Label djs;
    @FXML
    private Label djs1;
    @FXML
    private JFXSpinner spiner;

    @FXML
    private Label hr;

    @FXML
    private Label bp;
    @FXML
    private Label bp1;
    @FXML
    private Circle imgcrcl;
    @FXML
    private Label uname;
    @FXML
    private Label rat;
    @FXML
    private Label hLabel;
    @FXML
    private Label wLabel;
    @FXML
    private Label ageLabel;

    @FXML
    private JFXButton barbtn;

    @FXML
    private JFXButton back;
    @FXML
    private StackPane parentContainer;

    @FXML
    void b2h(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        HomePageController homepageController = loader.getController();
        homepageController.showname(loginName);
         homepageController.hidekabel();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Health Rater");
        stage.show();
    }

    @FXML
    void showbar(ActionEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BARGRAPHS.fxml"));
        Parent root = loader.load();
        BARGRAPHSController bargraphsc = loader.getController();
        bargraphsc.shownam(loginName, gender);
        bargraphsc.getdata();
       // bargraphsc.getAvgRating(overallrating);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("barchart.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Chart");
        stage.show();

    }
    List<Integer> list = new ArrayList<>();
    Year year = Year.now();
    int overallrating=0;
    void getInfo() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        ResultSet resultset = statement.executeQuery("select * from central where name = '" + loginName + "' ");
        while (resultset.next()) {
            b = resultset.getDouble("bmi");
            bm.setText(String.valueOf(b));
            systol = resultset.getInt("systolic");
            diastol = resultset.getInt("diastolic");
            bp.setText(String.valueOf(diastol));
            bp1.setText(String.valueOf(systol));
            heartrat = resultset.getInt("heartrate");
            hr.setText(String.valueOf(heartrat));
            chron = resultset.getInt("chronicno");
            djs.setText(String.valueOf(chron));
            nonchron = resultset.getInt("nonchronicno");
            djs1.setText(String.valueOf(nonchron));
            rate = resultset.getInt("rating");
            rat.setText(String.valueOf(rate));
            hei = resultset.getDouble("height");
            hLabel.setText(String.valueOf(hei));
            wei = resultset.getInt("weight");
            wLabel.setText(String.valueOf(wei));
            age = resultset.getInt("year");
            age = year.getValue() - age;
            ageLabel.setText(String.valueOf(age));
            uname.setText(loginName);

        }
        Double r = (double) rate;
        pi.setProgress(r / 100);
 
    }
    int gender;

    public void shownam(String s) {
        loginName = s;
    }

    public void shownameandgender(String s, int g) {
        loginName = s;
        uname.setText(loginName);
        gender = g;
        System.out.println(gender);
        if (gender != 1) {
            Image im = new Image("f.png", false);
            System.out.println("here");
            imgcrcl.setFill(new ImagePattern(im));
        } else {
            System.out.println("male");
            Image im = new Image("V.png", false);
            imgcrcl.setFill(new ImagePattern(im));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
