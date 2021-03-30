package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import main.java.steakoverflow.*;
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
        entities.clear();
        cables.clear();
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
                        entity = new Output(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Boolean.parseBoolean(element.get(3).toString()), Boolean.parseBoolean(element.get(4).toString()), element.get(5).toString());
                        break;
                    case "NOT":
                        entity = new NOT(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "AND":
                        entity = new AND(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "NAND":
                        entity = new NAND(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "OR":
                        entity = new OR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "NOR":
                        entity = new NOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "XOR":
                        entity = new XOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                    case "XNOR":
                        entity = new XNOR(i, element.get(0).toString(), Integer.parseInt(element.get(1).toString()), Integer.parseInt(element.get(2).toString()), Integer.parseInt(element.get(3).toString()), element.get(4).toString().split(","));
                        break;
                }

                if (entity != null)
                {

                    entity.getImg().setViewOrder(0.0);
                    if (!(entity instanceof Gate))
                    {
                        entity.getImg().setFitWidth(102);
                    }
                    playArea.getChildren().add(entity.getImg());
                    generateElementToPlayArea(playArea.getWidth(), playArea.getHeight(), entity.getImg(), entity.getImg().getFitWidth(), entity.getImg().getFitHeight(), Double.parseDouble(element.get(1).toString()), Double.parseDouble(element.get(2).toString()));
                    entities.add(entity);
                    // System.out.println(entities.get(i-1).getImg().getX() +" " +entities.get(i-1).getImg().getY());
                    if (entity instanceof Gate)
                    {
                        int gateXPositionOfClamp = 9;
                        int gateYPositionOf1stClamp = 19;
                        int gateYPositionOf2ndClamp = 71;
                        int gateYPositionOfMiddleClamp = 45;
                        int gateYPositionOf3ndClamp = 71;

                        int inputXPositionOfClamp = 12;
                        int inputYPositionOfClamp = 22;
                        int inputLockedYPositionOfClamp = 34;

                        for (int j = 1; j < ((Gate) entity).getInputIDs().length + 1; j++)
                        {
                            cable = new Line();
                            cable.setStroke(Color.web("#CA5801"));
                            cable.setStrokeWidth(7.0);
                            cable.setStartX(((entity.getImg().getX() / 100) * playArea.getWidth()) - (entity.getImg().getFitWidth() / 2 - gateXPositionOfClamp));
                            if (((Gate) entity).getInputIDs().length != 1)
                            {
                                switch (j)
                                {
                                    case 1:
                                        cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOf1stClamp);
                                        break;
                                    case 2:
                                        if (((Gate) entity).getInputIDs().length == 2)
                                        {
                                            cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOf2ndClamp);
                                        }
                                        else
                                            cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOfMiddleClamp);
                                        break;
                                    case 3:
                                        cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOf3ndClamp);
                                        break;
                                    default:
                                        cable.setStartY(0);

                                }
                            }
                            else
                            {
                                cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOfMiddleClamp);
                            }
                            Entity entityToJoin = entities.get(Integer.parseInt(((Gate) entity).getInputIDs()[j - 1]) - 1);

                            if (entityToJoin instanceof Input)
                            {
                                cable.setEndX(((entityToJoin.getImg().getX() / 100) * playArea.getWidth()) + (entityToJoin.getImg().getFitWidth() / 2 - inputXPositionOfClamp));
                                if (((Input) entityToJoin).isLocked())
                                {
                                    cable.setEndY(((entityToJoin.getImg().getY() / 100) * playArea.getHeight()) + inputLockedYPositionOfClamp);
                                }
                                else
                                    cable.setEndY(((entityToJoin.getImg().getY() / 100) * playArea.getHeight()) + inputYPositionOfClamp);
                            }
                            else
                            {
                                //code for join gate with gate
                            }
                            playArea.getChildren().add(cable);
                            cable.setStrokeLineCap(StrokeLineCap.ROUND);
                            cables.add(cable);
                        }

                    }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        checkButton.setOnMouseClicked(mouseEvent ->
        {
            checkConnection();
        });
        playArea.heightProperty().addListener(
                (observable) ->
                        renderElements());
    }

    public void checkConnection()
    {
        for (Entity entity : entities)
        {
            if (entity instanceof Gate)
            {
                // set inputs to object
                int number_of_inputs = ((Gate) entity).getInputIDs().length;
                boolean[] values = new boolean[number_of_inputs];

                for (int i = 0; i < number_of_inputs; i++)
                {
                    values[i] = entities.get(Integer.parseInt(((Gate) entity).getInputIDs()[i])).isValue();
                }

                ((Gate) entity).setInputValues(values);
                // set output
                ((Gate) entity).check();
            }
            if (entity instanceof Output)
            {
                if (entities.get(Integer.parseInt(((Output) entity).getConnectionID())).isValue() == entity.isValue())
                {
                    System.out.println("DONE");
                }
                else
                {
                    System.out.println("NONE");
                }
            }

        }
    }


}
