package main.java.steakoverflow.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import main.java.steakoverflow.Main;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;


import org.json.simple.parser.JSONParser;

public class Controller_SelectLevel
{

    public HBox hbox;

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
        try
        {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/res/Logicke_hradla_levels.json"));
            JSONObject jsonObject = (JSONObject) obj;

            int numberOfLevels = Integer.parseInt(jsonObject.get("levelNmb").toString());

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
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
