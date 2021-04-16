package main.java.steakoverflow.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.steakoverflow.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Controller_SelectLevel
{

    public HBox hbox;
    private boolean[] progression;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Main.activeWindow = 0;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);

    }

    public void switchSceneToLevel(ActionEvent event) throws IOException
    {
        // getting id from button
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        Main.activeWindow = 2;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);

        //Get controller of level scene
        FXMLLoader loader = (FXMLLoader) Main.roots[Main.activeWindow].getUserData();
        Controller_Level controller = loader.getController();
        //Pass id to another controller
        controller.setId(value);
        controller.renderElements();
    }

    public void initialize()
    {
        hbox.getChildren().clear();

        try
        {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/res/Logicke_hradla_levels.json"));
            JSONObject jsonObject = (JSONObject) obj;

            int numberOfLevels = Integer.parseInt(jsonObject.get("levelNmb").toString());
            getProgress(numberOfLevels);

            for (int i = 1; i < numberOfLevels + 1; i++)
            {
                Button btn = new Button();
                btn.setUserData(i + "");
                btn.setText(i + "");
                btn.getStyleClass().add("levelBtn");
                btn.setTextFill(Color.WHITE);
                btn.setOnAction(event ->
                {
                    try
                    {
                        switchSceneToLevel(event);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
                hbox.getChildren().add(btn);

                if (progression[i - 1])
                {
                    FileInputStream input = new FileInputStream("src/main/res/images/checkmark.png");
                    Image img = new Image(input);
                    //BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,  BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                    //Background background = new Background(bgImg);
                    //btn.setBackground(background);
                    ImageView imageView = new ImageView();
                    imageView.setImage(img);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(30);
                    hbox.getChildren().add(imageView);

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getProgress(int numberOfLevels)
    {
        JSONParser parser = new JSONParser();

        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Progression.json"));
            JSONObject object = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) object.get("progress");

            progression = new boolean[numberOfLevels];

            for (int i = 0; i < jsonArray.size(); i++)
            {
                progression[i] = Boolean.parseBoolean(jsonArray.get(i).toString());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
