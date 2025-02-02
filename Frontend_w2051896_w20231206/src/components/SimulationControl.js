import React, { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import axios from "axios";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";

// Register chart.js components
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const SimulationControl = () => {
  const [logs, setLogs] = useState([]);
  const [isRunning, setIsRunning] = useState(false);
  const [chartData, setChartData] = useState({
    labels: [], // Timestamps
    datasets: [
      {
        label: "Tickets Sold",
        data: [], // Number of tickets
        borderColor: "rgba(75,192,192,1)",
        backgroundColor: "rgba(75,192,192,0.2)",
        tension: 0.4,
      },
    ],
  });

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws/simulation");
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("WebSocket connected successfully.");
        client.subscribe("/topic/simulation", (message) => {
          const log = message.body;
          setLogs((prevLogs) => [...prevLogs, log]);

          // Parse the message for chart updates
          if (log.includes("retrieved Ticket ID:")) {
            const timestamp = new Date().toLocaleTimeString();
            setChartData((prevData) => ({
              ...prevData,
              labels: [...prevData.labels, timestamp],
              datasets: [
                {
                  ...prevData.datasets[0],
                  data: [...prevData.datasets[0].data, prevData.datasets[0].data.length + 1],
                },
              ],
            }));
          }
        });
      },
      onStompError: (frame) => {
        console.error("WebSocket error: ", frame.headers["message"]);
      },
      onWebSocketError: (err) => {
        console.error("WebSocket connection error:", err);
      },
      onDisconnect: () => console.log("Disconnected from WebSocket."),
    });

    client.activate();

    return () => {
      if (client.active) {
        client.deactivate();
      }
    };
  }, []);

  const handleStartSimulation = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/config/start");
      console.log(response.data);
      setIsRunning(true);
    } catch (err) {
      console.error("Start Simulation failed:", err.message);
    }
  };

  const handleStopSimulation = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/config/stop");
      console.log(response.data);
      setIsRunning(false);
    } catch (err) {
      console.error("Stop Simulation failed:", err.message);
    }
  };

  const clearLogs = () => {
    setLogs([]);
    setChartData({
      labels: [],
      datasets: [
        {
          label: "Tickets Sold",
          data: [],
          borderColor: "rgba(75,192,192,1)",
          backgroundColor: "rgba(75,192,192,0.2)",
          tension: 0.4,
        },
      ],
    });
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.header}>Simulation Control</h1>
      <div style={styles.buttonContainer}>
        <button
          onClick={handleStartSimulation}
          disabled={isRunning}
          style={{ ...styles.button, backgroundColor: isRunning ? "#ccc" : "#007bff" }}
        >
          Start Simulation
        </button>
        <button
          onClick={handleStopSimulation}
          disabled={!isRunning}
          style={{ ...styles.button, backgroundColor: !isRunning ? "#ccc" : "#dc3545" }}
        >
          Stop Simulation
        </button>
        <button onClick={clearLogs} style={{ ...styles.button, backgroundColor: "#6c757d" }}>
          Clear Logs
        </button>
      </div>
      <h2 style={styles.logHeader}>Simulation Logs</h2>
      <div style={styles.logContainer}>
        {logs.length > 0 ? (
          logs.map((log, idx) => (
            <div key={idx} style={styles.logEntry}>
              {log}
            </div>
          ))
        ) : (
          <p style={styles.noLogs}>No logs available</p>
        )}
      </div>
      <h2 style={styles.chartHeader}>Real-Time Analytics</h2>
      <div style={styles.chartContainer}>
        <Line
          data={chartData}
          options={{
            responsive: true,
            plugins: {
              legend: {
                position: "top",
              },
              title: {
                display: true,
                text: "Ticket Sales Over Time",
              },
            },
          }}
        />
      </div>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: "700px",
    margin: "20px auto",
    padding: "20px",
    border: "1px solid #ccc",
    borderRadius: "8px",
    backgroundColor: "#f9f9f9",
    textAlign: "center",
  },
  header: {
    fontSize: "24px",
    marginBottom: "20px",
  },
  buttonContainer: {
    display: "flex",
    justifyContent: "space-around",
    marginBottom: "20px",
  },
  button: {
    padding: "10px 20px",
    fontSize: "16px",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  logHeader: {
    fontSize: "20px",
    marginBottom: "10px",
  },
  logContainer: {
    maxHeight: "300px",
    overflowY: "auto",
    textAlign: "left",
    padding: "10px",
    border: "1px solid #ccc",
    borderRadius: "8px",
    backgroundColor: "#fff",
  },
  logEntry: {
    padding: "5px",
    borderBottom: "1px solid #eee",
    wordWrap: "break-word",
  },
  noLogs: {
    textAlign: "center",
    color: "#777",
    fontSize: "16px",
  },
  chartHeader: {
    fontSize: "20px",
    marginTop: "20px",
  },
  chartContainer: {
    padding: "10px",
    border: "1px solid #ccc",
    borderRadius: "8px",
    backgroundColor: "#fff",
  },
};

export default SimulationControl;













