package com.castinfo.devops.robotest.examples;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.TestCase;
import com.castinfo.devops.robotest.annot.RobotestCase;
import com.castinfo.devops.robotest.annot.RobotestConfig;
import com.castinfo.devops.robotest.annot.RobotestSuite;
import com.castinfo.devops.robotest.junit.JUnitCaseRunner;

@RobotestSuite(tag = "HELLO_WORLD_ROBOTEST", description = "Standard navigation through Castinfo web",
               configElements = { @RobotestConfig(	key = "WEB_TO_TEST", type = Properties.class, resource = "system://CAST-INFO-WEB") })
@RunWith(JUnitCaseRunner.class)
public class ITHelloWorld extends TestCase {

    @Test
    @RobotestCase(tag = "HELLO_WORLD_ROBOTEST_CASE_001", description = "The ROBOTEST HelloWorld example")
    public void helloWorld() throws RobotestException {
    	HelloWorldPageObject helloWorldPO = this.buildPageObject(HelloWorldPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg("WEB_TO_TEST", "CAST-INFO-WEB");
        helloWorldPO.openURL(urlToTest);
        helloWorldPO.checkTitle();
    }

}