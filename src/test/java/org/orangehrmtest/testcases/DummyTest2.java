package org.orangehrmtest.testcases;

import org.orangehrmtest.base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTest2 extends BaseClass {

    @Test
    public void dummyTest2() {
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "OrangeHRM", "Test Failed!");
    }
}
