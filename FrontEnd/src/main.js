import axios from 'axios'

var ph = {};
var temperature = {};
var tds = {};

function getLastReadData () {
    axios.get("https://waterdatacollectorbackend.onrender.com/getLastReadData", {
        params: {
            deviceId: "18fedc32a7b0"
        }
    })
        .then(function (response)  {
            const container = document.getElementById("last-data-container");

            const data = response.data;
            let { timestamp, ph, tds, temperature } = data; // Destructure data

            const localDate = new Date(timestamp).toLocaleString();
            ph = parseFloat(ph).toFixed(1);
            tds = parseFloat(tds).toFixed(1);
            temperature = parseFloat(temperature).toFixed(1);

            container.innerHTML =`
                <p>Date: ${localDate}</p>
                <p>pH: ${ph}</p>
                <p>TDS: ${tds}</p>
                <p>Temperature: ${temperature}°C</p>
            `;
        })
        .catch(function (error) {
            console.log(error);
        })
        .finally(function () {
        })
}

function getAverageValues() {
    axios.get("https://waterdatacollectorbackend.onrender.com/getAverageValues", {
        params: {
            deviceId: "18fedc32a7b0"
        }
    })
        .then(function (response)  {
            const container = document.getElementById("average-data-container");

            const data = response.data;
            let { timestamp, ph, tds, temperature } = data; // Destructure data

            const localDate = new Date(timestamp).toLocaleString();
            ph = parseFloat(ph).toFixed(1);
            tds = parseFloat(tds).toFixed(1);
            temperature = parseFloat(temperature).toFixed(1);

            container.innerHTML = `
                <p>Since: ${localDate}</p>
                <p>pH: ${ph}</p>
                <p>TDS: ${tds}</p>
                <p>Temperature: ${temperature}°C</p>
            `;
        })
        .catch(function (error) {
            console.log(error);
        })
        .finally(function () {
        })
}

function getLastHourReads() {
    axios.get("https://waterdatacollectorbackend.onrender.com/getLastHourReadsByDeviceId", {
        params: {
            deviceId: "18fedc32a7b0"
        }
    })
        .then(function (response)  {
            let timestamp = response.data.timestamp;
            let localTimes = timestamp.map(ts => {
                const date = new Date(ts);
                return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }); // Format only hours and minutes
            });

            ph = {
                x: localTimes,
                y: response.data.ph,
                type: 'scatter',
                name: 'pH Level'
            };

            temperature = {
                x: localTimes,
                y: response.data.temperature,
                type: 'scatter',
                name: 'Temperature (°C)',
            };

            tds = {
                x: localTimes,
                y: response.data.tds,
                type: 'scatter',
                name: 'TDS',
            };

            var data = [ph, temperature, tds];
            Plotly.newPlot(document.getElementById("graphic"), data);
        })
        .catch(function (error) {
            console.log(error);
        })
        .finally(function (response) {
        })
}

getLastReadData();
getAverageValues();
getLastHourReads();

setTimeout(function(){
    location.reload();
}, 1000 * 10);
