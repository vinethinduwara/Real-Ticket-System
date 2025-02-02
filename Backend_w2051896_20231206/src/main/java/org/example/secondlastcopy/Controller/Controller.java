package org.example.secondlastcopy.Controller;


import org.example.secondlastcopy.Service.ConfigurationService;
import org.example.secondlastcopy.util.JasonFileUtility;
import org.example.secondlastcopy.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/config")
public class Controller {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("/save")
    public String saveConfiguration(@RequestBody Configuration config) {
        configurationService.saveConfiguration(config);
        return "Configuration saved successfully.";
    }

    @PostMapping("/start")
    public String startSimulation() {
        return configurationService.startSimulation();
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        return configurationService.stopSimulation();

    }
    @GetMapping("/load")
    public Configuration loadConfiguration() {
        return JasonFileUtility.loadFromFile("config.json", Configuration.class);
    }



}