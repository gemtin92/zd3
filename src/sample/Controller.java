package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Controller {
    @FXML TextField nameTextField;
    @FXML TextField phoneTextField;

    @FXML Button addButton;
    @FXML Button editButton;
    @FXML Button deleteButton;

    @FXML TableView<Person> phoneBookTableView;

    //dodanie
    @FXML public void addToPhoneBook(ActionEvent event) {
        ObservableList<Person> data = phoneBookTableView.getItems();
        data.add(new Person(nameTextField.getText(), phoneTextField.getText()));
        nameTextField.setText("");
        phoneTextField.setText("");
        saveToFile();
    }

    //usuniecie
    @FXML public void deleteFromPhoneBook(ActionEvent event){
        ObservableList<Person> data = phoneBookTableView.getItems();
        data.remove(phoneBookTableView.getSelectionModel().getSelectedIndex());

        nameTextField.setText("");
        phoneTextField.setText("");
        saveToFile();
    }

    //zaznaczenie
    @FXML public void loadDataToTextField(MouseEvent event){
        Person tempPerson = phoneBookTableView.getSelectionModel().getSelectedItem();
        nameTextField.setText(tempPerson.getName());
        phoneTextField.setText(tempPerson.getPhone());
    }

    //update
    @FXML public void editInPhoneBook(ActionEvent event){
        ObservableList<Person> data = phoneBookTableView.getItems();
        data.remove(phoneBookTableView.getSelectionModel().getSelectedIndex());
        addToPhoneBook(null);
        saveToFile();
    }

    //odczyt pliku
    public void initialize() {
        try {
            File file = new File("phones.dat");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);

                ObservableList<Person> data = phoneBookTableView.getItems();

                while(scanner.hasNext()) {
                    data.add(new Person(scanner.nextLine(), scanner.nextLine()));
                }

                scanner.close();
            }
        }
        catch (IOException err) {
            nameTextField.setText("Read error!");
            phoneTextField.setText(err.getMessage());
        }
    }

    //zapis pliku
    public void saveToFile(){
        try {
            PrintWriter printWriter = new PrintWriter("phones.dat");

            ObservableList<Person> data = phoneBookTableView.getItems();
            data.forEach(item -> { printWriter.println(item.getName()); printWriter.println(item.getPhone());});

            printWriter.close();
        }
        catch (IOException err) {
            nameTextField.setText("Write error!");
        }
    }
}
