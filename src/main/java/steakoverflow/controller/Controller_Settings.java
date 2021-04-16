package main.java.steakoverflow.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.steakoverflow.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.print.attribute.standard.Media;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Controller_Settings
{
    public ComboBox<String> comboBox;
    public VBox alertBox;
    public Button applyBtn;
    public Slider sliderMusic, sliderSounds;
    public Text volumeMusic, volumeSounds;
    private boolean needRestart = false;
    private boolean resetProgress = false;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Main.activeWindow = 0;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);


        sliderMusic.setValue(Main.volumeMusic);
        sliderSounds.setValue(Main.volumeSounds);
        volumeMusic.setText(String.valueOf(Main.volumeMusic));
        volumeSounds.setText(String.valueOf(Main.volumeSounds));
        Main.player.setVolume((double) Main.volumeMusic / 100);
        applyBtn.setVisible(false);

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
        needRestart = true;
    }

    public void saveSettings()
    {
        if (resetProgress) deleteProgress();
        try
        {
            Main.volumeMusic = (int) sliderMusic.getValue();
            Main.volumeSounds = (int) sliderSounds.getValue();

            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/res/settings.cfg"));
            bw.write("width = " + Main.width);
            bw.newLine();
            bw.write("height = " + Main.height);
            bw.newLine();
            bw.write("fullscreen = " + Main.fullscreen);
            bw.newLine();
            bw.write("fullscreenWindowed = " + Main.fullscreenWindowed);
            bw.newLine();
            bw.write("volumeMusic = " + Main.volumeMusic);
            bw.newLine();
            bw.write("volumeSounds = " + Main.volumeSounds);
            bw.close();
            if (needRestart)
                alertBox.setVisible(true);
            else
                hideAlertBox();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void valueMusicUpdate()
    {
        volumeMusic.setText(String.valueOf((int) sliderMusic.getValue()));
        Main.player.setVolume(sliderMusic.getValue() / 100);
        applyBtn.setVisible(true);
    }

    public void valueSoundsUpdate()
    {
        volumeSounds.setText(String.valueOf((int) sliderSounds.getValue()));
        applyBtn.setVisible(true);
    }

    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList("900x700", "1280x720", "Fullscreen (windowed)", "Fullscreen");
        comboBox.setItems(options);
        if (Main.fullscreen) comboBox.setValue("Fullscreen");
        else if (Main.fullscreenWindowed) comboBox.setValue("Fullscreen (windowed)");
        else comboBox.setValue(Main.width + "x" + Main.height);

        sliderMusic.setValue(Main.volumeMusic);
        sliderSounds.setValue(Main.volumeSounds);
        volumeMusic.setText(String.valueOf(Main.volumeMusic));
        volumeSounds.setText(String.valueOf(Main.volumeSounds));

    }

    public void exitGame(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void hideAlertBox()
    {
        alertBox.setVisible(false);
        applyBtn.setVisible(false);
    }

    public void resetProgress()
    {
        resetProgress = true;
        applyBtn.setVisible(true);
    }

    public void deleteProgress()
    {
        try
        {
            int numberOfLevels = getNumberOfLevels();

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < numberOfLevels; i++)
            {
                jsonArray.add(false);
            }

            jsonObject.put("progress", jsonArray);
            Files.write(Paths.get("src/main/res/Progression.json"), jsonObject.toJSONString().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getNumberOfLevels()
    {
        int numberOfLevels = 0;
        JSONParser parser = new JSONParser();

        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Progression.json"));
            JSONObject object = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) object.get("progress");

            numberOfLevels = jsonArray.size();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return numberOfLevels;

    }

}
