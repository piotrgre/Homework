package ppa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    private Stage stage; //controler modyfikuje stage

    public void nextScreen(ActionEvent actionEvent) { //zmienia scene na stage'u
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("sample2.fxml")), 900.0, 700.0)); // ustawia scene za³adowan¹ z sample2.fxml
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}