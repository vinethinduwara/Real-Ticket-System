package org.example.secondlastcopy.Service;


import org.example.secondlastcopy.util.JasonFileUtility;
import org.example.secondlastcopy.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final String configFilePath = "config.json";

    @Autowired
    private SimulationRunner simulationRunner;


    public void saveConfiguration(Configuration config) {
        JasonFileUtility.saveToFile(configFilePath, config);
    }

    public String startSimulation() {
        if (simulationRunner.isRunning()) {
            return "Simulation is already running.";
        }

        Configuration config = JasonFileUtility.loadFromFile(configFilePath, Configuration.class);
        if (config == null) {
            return "Failed to load configuration. Please save a configuration first.";
        }

        simulationRunner.runSimulation(config);
        return "Simulation started. Check the logs for details.";
    }

    public String stopSimulation() {
        if (!simulationRunner.isRunning()) {
            return "Simulation is not running.";
        }

        simulationRunner.stopSimulation();
        return "Simulation stopped successfully.";
    }

    public boolean isSimulationRunning() {
        return simulationRunner.isRunning();
    }
}
