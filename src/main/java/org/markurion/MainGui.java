package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui {
    JFrame window;
    Image imageIcon;
    int counter = 0;

    public void start(){
        window = new JFrame();
        window.setSize(400,400);

        imageIcon = new IconMaster("/icons/M.png").getImage();
        window.setIconImage(imageIcon);

        JButton button = new JButton("0");
        button.setFont(new Font("Arial",Font.PLAIN, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                button.setText(String.valueOf(counter));
            }
        });
        window.add(button);
        window.setVisible(true);

        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        if(SystemTray.isSupported()) {
            TrayGui trayGui = new TrayGui(window, imageIcon);
        }


    }


}
