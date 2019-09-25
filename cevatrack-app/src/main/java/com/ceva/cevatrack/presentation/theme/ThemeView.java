package com.ceva.cevatrack.presentation.theme;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Theme Class.
 * 
 * @author mohammad.weshah
 *
 */
@Named
@SessionScoped
public class ThemeView implements Serializable {

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Set Color.
     * 
     * @param color
     *            theme color
     */
    public void change(String color) {
        if (color.equals("green")) {
            this.color = null;
        } else {
            this.color = "-" + color;
        }

    }

}
