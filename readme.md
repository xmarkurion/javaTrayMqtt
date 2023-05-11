<p align="center"><img src="http://www.markurion.eu/wp-content/uploads/2017/01/unnamed.gif"></p>

# MQTT Java App for turning light on and off when needed
###### If you want to run it please look at the instruction in how to section.

## Why ? 
This app was done for purpose to switch pc light on or off.
App it's able to store configuration file where server config 
is kept, so you can modify and try on your own!

## How this app works.
1. Open it enter your mqtt server credentials
2. Press save ( app should create and modify config.properties files)
3. Press Connect If connected you can close and reopen app.

## After config file initialized
1. Since initial config app by default's tries to connect to the known MQTT server
2. App sends a topic ("java/light/status") with value of 1.
3. App minimize itself to try 
4. When pc is shutting down there is hook added to the app, so it will send another mqtt message this time with value of 0

## Gui preview
Main window:

![GUI](/readme/app.png)

Tray icon:

![tray](/readme/tray.png)

## Handle Node-red logic
Now you can handle the logic in Node-Red or somewhere where you like to.
![nodered](/readme/nodered.png)
