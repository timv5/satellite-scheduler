package com.space;

import com.space.dto.Pass;
import com.space.dto.Result;
import com.space.utils.Constants;

import java.util.List;

public class DownLinkProgram {

    public static void fillTimeframe(List<Integer> timeframe, int passStart, int passEnd, int bandwidth) {
        for (int i = passStart; i <= passEnd; i++) {
            timeframe.set(i, timeframe.get(i) + bandwidth);
        }
    }

    public static Result findDownLinkMaxRate(List<Integer> timeframe) {
        Result maxDownLink = new Result(0, new Pass());
        for (int i = 0; i < timeframe.size() - Constants.PERIOD_IN_MIN; i ++) {
            Result newDownLink = calculateCurrMaxRate(timeframe, i, i + Constants.PERIOD_IN_MIN);
            if (newDownLink.getMaxDownlink() > maxDownLink.getMaxDownlink()) {
                maxDownLink.setMaxDownlink(newDownLink.getMaxDownlink());
                maxDownLink.setPass(newDownLink.getPass());
            }
        }

        return maxDownLink;
    }

    private static Result calculateCurrMaxRate(List<Integer> timeframe, int from, int to) {
        int max = 0;
        for (int i = from; i <= to; i++) {
            max += timeframe.get(i);
        }

        return new Result(max, new Pass(from, to));
    }

}
