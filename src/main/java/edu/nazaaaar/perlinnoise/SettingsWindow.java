package edu.nazaaaar.perlinnoise;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utils.Func;
import utils.TextFieldChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SettingsWindow {

    final Settings settings = new Settings();

    @FXML
    void onHoverEnter(MouseEvent event) {
        if (event.getSource() instanceof TextField textField) {
            if (textField.getUserData() != null) {
                info.setText(textField.getUserData().toString());
            }
        } else if (event.getSource() instanceof CheckBox checkBox) {
            if (checkBox.getUserData() != null) {
                info.setText(checkBox.getUserData().toString());
            }
        }
    }

    @FXML
    void onHoverExit(MouseEvent event) {
        info.setText("Point your mouse to some property to see additional information");
    }

    @FXML
    Label info;
    @FXML
    TextField seed;
    @FXML
    TextField numOctaves;
    @FXML
    TextField startFrequency;
    @FXML
    TextField resolution;
    @FXML
    TextField minValue;
    @FXML
    TextField maxValue;
    @FXML
    TextField sizeX;
    @FXML
    TextField sizeY;
    @FXML
    TextField offsetX;
    @FXML
    TextField offsetY;
    @FXML
    TextField waterLevel;
    @FXML
    CheckBox useWater;
    @FXML
    CheckBox useEaseCurve;

    @FXML
    TextField Height1;
    @FXML
    TextField Height2;
    @FXML
    TextField Height3;
    @FXML
    TextField Height4;
    @FXML
    TextField Height5;
    @FXML
    ColorPicker Color1;
    @FXML
    ColorPicker Color2;
    @FXML
    ColorPicker Color3;
    @FXML
    ColorPicker Color4;
    @FXML
    ColorPicker Color5;
    Map<TextField,ColorPicker> colorMap = new HashMap<>();

    @FXML
    ColorPicker defaultColor;

    @FXML
    void initialize(){
        colorMap.put(Height1,Color1);
        colorMap.put(Height2,Color2);
        colorMap.put(Height3,Color3);
        colorMap.put(Height4,Color4);
        colorMap.put(Height5,Color5);

        makeExitFocusEvents();

        for (TextField height :
                colorMap.keySet()) {
            addExitFocusEvent(height, TextFieldChecker::checkNaturalIntegerOutput);
        }

        makeUserData();
    }

    private void makeExitFocusEvents() {
        addExitFocusEvent(seed, TextFieldChecker::checkLongOutput);
        addExitFocusEvent(numOctaves, TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(startFrequency,TextFieldChecker::checkDoubleOutput);

        addExitFocusEvent(resolution,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(minValue,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(maxValue,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(sizeX,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(sizeY,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(offsetX,TextFieldChecker::checkDoubleOutput);
        addExitFocusEvent(offsetY,TextFieldChecker::checkDoubleOutput);
        addExitFocusEvent(waterLevel,TextFieldChecker::checkNaturalIntegerOutput);
    }

    private void makeUserData() {
        seed.setUserData("Seed value for Perlin noise generation.");
        numOctaves.setUserData("Number of octaves for Perlin noise generation.");
        startFrequency.setUserData("Starting frequency for Perlin noise generation.");
        resolution.setUserData("Resolution of the single generated unit in the world .");
        minValue.setUserData("Minimum value for the generated Perlin noise world.");
        maxValue.setUserData("Maximum value for the generated Perlin noise world.");
        sizeX.setUserData("Width of the generated Perlin noise world.");
        sizeY.setUserData("Height of the generated Perlin noise world.");
        offsetX.setUserData("Offset in the X direction for the generated Perlin noise world.");
        offsetY.setUserData("Offset in the Y direction for the generated Perlin noise world.");
        waterLevel.setUserData("Height level at which water begins in the generated noise world.");
        useWater.setUserData("Whether or not the generated noise world should use water.");
        useEaseCurve.setUserData("Whether or not to use an ease curve in the Perlin noise generation.");
    }

    public void onGenerateClicked(){
        settings.create(new Settings.SettingsConfig(
                new Settings.PerlinFBM_Config(
                        getIntAnswer(numOctaves),
                        getDoubleAnswer(startFrequency),
                        getLongAnswer(seed),
                        useEaseCurve.isSelected()
                ),
                new Settings.HeightmapConfig(
                        getIntAnswer(minValue),
                        getIntAnswer(maxValue),
                        getIntAnswer(sizeX),
                        getIntAnswer(sizeY),
                        getDoubleAnswer(offsetX),
                        getDoubleAnswer(offsetY)
                ),
                new Settings.MapInterpreterConfig(
                        getIntAnswer(resolution),
                        getIntAnswer(waterLevel),
                        useWater.isSelected(),
                        getAnswerColorMap(colorMap),
                        defaultColor.getValue()
                )
            )
        );
    }
    @FXML
    public void onCameraInstructionRequest(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(
                "Use the following keys to control the camera:\n" +
                "\n" +
                "Arrow Up - rotate the camera up\n" +
                "Arrow Down - rotate the camera down\n" +
                "Arrow Left - rotate the camera left\n" +
                "Arrow Right - rotate the camera right\n" +
                "W - move the camera forward\n" +
                "S - move the camera backward\n" +
                "A - move the camera left\n" +
                "D - move the camera right\n" +
                "Q - move the camera down\n" +
                "E - move the camera up\n" +
                "\n" +
                "Press any of the above keys to move the camera.\n" +
                "\n" +
                "Enjoy the program!");
        alert.showAndWait();
    }

    private Map<Integer, Color> getAnswerColorMap(Map<TextField, ColorPicker> colorMap) {
        Map<Integer,Color> res = new TreeMap<>();
        for (Map.Entry<TextField,ColorPicker> entry :
                colorMap.entrySet()) {
            res.put(getIntAnswer(entry.getKey()),entry.getValue().getValue());
        }
        return res;
    }

    private int getIntAnswer(TextField textField) {
        return Integer.parseInt(textField.getText());
    }

    private double getDoubleAnswer(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    private long getLongAnswer(TextField textField) {
        return Long.parseLong(textField.getText());
    }

    void addExitFocusEvent(TextField textField, Func<TextField> func){
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                func.invoke(textField);
            }
        });
    }


}
