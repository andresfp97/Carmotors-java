package com.carmotors.view;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            return dateFormatter.format(value);
        }
        return "";
    }
}
