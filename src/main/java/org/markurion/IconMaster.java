package org.markurion;

import java.awt.*;
import java.net.URL;

public class IconMaster {
    public Image getImage() {
        return image;
    }

    private Image image;

    /**
     * Generates Image from provided URL
     * @param iconLocation
     */
    public IconMaster(String iconLocation){
        URL iconURL = getClass().getResource(iconLocation);
        image = Toolkit.getDefaultToolkit().getImage(iconURL);
    }

    public static void main(String[] args){
        IconMaster icon = new IconMaster("/icons/M.png");
    }

}
