package com.castinfo.devops.robotest.testutils;

import com.castinfo.devops.robotest.PageObject;
import com.castinfo.devops.robotest.annot.RobotestStep;

public class AnnotatedPageObjectExampleErrors2 extends PageObject {

    @RobotestStep(tag = "TEST STEP 001",
                  description = "TEST_DESC",
                  captureScreenShootAtEndStep = true,
                  captureConsoleErrorLogsAtEndStep = true,
                  capturePageSourceAtEndStep = true)
    public void testMethod1() {
        // do nothing
    }

}
