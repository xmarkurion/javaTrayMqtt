package org.markurion;

import java.io.*;
import java.util.Properties;

/**
 * ConfigFileException, you can get the val of exception from it.
 */
class ConfigFileException extends Exception{
    private String value;
    ConfigFileException(){super();}
    ConfigFileException(String s){super(s);}
    ConfigFileException(String message, String value){
        super(message);
        this.value = value;
    }
    public String getValueOfException(){return value;}
}

public class ConfigFile {
    private String configFilePath;
    private Properties properties;
    private Boolean propertiesLoaded;

    /**
     * Constructor with default file config.properties
     */
    ConfigFile(){
        // Set config file is data
        String appDir = System.getProperty("user.dir");
        configFilePath = appDir + "\\" + "config.properties";
        initialize();
    }

    /**
     * Change name of configFile to what you want
     * @param configFileName - yourName.properties
     */
    ConfigFile(String configFileName){
        String appDir = System.getProperty("user.dir");
        configFilePath = appDir + "\\" + configFileName;
        initialize();
    }

    /**
     * Provide full path to config file as well as name.
     * @param filePatch like "c:\\config"
     * @param configFileName like "setup.properties"
     */
    ConfigFile(String filePatch, String configFileName){
        configFilePath = filePatch + "\\" + configFileName;
        initialize();
    }

    /**
     * Main methods that run's after object is created
     */
    private void initialize(){
        // Create config file if one does not exist
        checkConfigFileSetup();

        // Initialize Properties
        loadProperties();
    }

    /**
     * If config file does not exist outside of jar create an empty config file.
     */
    private void checkConfigFileSetup() {
        if (!new File(configFilePath).isFile()) {
            try{
                FileOutputStream fo = new FileOutputStream(configFilePath);
            }catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Print's where config file is located
     */
    public void printCurrentWorkingPropertyFileLocation(){
        System.out.println("Config file location: " + configFilePath);
    }

    /**
     * Loads properties to the class variable.
     */
    public void loadProperties(){
        try(FileInputStream input = new FileInputStream(configFilePath)){
            properties = new Properties();
            properties.load(input);
            propertiesLoaded = true;

        }catch (FileNotFoundException e){
            System.out.println(e.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the selected property
     * @param propertyKey
     * @throws ConfigFileException
     */
    public String getProperty(String propertyKey) throws ConfigFileException {
        if( propertiesLoaded ) {
            String data = properties.getProperty(propertyKey);
            if( data == null ){
                throw new ConfigFileException(
                        "Property: \"" + propertyKey + "\" does not exist!",
                        propertyKey);
            }
            return data;
        }else{
            throw new ConfigFileException("Properties were not loaded.");
        }
    }

    /**
     * Set the property or adds new property.
     * @param propertyKey
     * @param propertyValue
     * @throws ConfigFileException
     */
    public void setProperty(String propertyKey, String propertyValue) throws ConfigFileException {
        if( propertiesLoaded ) {
            properties.setProperty(propertyKey, propertyValue);
        }else{
            throw new ConfigFileException("Properties were not loaded.");
        }
    }

    /**
     * Save's configuration file.
     * @throws IOException
     * @throws ConfigFileException
     */
    public void save() throws IOException, ConfigFileException {
        if(propertiesLoaded){
            FileOutputStream fo = new FileOutputStream(configFilePath);
            properties.store(fo, null);
        }else{
            throw new ConfigFileException("Properties were not loaded.");
        }
    }

    public static void main(String[] args){
        ConfigFile a = new ConfigFile();
        try {
            a.getProperty("qqq");
        } catch (ConfigFileException e) {
            System.out.println("I don't see " + e.getValueOfException());
        }
    }
}
