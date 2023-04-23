package edu.nazaaaar.perlinnoise;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import utils.Func;
import utils.TextFieldChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SettingsWindow {

    final Settings settings = new Settings();

    @FXML
    TextField seed;
    @FXML
    TextField numOctaves;
    @FXML
    TextField startAmplitude;
    @FXML
    TextField startFrequency;
    @FXML
    TextField amplitudeMultiplier;
    @FXML
    TextField frequencyMultiplier;

    @FXML
    TextField resolution;
    @FXML
    TextField minValue;
    @FXML
    TextField maxValue;
    @FXML
    TextField size;
    @FXML
    TextField waterLevel;
    @FXML
    CheckBox useWater;

    @FXML
    TextField Height1;
    @FXML
    TextField Height2;
    @FXML
    TextField Height3;
    @FXML
    TextField Height4;
    @FXML
    ColorPicker Color1;
    @FXML
    ColorPicker Color2;
    @FXML
    ColorPicker Color3;
    @FXML
    ColorPicker Color4;

    Map<TextField,ColorPicker> colorMap = new HashMap<>();

    @FXML
    ColorPicker defaultColor;

    @FXML
    void initialize(){
        colorMap.put(Height1,Color1);
        colorMap.put(Height2,Color2);
        colorMap.put(Height3,Color3);
        colorMap.put(Height4,Color4);

        addExitFocusEvent(seed, TextFieldChecker::checkLongOutput);
        addExitFocusEvent(numOctaves, TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(startAmplitude,TextFieldChecker::checkDoubleOutput);
        addExitFocusEvent(startFrequency,TextFieldChecker::checkDoubleOutput);
        addExitFocusEvent(amplitudeMultiplier,TextFieldChecker::checkDoubleOutput);
        addExitFocusEvent(frequencyMultiplier,TextFieldChecker::checkDoubleOutput);

        addExitFocusEvent(resolution,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(minValue,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(maxValue,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(size,TextFieldChecker::checkNaturalIntegerOutput);
        addExitFocusEvent(waterLevel,TextFieldChecker::checkNaturalIntegerOutput);

        for (TextField height :
                colorMap.keySet()) {
            addExitFocusEvent(height, TextFieldChecker::checkNaturalIntegerOutput);
        }
    }

    public void onGenerateClicked(){
        settings.create(new Settings.SettingsConfig(
                new Settings.PerlinFBM_Config(
                        getIntAnswer(numOctaves),
                        getDoubleAnswer(startAmplitude),
                        getDoubleAnswer(startFrequency),
                        getDoubleAnswer(amplitudeMultiplier),
                        getDoubleAnswer(frequencyMultiplier),
                        getLongAnswer(seed)
                ),
                new Settings.HeightmapConfig(
                        getIntAnswer(minValue),
                        getIntAnswer(maxValue),
                        getIntAnswer(size)
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
