package com.space;

import com.space.dto.Pass;
import com.space.dto.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownLinkProgramTest {

    private DownLinkProgram downLinkProgram;

    @Before
    public void init() {
        downLinkProgram = new DownLinkProgram();
    }

    @Test
    public void testFillTimeframe() {
        // test data
        List<Integer> actual = new ArrayList<>(Collections.nCopies(5, 0));
        downLinkProgram.fillTimeframe(actual, 2, 4, 77);

        // expected
        List<Integer> expected = new ArrayList<>(Collections.nCopies(5, 0));
        expected.set(2, 77);
        expected.set(3, 77);
        expected.set(4, 77);

        // test
        Assert.assertEquals(expected.get(0), actual.get(0));
        Assert.assertEquals(expected.get(4), actual.get(4));
        Assert.assertNotEquals(expected.get(4), actual.get(0));
    }

    @Test
    public void testFindDownLinkMaxRate() {
        // test data
        List<Integer> data = new ArrayList<>(Collections.nCopies(5, 0));
        downLinkProgram.fillTimeframe(data, 2, 4, 5);

        // expected
        Result expected = new Result(15, new Pass(2, 4));

        // test
        Result actual = downLinkProgram.findDownLinkMaxRate(data, 3);
        Assert.assertEquals(expected.getMaxDownlink(), actual.getMaxDownlink());
    }
}