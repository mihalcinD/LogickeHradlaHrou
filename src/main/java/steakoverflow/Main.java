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
    public static double width, height;

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
            width = Double.parseDouble(br.readLine().split("= ")[1]);
            height = Double.parseDouble(br.readLine().split("= ")[1]);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
