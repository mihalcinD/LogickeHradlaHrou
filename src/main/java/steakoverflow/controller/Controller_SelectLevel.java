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
import org.json.simple.parser.JSONParser;

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
                Pane btnPane = new Pane();
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
                ImageView checkMark = new ImageView(new Image(new FileInputStream("src/main/res/images/checkmark.png")));
                checkMark.setPreserveRatio(true);
                checkMark.setFitWidth(40);
                checkMark.setX(17);
                checkMark.setY(19);
                checkMark.setVisible(false);
                if (progression[i - 1])
                {
                    checkMark.setVisible(true);
                }
                btnPane.getChildren().add(btn);
                btnPane.getChildren().add(checkMark);
                hbox.getChildren().add(btnPane);
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
