package main.java.steakoverflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Main extends Application
{
    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static int width, height, volumeMusic, volumeSounds;
    public static Scene rootScene;
    public static Parent[] roots = new Parent[4];
    public static int activeWindow = 0;
    private String[] paths = {"menu", "selectLevel", "level", "settings"};
    public static boolean fullscreenWindowed, fullscreen;
    public static MediaPlayer player;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loadSettings();
        playMusic();

        for (int i = 0; i < roots.length; i++)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../res/view/" + paths[i] + ".fxml"));
            roots[i] = loader.load();

            if (i == 2)
            {
                //Controller_Level controller_level = loader.getController();
                roots[i].setUserData(loader);
            }
        }
        primaryStage.setTitle("Logické hradlá hrou");
        primaryStage.getIcons().add(new Image("file:src/main/res/images/Hradla_hrou_icon.png"));
        rootScene = new Scene(roots[0], width, height);
        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        if (fullscreen)
        {
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("Stlačením ESC zmenšíte okno na defaultné rozmery (" + width + "x" + height + ")");
        }
        else if (fullscreenWindowed)
            primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void playMusic()
    {
        Media media = new Media(new File("src/main/res/music/Pumping_ation_perfection.wav").toURI().toString());
        player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
        player.setVolume((double) Main.volumeMusic / 100);
    }

    public void loadSettings()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/main/res/settings.cfg"));
            width = Integer.parseInt(br.readLine().split("= ")[1]);
            height = Integer.parseInt(br.readLine().split("= ")[1]);
            fullscreen = Boolean.parseBoolean(br.readLine().split("= ")[1]);
            fullscreenWindowed = Boolean.parseBoolean(br.readLine().split("= ")[1]);
            volumeMusic = Integer.parseInt(br.readLine().split("= ")[1]);
            volumeSounds = Integer.parseInt(br.readLine().split("= ")[1]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            width = 900;
            height = 700;
            fullscreen = false;
            fullscreenWindowed = false;
            volumeMusic = 50;
            volumeSounds = 50;
        }

    }

}
