package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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

    public void initialize()
    {
        JSONParser parser = new JSONParser();

        try
        {
            // ../../../res/Logicke_hradla_levels.json
            Object obj = parser.parse(new FileReader("D:\\Projects\\Programovanie\\Java\\LogickeHradlaHrou\\src\\main\\res\\Logicke_hradla_levels.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.toString());

            JSONObject level = (JSONObject) jsonObject.get("level1");
            System.out.println(level);

            Object test = (Object) level;

            //Iterator<JSONObject> iterator = level.iterator();


            JSONArray element = (JSONArray) level.get("element1");
            System.out.println(element);


            JSONArray element2 = (JSONArray) level.get("element2");
            System.out.println(element2.get(0));


            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.

            //Iterator<JSONObject> iterator = object.iterator();
            //while (iterator.hasNext()) {
            //    System.out.println(iterator.next());
            //}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
