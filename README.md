# IoT Water Data Collector System

## Project Overview
This fullstack project utilizes an **ESP32 microcontroller** with three sensors (**pH, Temperature, and TDS**) to collect water quality data. The collected data is sent to a **Spring Boot backend**, stored in an **SQLite database**, and visualized in a **frontend application**. The frontend fetches and displays:

- The **most recent** sensor readings
- The **average** of the last hour's readings
- **Graphs** for each sensor's data trends

## Technologies Used
### Backend:
- **Spring Boot** (Java) - REST API
- **SQLite** - Database for storing sensor readings

### Microcontroller:
- **ESP32** - Collects sensor data and sends it to the API
- **C++** - Programmed to read and process sensor data
- **Wi-Fi Connection** - Sends data via HTTP POST requests

### Frontend:
- **JavaScript, HTML, CSS** - Web interface
- **Chart.js (or other library)** - Displays real-time graphs
- **Fetch API** - Retrieves data from backend API

## Features
- Real-time data collection from **pH, Temperature, and TDS sensors**
- Stores sensor data in an **SQLite database**
- REST API for **data retrieval and processing**
- Frontend displays **real-time updates & graphs**
- Uses **quicksort** to process and filter sensor data

## How It Works
1. **ESP32 collects sensor readings** and filters out extreme values using quicksort.
2. **Sends the processed data** via HTTP POST requests to the backend API.
3. **Spring Boot backend stores** the readings in an SQLite database.
4. **Frontend fetches and displays** data:
   - Most recent reading
   - Average of the last hour
   - Graphs for trends visualization
