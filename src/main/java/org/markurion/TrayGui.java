package org.markurion;

import javax.swing.*;
import java.awt.*;

public class TrayGui {
    Showcase window;
    Image image;

    public TrayGui(Showcase window, Image image){
        this.window = window;
        this.image = image;
        this.window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setupTray();
    }

    public void setupTray(){
        SystemTray systemTray = SystemTray.getSystemTray();

        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu( mainPopUpMenu() );

        try{
            systemTray.add(trayIcon);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private PopupMenu mainPopUpMenu(){
        PopupMenu popupMenu = new PopupMenu();
        MenuItem show = new MenuItem("Show");
        show.addActionListener(e -> window.setVisible(true));

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> System.exit(0) );

        Font trayFont = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
        popupMenu.setFont(trayFont);

        popupMenu.add(show);
        popupMenu.addSeparator();
        popupMenu.add( subActionnMenu() );

        popupMenu.add(exit);
        return popupMenu;
    }

    /**
     * Creates action menu with on off comands
     * @return Menu
     */
    private Menu subActionnMenu(){
        Menu command = new Menu("Send command");

        MenuItem turnOn = new MenuItem("ON");
        turnOn.addActionListener(e -> window.mqtt.lightOn());

        MenuItem turnOff = new MenuItem("OFF");
        turnOff.addActionListener(e -> window.mqtt.lightOff());

        command.add(turnOn);
        command.add(turnOff);
        return command;
    }

}