package main.java.steakoverflow.controller;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.java.steakoverflow.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Settings
{
    public ComboBox<String> comboBox;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../res/view/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void updateSettings(ActionEvent event)
    {
        String resolution[] = comboBox.getValue().split("x");
        Main.width = Integer.parseInt(resolution[0]);
        Main.height = Integer.parseInt(resolution[1]);

        System.out.println(Main.width);
        System.out.println(Main.height);
    }

    public void saveSettings()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/res/settings.cfg"));
            bw.write("width = " + Main.width);
            bw.newLine();
            bw.write("height = " + Main.height);
            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList("900x700", "1280x720", "1920x1080");
        comboBox.setItems(options);
        comboBox.setValue(Main.width + "x" + Main.height);

    }


}
