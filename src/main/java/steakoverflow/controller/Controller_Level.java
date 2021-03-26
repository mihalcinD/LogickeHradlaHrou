package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import main.java.steakoverflow.Entity;
import main.java.steakoverflow.Input;
import main.java.steakoverflow.Main;
import main.java.steakoverflow.Output;
import main.java.steakoverflow.gates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Controller_Level implements Initializable
{

    public AnchorPane playArea;
    public Text levelID;
    private int id;
    public Polygon checkButton;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Line> cables = new ArrayList<>();

    public void switchSceneToMenu(ActionEvent event) throws IOException
    {
        Main.activeWindow = 0;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
    }

    public void setId(int id)
    {
        this.id = id;
        levelID.setText("Level " + this.id);
    }

    public void renderElements()
    {
        playArea.getChildren().clear();
        JSONParser parser = new JSONParser();
        Line cable = null;
        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Logicke_hradla_levels.json"));
            JSONObject jsonObject = (JSONObject) obj;

            //int numberOfLevels = Integer.parseInt(jsonObject.get("levelNmb").toString());
            JSONObject level = (JSONObject) jsonObject.get("level" + id);

            for (int i = 1; i < Integer.parseInt(level.get("elementNmb").toString()) + 1; i++)
            {
                JSONArray element = (JSONArray) level.get("element" + i);

                Entity entity = null;


                switch (element.get(0).toString())
                {
                    case "input":
                        entity = new Input(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Boolean.parseBoolean(element.get(3).toString()), Boolean.parseBoolean(element.get(4).toString()));
                        break;
                    case "output":
                        entity = new Output(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Boolean.parseBoolean(element.get(3).toString()), Boolean.parseBoolean(element.get(4).toString()));
                        break;
                    case "NOT":
                        entity = new NOT(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "AND":
                        entity = new AND(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "NAND":
                        entity = new NAND(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "OR":
                        entity = new OR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "NOR":
                        entity = new NOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "XOR":
                        entity = new XOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                    case "XNOR":
                        entity = new XNOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()));
                        break;
                }

                if (entity != null)
                {

                    playArea.getChildren().add(entity.getImg());
                    generateElementToPlayArea(playArea.getWidth(), playArea.getHeight(), entity.getImg(), entity.getImg().getFitWidth(), entity.getImg().getFitHeight(), Double.parseDouble(element.get(1).toString()), Double.parseDouble(element.get(2).toString()));
                    // generateElementToPlayArea(playArea.getWidth(), playArea.getHeight(), entity.getImg(),entity.getImg().getFitWidth(),entity.getImg().getFitHeight(), 50, 50); //malo by vygenerovat presne v strede AnchorPanu
                    entities.add(entity);
                    entity.getImg().getFitWidth();
                }


            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void generateElementToPlayArea(double widthPane, double heightPane, Node element, double widthElement, double heightElement, double percentageFromLeft, double percentageFromTop)
    {
        AnchorPane.setLeftAnchor(element, (widthPane * (percentageFromLeft / 100)) - (widthElement / 2));
        AnchorPane.setTopAnchor(element, (heightPane * (percentageFromTop / 100)) - (heightElement / 2));
    }

    private void clearPane()
    {
        playArea.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        checkButton.setOnMouseClicked(mouseEvent ->
        {
            clearPane();
            renderElements();
        });
        playArea.heightProperty().addListener(
                (observable) ->
                        renderElements());
    }
}
