package org.orangehrmtest.testcases;

import org.orangehrmtest.base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTest extends BaseClass {

    @Test
    public void dummyTest() {
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "OrangeHRM", "Test Failed!");
    }
}
