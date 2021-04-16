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
        Main.activeWindow = 1;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);

        //Get controller of selectLevel scene
        FXMLLoader loader = (FXMLLoader) Main.roots[Main.activeWindow].getUserData();
        Controller_SelectLevel controller = loader.getController();
        //re-generate scene
        controller.initialize();

    }

    public void switchSceneToSettings(ActionEvent event) throws IOException
    {

        Main.activeWindow = 3;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
    }

    public void closeApp()
    {
        System.exit(0);
    }

}
