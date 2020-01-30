package ppa;

import javafx.application.Application;
import javafx.scene.control.ColorPicker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{ //nadpisywanie motody start z Application, parametr primaryStage zawiera pierwszy stage, zwykle dyktowant przez platforme
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml")); // loader z FXML
        Parent root = loader.load(); //obiekt Parent, zloadowana z FXML hierarchia obiektów
        Controller ctrl = loader.getController(); //controller przypiany do FXML
        primaryStage.setTitle("PPA 02"); // tytu³
        primaryStage.setScene(new Scene(root, 900, 700)); // ustaw scene, z hierachii, obiekt Parent, szerokosc, wysokosc sceny
        primaryStage.getScene().getStylesheets().add("ppa/sample.css"); //dodaj css do sceny; stylizowanie
        primaryStage.show(); //poka¿ stage
        ctrl.setStage(primaryStage); // ustaw pierwszy stage
		
    }

    public static void main(String[] args) {
        launch(args); //launch() dzia³a tylko na subklasach Application
    }
}
