package utils;

import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class TextFieldChecker {

    private static final int DEFAULT_VALUE = 0;

    public static void checkNaturalIntegerOutput(@NotNull TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            textField.setText(String.valueOf(DEFAULT_VALUE));
        } else {
            try {
                int value = Integer.parseInt(text);

                if (value > 0) textField.setText(String.valueOf(value));
                else textField.setText(String.valueOf(DEFAULT_VALUE));
            } catch (NumberFormatException e) {
                textField.setText(String.valueOf(DEFAULT_VALUE));
            }
        }
    }

    public static void checkLongOutput(@NotNull TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            textField.setText(String.valueOf(DEFAULT_VALUE));
        } else {
            try {
                long value = Long.parseLong(text);
                textField.setText(String.valueOf(value));
            } catch (NumberFormatException e) {
                textField.setText(String.valueOf(DEFAULT_VALUE));
            }
        }
    }

    public static void checkDoubleOutput(@NotNull TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            textField.setText(String.valueOf(DEFAULT_VALUE));
        } else {
            try {
                double value = Double.parseDouble(text);
                textField.setText(String.valueOf(value));
            } catch (NumberFormatException e) {
                textField.setText(String.valueOf(DEFAULT_VALUE));
            }
        }
    }

}