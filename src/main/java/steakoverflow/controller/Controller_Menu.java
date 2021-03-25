package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.steakoverflow.Main;

import java.io.IOException;

public class Controller_Menu
{
    public void switchSceneToLevel(ActionEvent event) throws IOException
    {
        //Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../res/view/selectLevel.fxml"));
        //  Scene tableViewScene = new Scene(tableViewParent);
        Main.activeWindow = 1;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
        //This line gets the Stage information
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(tableViewScene);
//        window.show();
    }

    public void switchSceneToSettings(ActionEvent event) throws IOException
    {
//        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../res/view/settings.fxml"));
//        Scene tableViewScene = new Scene(tableViewParent);
//
//        //This line gets the Stage information
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        window.setScene(tableViewScene);
//        window.show();
        Main.activeWindow = 3;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
    }

    public void closeApp()
    {
        System.exit(0);
    }

}
