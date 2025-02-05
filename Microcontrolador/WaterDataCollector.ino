#include <WiFiManager.h> // https://github.com/tzapu/WiFiManager
#include <HTTPClient.h>
#include <esp_system.h> // Required for ESP-specific functions

// Pin and variable definitions for pH sensor
const int analogpHReader = 36;
float pHConverter = 0.0;
int pHSamples[100];

// Variables for TDS and temperature sampling
float tempSamples[100];
int tdsSamples[100];

// Variable to store the device ID
String deviceId;

void bubbleSortInt(int arr[], int size) {
  for (int i = 0; i < size - 1; i++) {
    for (int j = 0; j < size - i - 1; j++) {
      if (arr[j] > arr[j + 1]) {
        int temp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = temp;
      }
    }
  }
}

void bubbleSortFloat(float arr[], int size) {
  for (int i = 0; i < size - 1; i++) {
    for (int j = 0; j < size - i - 1; j++) {
      if (arr[j] > arr[j + 1]) {
        float temp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = temp;
      }
    }
  }
}

void setup() {
  Serial.begin(115200);
  Serial1.begin(300, SERIAL_8N1, 16, 17); // RX = GPIO16, TX = GPIO17

  // Get the ESP32 chip ID as a 64-bit number
  uint64_t chipId = ESP.getEfuseMac();
  
  // Convert the chip ID to a hexadecimal string
  deviceId = String((uint32_t)(chipId >> 32), HEX) + String((uint32_t)chipId, HEX);

  // Print the device ID for debugging
  Serial.print("Device ID: ");
  Serial.println(deviceId);

  WiFiManager wm;
  bool res = wm.autoConnect("luiz_esp32", "waterdata");

  if (!res) {
    Serial.println("Failed to connect to WiFi");
    ESP.restart();
  } else {
    Serial.println("WiFi connected!");
  }
}

void loop() {
  int delayTime = 30000;
  delay(delayTime);
  float temp = 0.0;
  int tds = 0;

  // Collect 100 samples for Temp and TDS
  for (int i = 0; i < 100; i++) {
    if (Serial1.available() > 0) {
      String msg = Serial1.readStringUntil('\n');
      int colonIndex = msg.indexOf(':');
      if (colonIndex != -1) {
        tempSamples[i] = msg.substring(0, colonIndex).toFloat();
        tdsSamples[i] = msg.substring(colonIndex + 1).toInt();
      }
    }
    delay(10);
  }

  // Collect 100 samples for pH
  for (int i = 0; i < 100; i++) {
    pHSamples[i] = analogRead(analogpHReader);
    delay(10);
  }

  // Sort samples
  bubbleSortFloat(tempSamples, 100);
  bubbleSortInt(tdsSamples, 100);
  bubbleSortInt(pHSamples, 100);

  // Calculate averages (middle 50 samples)
  float avgTemp = 0.0;
  int avgTDS = 0;
  int avgPH = 0;

  for (int i = 26; i < 76; i++) {
    avgTemp += tempSamples[i];
    avgTDS += tdsSamples[i];
    avgPH += pHSamples[i];
  }
  avgTemp /= 50;
  avgTDS /= 50;
  avgPH /= 50;

  // Convert pH average to voltage and then pH value
  float voltage = avgPH * 5.0 / 1024;
  pHConverter = -0.25 * voltage + 11.34;

  // Print values to Serial Monitor
  Serial.print("Average Temp: ");
  Serial.println(avgTemp);
  Serial.print("Average TDS: ");
  Serial.println(avgTDS);
  Serial.print("Average pH Value: ");
  Serial.println(pHConverter);

  // Send data to server if Wi-Fi is connected
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;

    String serverName = "https://waterdatacollectorbackend.onrender.com/postDeviceData?";
    serverName += "deviceId=" + deviceId;
    serverName += "&ph=" + String(pHConverter, 2); // Format pH with 2 decimal points
    serverName += "&temperature=" + String(avgTemp, 2); // Format Temp with 2 decimal points
    serverName += "&tds=" + String(avgTDS); // TDS as integer

    http.begin(serverName);
    int httpResponseCode = http.POST("");
    if (httpResponseCode > 0) {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
    } else {
      Serial.print("Error in HTTP request: ");
      Serial.println(httpResponseCode);
    }
    http.end();
  }

}
