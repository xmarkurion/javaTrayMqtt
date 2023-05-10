package org.markurion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

class ConfigFileException extends Exception{
    ConfigFileException(){super();}
    ConfigFileException(String s){super(s);}
}

public class ConfigFile {
    private String rootPath;
    private String defaultConfigPath;
    private Properties properties;
    private Boolean propertiesLoaded;

    /**
     * Constructor with default file
     */
    ConfigFile(){
      rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
     // rootPath = this.getClass().getResource("").getPath();
        System.out.println(rootPath);
       defaultConfigPath = rootPath + "data/config.properties";
      // defaultConfigPath = Main.class.getResourceAsStream("data/config.properties");
       loadProperties();
    }

    /**
     * Construction with file
     * @param configFilePath
     */
    ConfigFile(String configFilePath){
        rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        defaultConfigPath = rootPath + configFilePath;
        loadProperties();
    }

    /**
     * Print's where config file is located
     */
    public void printCurrentWorkingPropertyFileLocation(){
        System.out.println("Config file location: " + defaultConfigPath);
    }

    /**
     * Loads properties to the class variable.
     */
    public void loadProperties(){
        try(FileInputStream input = new FileInputStream(defaultConfigPath);){
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
            properties.store(new FileWriter(defaultConfigPath), null);
        }else{
            throw new ConfigFileException("Properties were not loaded.");
        }
    }

    public static void main(String[] args){
        ConfigFile configFile = new ConfigFile();
        try{
            String ip = configFile.getProperty("ic");
            System.out.println(ip);
        } catch (ConfigFileException e) {
            try{
                configFile.setProperty("ic", "123.123.2324.43");
                configFile.save();
            } catch (ConfigFileException ex) {
                System.out.println(ex.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
