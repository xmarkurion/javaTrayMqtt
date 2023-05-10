package org.markurion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayGui {
    JFrame window;
    Image image;

    public TrayGui(JFrame window, Image image){
        this.window = window;
        this.image = image;
        this.window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setupTray();
    }

    public void setupTray(){
        SystemTray systemTray = SystemTray.getSystemTray();

        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(true);

        PopupMenu popupMenu = new PopupMenu();

        MenuItem show = new MenuItem("Show");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(true);
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        popupMenu.add(show);
        popupMenu.add(exit);

        trayIcon.setPopupMenu(popupMenu);
        try{
            systemTray.add(trayIcon);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}


//        String classpath = System.getProperty("java.class.path");
////        String[] classPathValues = classpath.split(File.pathSeparator);
////        System.out.println(Arrays.toString(classPathValues));
