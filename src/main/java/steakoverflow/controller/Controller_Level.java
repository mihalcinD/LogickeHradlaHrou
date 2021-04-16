package main.java.steakoverflow.controller;


import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.util.Duration;
import main.java.steakoverflow.*;
import main.java.steakoverflow.gates.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Controller_Level implements Initializable
{

    public AnchorPane playArea;
    public Text levelID;
    public Polygon checkButton;
    public static boolean isInPause;
    public static MediaPlayer alertPlayer;
    public Button pauseBtn;
    private int id;
    private int numberOfLevels;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Line> cables = new ArrayList<>();
    private long start_time;
    private int attempts, difficulty;
    private final double multiplier_sec = 30;
    private VBox vbox;
    private double time_inPause_sec = 0;
    private boolean canCheck = true;

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
        time_inPause_sec = 0;
        playArea.getChildren().clear();
        entities.clear();
        cables.clear();
        isInPause = false;
        pauseBtn.setVisible(true);
        checkButton.setVisible(true);
        canCheck = true;
        JSONParser parser = new JSONParser();
        Line cable = null;
        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Logicke_hradla_levels.json"));
            JSONObject jsonObject = (JSONObject) obj;

            numberOfLevels = Integer.parseInt(jsonObject.get("levelNmb").toString());
            JSONObject level = (JSONObject) jsonObject.get("level" + id);
            difficulty = Integer.parseInt(level.get("difficulty").toString());

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
                        int gateYPositionOf1stClamp = 17;
                        int gateYPositionOf2ndClamp = 69;
                        int gateYPositionOfMiddleClamp = 43;
                        int gateYPositionOf3ndClamp = 69;

                        int inputXPositionOfClamp = 12;
                        int inputYPositionOfClamp = 22;
                        int inputLockedYPositionOfClamp = 34;

                        int gateXPositionOfOutputClamp = 9;
                        int gateYPositionOfOutputClamp = 44;

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
                                cable.setEndX(((entityToJoin.getImg().getX() / 100) * playArea.getWidth()) + (entityToJoin.getImg().getFitWidth() / 2 - gateXPositionOfOutputClamp));
                                cable.setEndY(((entityToJoin.getImg().getY() / 100) * playArea.getHeight()) + gateYPositionOfOutputClamp);

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
                        int gateYPositionOfMiddleClamp = 44;

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

    public void wrongConnectionNotification()
    {
        try
        {
            ImageView x = new ImageView(new Image(new FileInputStream("src/main/res/images/wrong.png")));
            x.setPreserveRatio(true);
            x.setFitWidth(100);
            playArea.getChildren().add(x);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), x);
            st.setByX(2);
            st.setByY(2);
            st.play();
            st.setOnFinished(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    x.setVisible(false);
                    canCheck = true;
                }
            });
            AnchorPane.setLeftAnchor(x, (playArea.getWidth() * 0.5) - 50);
            AnchorPane.setTopAnchor(x, (playArea.getHeight() * 0.5) - 50);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void checkConnection()
    {
        if (canCheck)
        {
            canCheck = false;
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
                isInPause = true;
                hideControls();
                playSound("success");
                updateProgress();

            }
            else
            {
                wrongConnectionNotification();
                playSound("error");
            }
        }
    }

    private void updateProgress()
    {
        try
        {
            boolean[] progression = getProgress();
            progression[id - 1] = true;

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for (boolean value : progression)
            {
                jsonArray.add(value);
            }


            jsonObject.put("progress", jsonArray);
            Files.write(Paths.get("src/main/res/Progression.json"), jsonObject.toJSONString().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public boolean[] getProgress()
    {
        boolean[] progression = new boolean[numberOfLevels];
        JSONParser parser = new JSONParser();

        try
        {
            Object obj = parser.parse(new FileReader("src/main/res/Progression.json"));
            JSONObject object = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) object.get("progress");

            for (int i = 0; i < jsonArray.size(); i++)
            {
                progression[i] = Boolean.parseBoolean(jsonArray.get(i).toString());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return progression;

    }

    private void playSound(String sound)
    {
        String pathname = "";

        switch (sound.toLowerCase())
        {
            case "success":
                pathname = "src/main/res/sounds/completion-of-a-level.wav";
                break;
            case "error":
                pathname = "src/main/res/sounds/error-alert.wav";
                break;
        }

        Media media = new Media(new File(pathname).toURI().toString());
        alertPlayer = new MediaPlayer(media);
        alertPlayer.play();
        alertPlayer.setVolume((double) Main.volumeSounds / 100);

    }

    private void blurElements()
    {
        BoxBlur bb = new BoxBlur();
        bb.setWidth(20);
        bb.setHeight(20);
        bb.setIterations(3);

        for (Entity entita : entities)
        {
            entita.getImg().setEffect(bb);
            if (entita instanceof Output)
            {
                    entita.getImg().setCursor(Cursor.DEFAULT);
            }
            else if (entita instanceof Input)
            {
                entita.getImg().setCursor(Cursor.DEFAULT);
            }
        }
        for (Line line : cables)
        {
            line.setEffect(bb);
        }
    }

    private void unblurElements()
    {
        for (Entity entita : entities)
        {
            entita.getImg().setEffect(null);
            if (entita instanceof Output)
            {
                if (!((Output) entita).isLocked())
                {
                    entita.getImg().setCursor(Cursor.HAND);
                }
                else
                    entita.getImg().setCursor(Cursor.CLOSED_HAND);
            }
            else if (entita instanceof Input)
            {
                if (!((Input) entita).isLocked())
                {
                    entita.getImg().setCursor(Cursor.HAND);
                }
                else
                    entita.getImg().setCursor(Cursor.CLOSED_HAND);
            }
        }
        for (Line line : cables)
        {
            line.setEffect(null);
        }
    }

    private void hideControls()
    {
        checkButton.setVisible(false);
        pauseBtn.setVisible(false);
    }

    private void showControls()
    {
        checkButton.setVisible(true);
        pauseBtn.setVisible(true);
    }

    private void createVbox()
    {
        vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setStyle("-fx-background-color: rgba(0,0,0,0.35);");
        vbox.setPrefWidth(315);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        AnchorPane.setLeftAnchor(vbox, ((playArea.getWidth() * 0.5) - 157.5));
        AnchorPane.setTopAnchor(vbox, 50.0);
    }

    private void showPassWindow()
    {
        blurElements();
        createVbox();
        long finish_time = System.nanoTime();
        double time_elapsed_sec = ((double) (finish_time - start_time) / 10000000) - time_inPause_sec;
        time_elapsed_sec = (double) Math.round(time_elapsed_sec) / 100;
        ImageView stars = null;

        try
        {
            if ((multiplier_sec * difficulty) - time_elapsed_sec >= 0)
            {
                if (attempts == 1)
                {
                    stars = new ImageView(new Image(new FileInputStream("src/main/res/images/3OutOf3Stars.png")));
                }
                else if (attempts < 4)
                    stars = new ImageView(new Image(new FileInputStream("src/main/res/images/2OutOf3Stars.png")));
                else
                    stars = new ImageView(new Image(new FileInputStream("src/main/res/images/1OutOf3Stars.png")));

            }
            else if ((multiplier_sec * difficulty) - time_elapsed_sec >= -(multiplier_sec * difficulty))
            {
                if (attempts < 4)
                    stars = new ImageView(new Image(new FileInputStream("src/main/res/images/2OutOf3Stars.png")));
                else
                    stars = new ImageView(new Image(new FileInputStream("src/main/res/images/1OutOf3Stars.png")));

            }
            else
                stars = new ImageView(new Image(new FileInputStream("src/main/res/images/1OutOf3Stars.png")));

            stars.setPreserveRatio(true);
            stars.setFitWidth(200);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        int time_elapsed_min = (int) time_elapsed_sec / 60;
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
        HBox btns_hbox = new HBox();
        Button reset_btn = new Button("Reštart");
        Button continue_btn = new Button("Pokračovať");
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
            if (id + 1 <= numberOfLevels)
            {
                setId(id += 1);
                renderElements();
            }
            else
            {
                switchSceneToSelectLevel();
            }

        });

        vbox.getChildren().add(pass_text);
        vbox.getChildren().add(attempts_text);
        vbox.getChildren().add(time_text);
        vbox.getChildren().add(stars);
        vbox.getChildren().add(btns_hbox);
        vbox.getChildren().add(menu_text);
        playArea.getChildren().add(vbox);
    }

    public void showPauseMenu()
    {
        long start_time_pause = System.nanoTime();

        isInPause = true;
        createVbox();
        Text pause_text = new Text("Pauza");
        pause_text.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");

        Button continue_btn = new Button("Pokračovať");
        continue_btn.setPrefWidth(200);
        continue_btn.getStyleClass().add("passBtns");
        continue_btn.setCursor(Cursor.HAND);
        continue_btn.setOnMouseClicked(mouseEvent ->
        {
            long finish_time_pause = System.nanoTime();
            time_inPause_sec += ((double) (finish_time_pause - start_time_pause) / 10000000);
            showControls();
            unblurElements();
            isInPause = false;
            vbox.setVisible(false);

        });

        Button menu_btn = new Button("Menu");
        menu_btn.setPrefWidth(200);
        menu_btn.getStyleClass().add("passBtns");
        menu_btn.setCursor(Cursor.HAND);
        menu_btn.setOnMouseClicked(mouseEvent ->
        {
            switchSceneToMenu();
        });

        Button reset_btn = new Button("Reštart");
        reset_btn.setPrefWidth(200);
        reset_btn.getStyleClass().add("passBtns");
        reset_btn.setCursor(Cursor.HAND);
        reset_btn.setOnMouseClicked(mouseEvent ->
        {
            renderElements();
        });
        vbox.setPadding(new Insets(0, 0, 20, 0));
        vbox.getChildren().add(pause_text);
        vbox.getChildren().add(continue_btn);
        vbox.getChildren().add(reset_btn);
        vbox.getChildren().add(menu_btn);
        blurElements();
        hideControls();
        playArea.getChildren().add(vbox);

    }

}
