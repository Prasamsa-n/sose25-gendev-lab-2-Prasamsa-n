package gendev.lab2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.games.controller.jits.BasicJitController;

public class SpecSimulatorCmd {

    public static void main(String[] args) throws IOException {

        Map<String, String> inputs = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Instantiate the controller executor using the compiled controller in "out/jit"
        ControllerExecutor executor = new ControllerExecutor(new BasicJitController(), "out/jit", "Spec");

        boolean iniState = true;

        System.out.println("Greenhouse Controller Simulation Started...");
        System.out.println("Enter environment values (true/false). Type 'exit' to quit.");

        while (true) {
            inputs.clear();

            System.out.print("Is temperature high? (tempHigh): ");
            String temp = scanner.nextLine();
            if (temp.equalsIgnoreCase("exit")) break;
            inputs.put("tempHigh", temp);

            System.out.print("Is soil dry? (soilDry): ");
            String soil = scanner.nextLine();
            if (soil.equalsIgnoreCase("exit")) break;
            inputs.put("soilDry", soil);

            System.out.print("Is light low? (lightLow): ");
            String light = scanner.nextLine();
            if (light.equalsIgnoreCase("exit")) break;
            inputs.put("lightLow", light);

            // Send inputs to controller
            if (iniState) {
                executor.initState(inputs);
                iniState = false;
            } else {
                executor.updateState(inputs);
            }

            // Read and print outputs
            Map<String, String> outputs = executor.getCurrOutputs();
            System.out.println("---- System Actions ----");
            System.out.println("Fan On:         " + outputs.get("fanOn"));
            System.out.println("Water Pump On:  " + outputs.get("waterPumpOn"));
            System.out.println("Grow Light On:  " + outputs.get("growLightOn"));
            System.out.println("------------------------\n");
        }

        scanner.close();
        System.out.println("Simulation Ended.");
    }
}
