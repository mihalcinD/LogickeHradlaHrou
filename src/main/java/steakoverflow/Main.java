package main.java.steakoverflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application
{
    public static int width, height;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loadSettings();
        Parent root = FXMLLoader.load(getClass().getResource("../../res/view/menu.fxml"));
        primaryStage.setTitle("Logické hradlá hrou");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void loadSettings()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/main/res/settings.cfg"));
            width = Integer.parseInt(br.readLine().split("= ")[1]);
            height = Integer.parseInt(br.readLine().split("= ")[1]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            width = 800;
            height = 600;
        }
    }
}
