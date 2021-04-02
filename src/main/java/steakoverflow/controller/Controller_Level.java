package main.java.steakoverflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
    public Button pauseBtn;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Line> cables = new ArrayList<>();
    private long start_time;
    private int attempts;

    public void switchSceneToMenu()
    {
        Main.activeWindow = 0;
        Main.rootScene.setRoot(Main.roots[Main.activeWindow]);
    }

    public void switchSceneToSelectLevel()
    {
        Main.activeWindow = 1;
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
        pauseBtn.setVisible(true);
        checkButton.setVisible(true);
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
                        //inputs and outputs set different width
                        entity.getImg().setFitWidth(102);
                    }
                    playArea.getChildren().add(entity.getImg());

                    generateElementToPlayArea(playArea.getWidth(), playArea.getHeight(), entity.getImg(), entity.getImg().getFitWidth(), entity.getImg().getFitHeight(), Double.parseDouble(element.get(1).toString()), Double.parseDouble(element.get(2).toString()));
                    entities.add(entity);

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
                    else if (entity instanceof Output)
                    {
                        int outputXPositionOfClamp = 12;
                        int outputYPositionOfClamp = 22;
                        int outputLockedYPositionOfClamp = 34;
                        int gateXPositionOfClamp = 9;
                        int gateYPositionOfMiddleClamp = 46;

                        cable = new Line();
                        cable.setStroke(Color.web("#CA5801"));
                        cable.setStrokeWidth(7.0);
                        cable.setStartX(((entity.getImg().getX() / 100) * playArea.getWidth()) - (entity.getImg().getFitWidth() / 2 - outputXPositionOfClamp));

                        if (((Output) entity).isLocked())
                        {
                            cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + outputLockedYPositionOfClamp);
                        }
                        else
                            cable.setStartY(((entity.getImg().getY() / 100) * playArea.getHeight()) + outputYPositionOfClamp);

                        Entity entityToJoin = entities.get(Integer.parseInt(((Output) entity).getConnectionID()) - 1);

                        cable.setEndX(((entityToJoin.getImg().getX() / 100) * playArea.getWidth()) + (entityToJoin.getImg().getFitWidth() / 2 - gateXPositionOfClamp));
                        cable.setEndY(((entityToJoin.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOfMiddleClamp);


                        playArea.getChildren().add(cable);
                        cable.setStrokeLineCap(StrokeLineCap.ROUND);
                        cables.add(cable);
                    }
                }


            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        attempts = 0;
        // start counting time
        start_time = System.nanoTime();

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
        attempts++;
        boolean level_passed = true;

        for (Entity entity : entities)
        {
            if (entity instanceof Gate)
            {
                // set inputs to object
                int number_of_inputs = ((Gate) entity).getInputIDs().length;
                boolean[] values = new boolean[number_of_inputs];

                for (int i = 0; i < number_of_inputs; i++)
                {
                    values[i] = entities.get(Integer.parseInt(((Gate) entity).getInputIDs()[i]) - 1).isValue();
                }

                ((Gate) entity).setInputValues(values);
                // set output
                ((Gate) entity).check();
            }
            if (entity instanceof Output)
            {
                if (entities.get((Integer.parseInt(((Output) entity).getConnectionID())) - 1).isValue() != entity.isValue())
                {
                    level_passed = false;
                }
            }
        }

        if (level_passed)
        {
            //toto sa vykona ked vsetky outputs budu spravne
            showPassWindow();
            checkButton.setVisible(false);
            pauseBtn.setVisible(false);
        }
        else
        {
            System.out.println("Mas to zle pepega");
        }

    }

    private void showPassWindow()
    {
        BoxBlur bb = new BoxBlur();
        bb.setWidth(20);
        bb.setHeight(20);
        bb.setIterations(3);

        for (Entity entita : entities)
        {
            entita.getImg().setEffect(bb);
        }
        for (Line line : cables)
        {
            line.setEffect(bb);
        }

        long finish_time = System.nanoTime();
        double time_elapsed_sec = ((double) (finish_time - start_time) / 10000000);
        time_elapsed_sec = (double) Math.round(time_elapsed_sec) / 100;
        int time_elapsed_min = (int) time_elapsed_sec / 60;
        System.out.println(time_elapsed_sec);
        VBox vbox = new VBox();
        Text pass_text = new Text("LEVEL " + id + " zvládnutý");
        Text time_text;
        if (time_elapsed_min > 0)
        {
            time_elapsed_sec = time_elapsed_sec - (time_elapsed_min * 60);
            time_text = new Text("Celkový čas: " + time_elapsed_min + "m " + (int) time_elapsed_sec + "s");
        }
        else
        {
            time_text = new Text("Celkový čas: " + time_elapsed_sec + "s");
        }

        Text attempts_text = new Text("Počet pokusov: " + attempts);
        Text menu_text = new Text("Menu");
        ImageView stars = null;
        try
        {
            stars = new ImageView(new Image(new FileInputStream("src/main/res/images/stars.png")));
            stars.setPreserveRatio(true);
            stars.setFitWidth(200);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        HBox btns_hbox = new HBox();
        Button reset_btn = new Button("Reštart");
        Button continue_btn = new Button("Pokračovať");
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setStyle("-fx-background-color: rgba(0,0,0,0.35);");
        vbox.setPrefWidth(315);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        pass_text.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        time_text.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        attempts_text.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        menu_text.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #797979;");
        menu_text.setCursor(Cursor.HAND);
        menu_text.setOnMouseClicked(mouseEvent ->
        {
            switchSceneToMenu();
        });
        btns_hbox.setSpacing(20);
        btns_hbox.setAlignment(Pos.CENTER);
        btns_hbox.setPadding(new Insets(20, 0, 0, 0));
        btns_hbox.getChildren().add(reset_btn);
        btns_hbox.getChildren().add(continue_btn);
        reset_btn.getStyleClass().add("passBtns");
        continue_btn.getStyleClass().add("passBtns");
        reset_btn.setPrefWidth(137);
        reset_btn.setPrefHeight(46);
        reset_btn.setCursor(Cursor.HAND);
        reset_btn.setOnMouseClicked(mouseEvent ->
        {
            renderElements();
        });
        continue_btn.setPrefWidth(137);
        continue_btn.setPrefHeight(46);
        continue_btn.setCursor(Cursor.HAND);
        continue_btn.setOnMouseClicked(mouseEvent ->
        {
            switchSceneToSelectLevel();
        });
        vbox.getChildren().add(pass_text);
        vbox.getChildren().add(attempts_text);
        vbox.getChildren().add(time_text);
        vbox.getChildren().add(stars);
        vbox.getChildren().add(btns_hbox);
        vbox.getChildren().add(menu_text);
        playArea.getChildren().add(vbox);
        System.out.println(playArea.getWidth() * 0.5);
        AnchorPane.setLeftAnchor(vbox, ((playArea.getWidth() * 0.5) - 157.5));
        AnchorPane.setTopAnchor(vbox, 50.0);

    }


}
