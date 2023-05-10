package org.example;

import java.io.IOException;

public class MyConfigFile extends ConfigFile{

    public static void main(String[] args){
        MyConfigFile m = new MyConfigFile();
//        m.printCurrentWorkingPropertyFileLocation();
    }

    MyConfigFile(){
        check();
    }

    public void check(){
        try{
            String ip = getProperty("ip");
        }catch (ConfigFileException e){
           UpdateConfig("ip","");
        }
        try{
            String user = getProperty("login");
        }catch (ConfigFileException e){
            UpdateConfig("login","");
        }
        try{
            String password = getProperty("password");
        }catch (ConfigFileException e){
            UpdateConfig("password","");
        }
    }

    private void UpdateConfig(String key, String value){
        try{
            setProperty(key, value);
            save();
        }catch (ConfigFileException e){
            System.out.println(e.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String ip(){
        try{
            return getProperty("ip");
        } catch (ConfigFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void setIP(String data){
        try{
            setProperty("ip",data);
            save();
        } catch (ConfigFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String login(){
        try{
            return getProperty("login");
        } catch (ConfigFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLogin(String data){
        try{
            setProperty("login",data);
            save();
        } catch (ConfigFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String password(){
        try{
            return getProperty("password");
        } catch (ConfigFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPassword(String data){
        try{
            setProperty("password",data);
            save();
        } catch (ConfigFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setAll(String ip, String login, String password){
        try {
            setProperty("ip",ip);
            setProperty("login",login);
            setProperty("password",password);
            save();
            return true;
        } catch (ConfigFileException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
