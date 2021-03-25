package main.java.steakoverflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main extends Application
{
    public static int width, height;
    public static Stage mainStage;
    public static Scene rootScene;
    public static Parent[] roots = new Parent[4];
    public static int activeWindow = 0;
    private String[] paths = {"menu", "selectLevel", "level", "settings"};

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loadSettings();
        for (int i = 0; i < roots.length; i++)
        {
            roots[i] = FXMLLoader.load(getClass().getResource("../../res/view/" + paths[i] + ".fxml"));
        }
        primaryStage.setTitle("Logické hradlá hrou");
        primaryStage.getIcons().add(new Image("file:src/main/res/images/Hradla_hrou_icon.png"));
        rootScene = new Scene(roots[0], width, height);
        primaryStage.setScene(rootScene);
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
