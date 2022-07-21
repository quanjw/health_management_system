package com.quanjiawei.utils;

import org.junit.Test;

public class DUtilTest {

    @Test
    public  void testDutil() {
        assert (DateUtils.getLastDayOfMonth("2022-2").equals("2022-02-28"));
        assert (DateUtils.getLastDayOfMonth("2022-7").equals("2022-07-31"));
        assert (DateUtils.getLastDayOfMonth("2022-8").equals("2022-08-31"));
        assert (DateUtils.getLastDayOfMonth("2022-9").equals("2022-09-30"));
    }
}
