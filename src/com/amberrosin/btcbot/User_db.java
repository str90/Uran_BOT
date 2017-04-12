package com.amberrosin.btcbot;

import java.util.Properties;

import java.util.Scanner;

import java.io.*;

/**
 * Created by str on 30.03.2017.
 */
public class User_db {
    private boolean is_config_exists = false;
    private String confFileName = new String("config.properties");
    private String apiKey = new String();
    private String apiSecret = new String();

    public void writeConfigFile() throws IOException {
        try (FileOutputStream writeConfig = new FileOutputStream(confFileName)) {
            Scanner getUserCreds = new Scanner(System.in);
            System.out.println("Enter your API key and secret from BTC-e: ");
            Properties config = new Properties();
            config.setProperty("apiKey", getUserCreds.nextLine());
            config.setProperty("apiSecret", getUserCreds.nextLine());
            config.store(writeConfig, null);
        } catch (IOException w_conf_file_exc) { w_conf_file_exc.printStackTrace(); }
    }

    public void loadConfigFile() throws IOException {
        while(!is_config_exists) {
            try (FileInputStream readConfString = new FileInputStream(confFileName)) {
                Properties config = new Properties();
                config.load(readConfString);
                apiKey = config.getProperty("apiKey");
                apiSecret = config.getProperty("apiSecret");
                is_config_exists = true;
            } catch (FileNotFoundException f_not_found) {
                System.out.printf("File %s not found, creating new\n", confFileName);
               writeConfigFile();
                is_config_exists = false;
            }
            catch(IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }
    public String getApiKey() {
        return apiKey;
    }
    public String getApiSecret() {
        return apiSecret;
    }
}