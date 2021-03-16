package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;

import main.java.steakoverflow.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class Controller_Level
{

    public AnchorPane playArea;
    public Text levelID;
    private int id;

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../res/view/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void setId(int id)
    {
        this.id = id;
        levelID.setText("Level " + this.id);
    }

    public void renderElements()
    {

        Input in = new Input("input", 500, 500, false, false);
        playArea.getChildren().add(in.getImg());


        System.out.println(playArea.toString());


        JSONParser parser = new JSONParser();

        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Logicke_hradla_levels.json"));
            JSONObject jsonObject = (JSONObject) obj;

            //int numberOfLevels = Integer.parseInt(jsonObject.get("levelNmb").toString());
            JSONObject level = (JSONObject) jsonObject.get("level" + id);

            for (int i = 1; i < Integer.parseInt(level.get("elementNmb").toString()) + 1; i++)
            {
                JSONArray element = (JSONArray) level.get("element" + i);

                switch (element.get(0).toString())
                {
                    case "input":
                        //code for input
                        break;
                    case "output":
                        //code for output
                        break;
                    case "NOT":
                        //code for NOT
                        break;
                    case "AND":
                        //code for AND
                        break;
                    case "NAND":
                        //code for NAND
                        break;
                    case "OR":
                        //code for OR
                        break;
                    case "NOR":
                        //code for NOR
                        break;
                    case "XOR":
                        //code for XOR
                        break;
                }

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
