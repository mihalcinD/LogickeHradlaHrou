package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;

import main.java.steakoverflow.Entity;
import main.java.steakoverflow.Input;
import main.java.steakoverflow.Output;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Controller_Level
{

    public GridPane playArea;
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

                Entity entity;

                switch (element.get(0).toString())
                {
                    case "input":
                        entity = new Input(element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Boolean.parseBoolean(element.get(3).toString()), Boolean.parseBoolean(element.get(4).toString()));
                        playArea.add(entity.getImg(), entity.getTableX(), entity.getTableY());
                        break;
                    case "output":
                        entity = new Output(element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Boolean.parseBoolean(element.get(3).toString()), Boolean.parseBoolean(element.get(4).toString()));
                        playArea.add(entity.getImg(), entity.getTableX(), entity.getTableY());
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
