package org.example;

import java.awt.event.*;
import net.miginfocom.swing.MigLayout;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.awt.*;
import javax.swing.*;

/*
 * Created by JFormDesigner on Tue May 09 10:21:48 IST 2023
 */

/**
 * @author M
 */
public class Showcase extends JFrame {
    public MyConfigFile configFile;
    public MqttHandler mqtt;
    private final Image imageIcon;


    public Showcase() {
        configFile = new MyConfigFile();
        imageIcon = new IconMaster("/icons/M.png").getImage();

        //Init all Jframe components
        initComponents();

        //Disable on off buttons
        enableOrDisableButtonOnOff(false);

        //Read configuration file
        readConfig();

        //Main part of application
        MainFrmae();

        //Add tray icon and tray actions
        handleTrayLogic();

        //Connect to mqtt
        connect();

        //Hide to tray
        dispose();
    }

    public void MainFrmae(){
        setTitle("MQTT-PC-Light-Handler");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setIconImage(imageIcon);
        handleExitBehaviour();
    }

    public void handleExitBehaviour(){
        Thread a = new Thread(() -> {
            mqtt.lightOff();
        });
        Runtime.getRuntime().addShutdownHook(a);

    }

    public void handleTrayLogic(){
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        if(SystemTray.isSupported()) {
            TrayGui trayGui = new TrayGui(this, imageIcon);
        }
    }

    public void readConfig() {
        textField_ip.setText(configFile.ip());
        textField_login.setText(configFile.login());
        textField_password.setText(configFile.password());
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        System.exit(0);
    }

    private void btn_hideMouseClicked(MouseEvent e) {
        System.out.println("Hide btn clicked");
        dispose();
    }

    private void btn_onMouseClicked(MouseEvent e) {
        System.out.println("Light on btn clicked");
        mqtt.lightOn();
    }

    private void lightOnWithDelay(int delay){
        final Timer timer = new Timer(delay, null);
        timer.addActionListener((e) -> {
            mqtt.lightOn();
            timer.stop();
        });
        timer.start();
    }

    private void btn_offMouseClicked(MouseEvent e) {
        System.out.println("Light off btn clicked");
        mqtt.lightOff();
    }

    private void btn_ConnectMouseClicked(MouseEvent e) {
        System.out.println("Trying to connect:   ");
        connect();
    }

    private void connect(){
        try {
            mqtt = new MqttHandler(configFile.ip(), configFile.login(), configFile.password());
            textField_status.setText("Connected: " + configFile.ip());
            enableOrDisableButtonOnOff(true);
            lightOnWithDelay(1000);
        } catch (MqttException ex) {
            textField_status.setText("Not Connected: " + ex.toString());
            enableOrDisableButtonOnOff(false);
            throw new RuntimeException(ex);
        }
    }

    private void btn_SaveMouseClicked(MouseEvent e) {
        System.out.println("Save btn clicked");
        String ip = textField_ip.getText();
        String login = textField_login.getText();
        String password = String.valueOf(textField_password.getPassword());

        configFile.setAll(ip,login,password);
        readConfig();
    }

    private void enableOrDisableButtonOnOff(Boolean value){
       btn_on.setEnabled(value);
       btn_off.setEnabled(value);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        buttonBar = new JPanel();
        btn_hide = new JButton();
        cancelButton = new JButton();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField_ip = new JTextField();
        label2 = new JLabel();
        textField_login = new JTextField();
        label3 = new JLabel();
        textField_password = new JPasswordField();
        textField_status = new JTextField();
        btn_Connect = new JButton();
        btn_Save = new JButton();
        label_light = new JLabel();
        btn_on = new JButton();
        btn_off = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- btn_hide ----
                btn_hide.setText("Hide");
                btn_hide.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btn_hideMouseClicked(e);
                    }
                });
                buttonBar.add(btn_hide, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("EXIT");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            //======== panel1 ========
            {

                //---- label1 ----
                label1.setText("MQTT-Server-IP");
                label1.setHorizontalAlignment(SwingConstants.RIGHT);

                //---- textField_ip ----
                textField_ip.setHorizontalAlignment(SwingConstants.CENTER);
                textField_ip.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                //---- label2 ----
                label2.setText("Login");
                label2.setHorizontalAlignment(SwingConstants.RIGHT);

                //---- textField_login ----
                textField_login.setHorizontalAlignment(SwingConstants.CENTER);

                //---- label3 ----
                label3.setText("Password");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);

                //---- textField_password ----
                textField_password.setHorizontalAlignment(SwingConstants.CENTER);

                //---- textField_status ----
                textField_status.setText("Status");
                textField_status.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                textField_status.setHorizontalAlignment(SwingConstants.CENTER);
                textField_status.setEditable(false);

                //---- btn_Connect ----
                btn_Connect.setText("Connect");
                btn_Connect.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btn_ConnectMouseClicked(e);
                    }
                });

                //---- btn_Save ----
                btn_Save.setText("Save configuration");
                btn_Save.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btn_SaveMouseClicked(e);
                    }
                });

                //---- label_light ----
                label_light.setIcon(new ImageIcon(getClass().getResource("/icons/light_off.png")));
                label_light.setHorizontalAlignment(SwingConstants.CENTER);

                //---- btn_on ----
                btn_on.setText("ON");
                btn_on.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btn_onMouseClicked(e);
                    }
                });

                //---- btn_off ----
                btn_off.setText("OFF");
                btn_off.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btn_offMouseClicked(e);
                    }
                });

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap(8, Short.MAX_VALUE)
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(textField_status)
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                            .addGap(0, 2, Short.MAX_VALUE)
                                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(textField_password, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(textField_login, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(panel1Layout.createParallelGroup()
                                                .addComponent(btn_Connect, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_Save, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(label1)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textField_ip, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE))))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(label_light, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(btn_on, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_off, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 223, Short.MAX_VALUE)))
                            .addContainerGap())
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField_ip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField_login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_Connect))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_Save))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textField_status, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(btn_on)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_off))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label_light, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(11, Short.MAX_VALUE))
                );
            }
            dialogPane.add(panel1, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel buttonBar;
    private JButton btn_hide;
    private JButton cancelButton;
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField_ip;
    private JLabel label2;
    private JTextField textField_login;
    private JLabel label3;
    private JPasswordField textField_password;
    private JTextField textField_status;
    private JButton btn_Connect;
    private JButton btn_Save;
    private JLabel label_light;
    private JButton btn_on;
    private JButton btn_off;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
