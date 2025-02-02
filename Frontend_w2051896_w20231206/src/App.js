import React, { useState } from "react";
import ConfigurationForm from "./components/ConfigurationForm.js";
import SimulationControl from "./components/SimulationControl.js";
import LogDisplay from "./components/LogDisplay.js";

const App = () => {
  const [logs, setLogs] = useState([]);

  const handleConfigSave = (config) => {
    console.log("Configuration Saved:", config);
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.header}>Simulation Dashboard</h1>
      <ConfigurationForm onConfigSave={handleConfigSave} />
      <SimulationControl />
      <LogDisplay logs={logs} />
    </div>
  );
};

const styles = {
  container: {
    padding: "20px",
    fontFamily: "'Arial', sans-serif",
  },
  header: {
    textAlign: "center",
    marginBottom: "20px",
    color: "#333",
  },
};

export default App;

