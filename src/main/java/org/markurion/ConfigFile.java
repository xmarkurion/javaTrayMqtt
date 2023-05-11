package org.markurion;

import java.io.*;
import java.util.Properties;

class ConfigFileException extends Exception{
    ConfigFileException(){super();}
    ConfigFileException(String s){super(s);}
}

public class ConfigFile {
    private String configFilePath;
    private String defaultConfigPath;

    private InputStream inputStream;
    private Properties properties;
    private Boolean propertiesLoaded;

    /**
     * Constructor with default file
     */
    ConfigFile(){
        // Set config file is data
        String appDir = System.getProperty("user.dir");
        configFilePath = appDir + "\\" + "config.properties";

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
                throw new ConfigFileException("Property: \"" + propertyKey + "\" does not exist!");
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
}
