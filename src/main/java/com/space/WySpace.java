package com.space;

import com.space.dto.Result;
import com.space.dto.Satellite;
import com.space.utils.Constants;
import com.space.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class WySpace {

    private static final Logger LOGGER = LoggerFactory.getLogger(WySpace.class);

    public static void main(String[] args) {
        // input parameter is mandatory
        if (args == null || StringUtils.isEmpty(args[0])) {
            LOGGER.error("Please pass ground station bandwidth");
            return;
        }

        // initialize bandwidth and timeframe list of size 1440
        int groundStationBandwidth = Integer.parseInt(args[0]);
        List<Integer> timeframe = new ArrayList<>(Collections.nCopies(Constants.MINUTES_IN_DAY, 0));

        try {
            // read data from pass-schedule.txt
            HashMap<String, Satellite> satelliteData = Utils.readData(Constants.DATA_PATH);

            // fill the time frame of one day in minutes in a list - 1440 min 0 list size
            satelliteData.forEach((satelliteName, satellite) -> {
                satellite.getPasses().forEach(currPass -> {
                    DownLinkProgram.fillTimeframe(timeframe, currPass.getPassStart(), currPass.getPassEnd(), satellite.getBandwidth());
                });
            });

            // find max bandwidth in 30-min timeframe
            Result result = DownLinkProgram.findDownLinkMaxRate(timeframe);

            // print results in a file /data/results.txt
            Utils.writeResults(
                    result.getMaxDownlink(),
                    Utils.getTime(result.getPass().getPassStart()),
                    Utils.getTime(result.getPass().getPassEnd()),
                    groundStationBandwidth
            );
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
