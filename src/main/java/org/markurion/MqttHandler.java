package org.markurion;

import org.eclipse.paho.client.mqttv3.*;
import java.util.UUID;
import java.util.concurrent.Callable;

// Read more here
// https://www.baeldung.com/java-mqtt-client

public class MqttHandler {
    IMqttClient publisher;
    String ip,login,password;

    /**
     * MQTT class init with ip login and pass
     * @param ip
     * @param login
     * @param password
     * @throws MqttException
     */
    MqttHandler(String ip, String login, String password) throws MqttException {
        this.ip= ip;
        this.login = login;
        this.password = password;

        String publisherId = UUID.randomUUID().toString();
        publisher = new MqttClient("tcp://"+ip,publisherId,null);
        connect();
    }

    /**
     * Method that handles the connection.
     * @throws MqttException
     */
    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(login);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
    }

    public void lightOn(){
        LightSensor l = new LightSensor(publisher, "1");
        try {
            l.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void lightOff(){
        LightSensor l = new LightSensor(publisher, "0");
        try {
            l.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Temp test, so I can check if this class works.
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        MyConfigFile my = new MyConfigFile();
        my.printCurrentWorkingPropertyFileLocation();
        MqttHandler a = new MqttHandler(my.ip(), my.login(), my.password());
    }
}

/**
 * Callable class so we can call it when we need to send the topic to our MQTT server.
 */
class LightSensor implements Callable<Void> {

    private static final String TOPIC = "java/light/status";
    IMqttClient client;
    String lightValue;

    public LightSensor(IMqttClient client, String lightValue) {
        this.client = client;
        this.lightValue = lightValue;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
            return null;
        }
        MqttMessage msg = preparedMessage();
        msg.setQos(2);
        msg.setRetained(true);
        client.publish(TOPIC,msg);
        return null;
    }

    private MqttMessage preparedMessage() {
        byte[] payload = lightValue.getBytes();
        return new MqttMessage(payload);
    }
}
