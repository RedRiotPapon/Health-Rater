/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class TipsonlineController implements Initializable {

    /**
     * Initializes the controller class.
     */
      @FXML
    private JFXButton okay;
    @FXML
    void close(ActionEvent event) {
  Stage stage = (Stage) okay.getScene().getWindow();
    stage.close();
    }
  
    @FXML
    Label label;
    void showtips ()
    {   Random rand = new Random();
        int r = rand.nextInt(100);
                        try {
            Document document = (Document) Jsoup.connect("https://gethealthyu.com/101-fitness-tips-that-rock/").userAgent("mozilla/17.0").get();
            Elements temp =  document.select("div.the-content__body");
            int i=0;
            for (Element lisElement : temp.select("p"))
                {
                      i++;
                      if(i==r)
                      {
                          String s =(lisElement.getElementsByTag("p").first().text());
                          label.setText(s);
                      }
        
        }
        
        
    }
         catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
