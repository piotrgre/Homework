package ppa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import javafx.beans.value.ObservableValueBase;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.converter.IntegerStringConverter;
import ppa.component.CheckBoxEditableTableCell;
import ppa.component.CircleColorPickerTableCell;
import ppa.model.Car;

/**
 * Created by pwilkin on 21-Nov-19.
 */
public class CarTableController {


	
    @FXML
    protected TableView<Car> carTableView;  //powi¹zanie elementu fxmla z controllerem
    

    public void initialize() {
        TableColumn<Car, String> makeColumn = (TableColumn<Car, String>) carTableView.getColumns().get(0); //nowa kolumna (typ tabeli, co jest w komórkach) = pobierana z fxmla pierwsza kolumna
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make")); //okreœla treœæ komórek, uzywajac property make
        makeColumn.setCellFactory(TextFieldTableCell.forTableColumn()); //okreœla formatowanie komórek, uzywajac jednej z gotowych formatów
        makeColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow()); //nowa zmienna edytowanego samochodu
            editedCar.setMake(edit.getNewValue()); //ustawia konkretny CellEditEvent dokonany przez u¿ytkownika jako mowa wartoœæ w³asnoœci make
        });

        TableColumn<Car, String> modelColumn = (TableColumn<Car, String>) carTableView.getColumns().get(1);
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setModel(edit.getNewValue());
        });

        TableColumn<Car, Integer> yearColumn = (TableColumn<Car, Integer>) carTableView.getColumns().get(2);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setYearOfProduction(edit.getNewValue());
        });

        TableColumn<Car, Boolean> dieselColumn = (TableColumn<Car, Boolean>) carTableView.getColumns().get(3);
        dieselColumn.setCellValueFactory(new PropertyValueFactory<>("diesel"));
        dieselColumn.setCellFactory(list -> new CheckBoxEditableTableCell<>());
        dieselColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setDiesel(edit.getNewValue());
        });

        TableColumn<Car, Circle> colorColumn = (TableColumn<Car, Circle>) carTableView.getColumns().get(4);
        colorColumn.setCellValueFactory(carCircleCellDataFeatures -> {
            Car car = carCircleCellDataFeatures.getValue();
            String colorString = car.getColor();
            CircleColorPickerTableCell colorCircle = new CircleColorPickerTableCell();
            colorCircle.getCircle().setFill(Color.web(colorString));
            return new ObservableValueBase<Circle>() {
                @Override
                public Circle getValue() {
                    return colorCircle.getCircle();
                }
            };
        });
        colorColumn.setCellFactory(list -> new CircleColorPickerTableCell<>());
        colorColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setColor(edit.getNewValue().getFill().toString());
        });
        
       
       
        XStream serializer = new XStream();
        try (FileInputStream fis = new FileInputStream("cars.xml")) {
            List<Car> deserializedCars = (List<Car>) serializer.fromXML(new InputStreamReader(fis));
            carTableView.getItems().addAll(deserializedCars);
        } catch (Exception e) {
            e.printStackTrace();
            Car car1 = Car.create("Ford", "Focus", 2006, true, 82.0, "#FFCC33");
            Car car2 = Car.create("Porsche", "Carrera", 1976, true, 233.0, "#000000");
            Car car3 = Car.create("Honda", "Civic", 2010, true, 112.0, "#D2A088");
            carTableView.getItems().addAll(car1, car2, car3);
        }
        carTableView.setEditable(true);
        carTableView.getSelectionModel().cellSelectionEnabledProperty().setValue(true);
        carTableView.setOnMouseClicked(click -> {
            if (click.getClickCount() > 1) {
                editFocusedCell();
            }
        });}
  
        public void save() {
            XStream serializer1 = new XStream();
            String xmlVersion = serializer1.toXML(new ArrayList<>(carTableView.getItems()));
            try (FileOutputStream fos = new FileOutputStream("cars.xml")) { // try-with-resources
                try (PrintWriter pw = new PrintWriter(fos)) {
                    pw.print(xmlVersion);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);}

            }

        
    

    private void editFocusedCell() {
        TablePosition<Car, ?> focusedCell = carTableView.focusModelProperty().get().focusedCellProperty().get();
        carTableView.edit(focusedCell.getRow(), focusedCell.getTableColumn());
       
    }


}
