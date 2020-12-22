package com.arczipt.ewolucja.gui.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsItem {
    private Label label;
    private TextField textField;

    public SettingsItem(String name, String value){
        label = new Label();
        label.setText(name);

        textField = new TextField();
        textField.setText(value);
    }

    public Label getLabel() {
        return label;
    }

    public TextField getTextField() {
        return textField;
    }
}
