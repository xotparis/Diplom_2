package edu.praktikum.diplom2.tests;

import edu.praktikum.diplom2.utils.RestAssuredUtil;
import org.junit.After;
import org.junit.BeforeClass;

public class BaseTest {
    protected String accessToken;

    @BeforeClass
    public static void setUp() {
        RestAssuredUtil.setUp();
    }

    @After
    public void tearDown() {
        RestAssuredUtil.tearDown(accessToken);
    }
}

