package com.space.utils;

import com.space.dto.Pass;
import com.space.dto.Satellite;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    public static HashMap<String, Satellite> readData(String location) throws IOException {
        HashMap<String, Satellite> data = new HashMap<>();

        BufferedReader reader;
        File inputFile = new File(location);
        reader = new BufferedReader(new FileReader(inputFile));
        String fileLine = reader.readLine();
        while (fileLine != null) {
            String[] satelliteData = fileLine.split(",");
            String satelliteName = satelliteData[0];
            String satelliteBandwidth = satelliteData[1];
            int passStart = getTimeInMin(satelliteData[2]);
            int passEnd = getTimeInMin(satelliteData[3]);
            if (!data.containsKey(satelliteName)) {
                // check if satellite does not exit
                List<Pass> passes = new ArrayList<>();
                passes.add(new Pass(passStart, passEnd));
                data.put(satelliteName, new Satellite(satelliteName, Integer.parseInt(satelliteBandwidth), passes));
            } else {
                // if exists
                List<Pass> passes = data.get(satelliteName).getPasses();
                passes.add(new Pass(passStart, passEnd));
                data.get(satelliteName).setPasses(passes);
            }
            fileLine = reader.readLine();
        }
        reader.close();

        return data;
    }

    public static void writeResults(Integer maxDownLink, String startPass, String endPass, int groundStationBandwidth) throws IOException {
        Path filePath = Path.of(Constants.RESULT_FILE_PATH);
        String content  = "Max bandwidth: " + maxDownLink + " per " + Constants.PERIOD_IN_MIN + " min \n";
        content += "Start time (pass): " + startPass + "\n";
        content += "End time (pass): " + endPass + "\n";
        content += ("The ground station has the bandwidth to support this: " + ((groundStationBandwidth > maxDownLink) ? "Yes" : "No"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(content);
        }
    }

    public static String getTime(Integer time) {
        int hours = time / 60;
        int min = time % 60;
        return (hours + ":" + min);
    }

    private static Integer getTimeInMin(String time) {
        String[] hoursMin = time.split(":");
        return (Integer.parseInt(hoursMin[1]) + (60 * Integer.parseInt(hoursMin[0])));
    }

}
