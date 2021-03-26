package main.java.steakoverflow.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import main.java.steakoverflow.Main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Controller_Settings
{
    public ComboBox<String> comboBox;
    public VBox alertBox;
    public Button applyBtn;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Main.activeWindow = 0;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
    }

    public void updateSettings(ActionEvent event)
    {
        if (comboBox.getValue().contains("x"))
        {
            String resolution[] = comboBox.getValue().split("x");
            Main.width = Integer.parseInt(resolution[0]);
            Main.height = Integer.parseInt(resolution[1]);
            Main.fullscreen = false;
            Main.fullscreenWindowed = false;

        }
        else if (comboBox.getValue().contains("(windowed)"))
        {
            Main.fullscreenWindowed = true;
            Main.fullscreen = false;
        }
        else
        {
            Main.fullscreen = true;
            Main.fullscreenWindowed = false;
        }
        applyBtn.setVisible(true);
    }

    public void saveSettings()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/res/settings.cfg"));
            bw.write("width = " + Main.width);
            bw.newLine();
            bw.write("height = " + Main.height);
            bw.newLine();
            bw.write("fullscreen = " + Main.fullscreen);
            bw.newLine();
            bw.write("fullscreenWindowed = " + Main.fullscreenWindowed);
            bw.close();
            alertBox.setVisible(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList("900x700", "1280x720", "Fullscreen (windowed)", "Fullscreen");
        comboBox.setItems(options);
        if (Main.fullscreen) comboBox.setValue("Fullscreen");
        else if (Main.fullscreenWindowed) comboBox.setValue("Fullscreen (windowed)");
        else comboBox.setValue(Main.width + "x" + Main.height);

    }


    public void exitGame(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void hideAlertBox(ActionEvent actionEvent)
    {
        alertBox.setVisible(false);
        applyBtn.setVisible(false);
    }
}
