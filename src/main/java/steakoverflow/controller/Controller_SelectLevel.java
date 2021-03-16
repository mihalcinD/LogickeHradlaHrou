package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;

public class Controller_SelectLevel
{

    public HBox hbox;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../res/view/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void switchSceneToLevel(ActionEvent event) throws IOException
    {
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../res/view/level.fxml"));
        Parent root = loader.load();
        //Get controller of level scene
        Controller_Level controller_level = loader.getController();
        //Pass id to another controller
        controller_level.setId(value);
        controller_level.renderElements();
        Scene tableViewScene = new Scene(root);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
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
