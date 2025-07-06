package gendev.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.games.controller.jits.BasicJitController;

public class SpecSimulatorCmd {

	public static void main(String[] args) throws IOException {

		Map<String, String> inputs = new HashMap<>();

		// Instantiate a new controller executor
		ControllerExecutor executor = new ControllerExecutor(new BasicJitController(), "out/jit", "Spec");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean iniState = true;

		System.out.println("Starting Smart Room Lighting System Simulator.");
		System.out.println("Enter input values for each step: true/false");

		while (true) {
			inputs.clear();

			// Read inputs from the console
			System.out.print("isOccupied (true/false): ");
			String isOccupied = reader.readLine().trim();

			System.out.print("isDayTime (true/false): ");
			String isDayTime = reader.readLine().trim();

			System.out.print("isSunny (true/false): ");
			String isSunny = reader.readLine().trim();

			// Put inputs in the map as expected by the controller (strings "true"/"false")
			inputs.put("isOccupied", isOccupied);
			inputs.put("isDayTime", isDayTime);
			inputs.put("isSunny", isSunny);

			// Execute controller
			if (iniState) {
				executor.initState(inputs);
				iniState = false;
			} else {
				executor.updateState(inputs);
			}

			// Read outputs from the controller
			boolean mainLightOn = Boolean.parseBoolean(executor.getCurrOutputVal("mainLightOn"));
			boolean windowBlindsOpen = Boolean.parseBoolean(executor.getCurrOutputVal("windowBlindsOpen"));
			boolean nightLampOn = Boolean.parseBoolean(executor.getCurrOutputVal("nightLampOn"));

			// Print the system outputs
			System.out.println("Outputs:");
			System.out.println(" mainLightOn = " + mainLightOn);
			System.out.println(" windowBlindsOpen = " + windowBlindsOpen);
			System.out.println(" nightLampOn = " + nightLampOn);
			System.out.println("-----------------------------------");
		}
	}
}
