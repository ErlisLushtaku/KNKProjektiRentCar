
import Utils.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.LangEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.User;

import java.awt.event.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent= FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws Exception {
         launch(args);
    }
}
