package com.castinfo.devops.robotest.examples;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.castinfo.devops.robotest.PageObject;
import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.annot.RobotestStep;

public class EmpleoPageObject extends PageObject {

    public static final String PRE_HOME_CFG = "PRE_HOME_CFG";

    /*
     * Check Links on Empleo Sections
     */
    @RobotestStep(tag = "EMPLEO_STEP_001", description = "Check Empleo Delegacion", captureScreenShootAtEndStep = true)
    public void checkEmpleoDelegacion() throws RobotestException, URISyntaxException {
        String handler = this.getDriver().getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) this.getDriver();
        List<WebElement> listElem = this.findElementsBy(By.xpath("//*[@id=\"post-1512\"]/div/div[5]/div"));
        Assert.assertNotNull("There is no 'Delegacion' elements to show in this page", listElem);
        for (WebElement elem : listElem) {
            List<WebElement> linkElems = elem.findElements(By.tagName("a"));
            Assert.assertNotNull("the object has no links", linkElems);
            for (WebElement href : linkElems) {
                String link = href.getAttribute("href");
                if (link.contains("mailto")) {
                    continue;
                }
                js.executeScript("window.open('" + link + "','_blank');");
                this.switchToAnotherWindow();
                URI linkDomain = new URI(link);
                URI actualDomain = new URI(this.getCurrentUrl());
                Boolean okPage = actualDomain.getHost().contains(linkDomain.getHost());
                Assert.assertTrue("destination page [" + actualDomain.getHost() + "] is not the same as [" + linkDomain.getHost() + "]",
                                  okPage);
                this.getDriver().close();
                this.getDriver().switchTo().window(handler);
            }
        }
    }

    /*
     * Check email on empleo Sections
     */
    @RobotestStep(tag = "EMPLEO_STEP_002", description = "Check Empleo Mail", captureScreenShootAtEndStep = true)
    public void checkEmpleoEmail(final HomePageObject home) throws RobotestException, InterruptedException {
        home.checkCastCookies();
        List<WebElement> listElems = this.findElementsBy(By.tagName("a"));
        Assert.assertNotNull("there is no links in the current page", listElems);
        int size = listElems.size();
        int i = 0;
        for (WebElement elem : listElems) {
            String link = elem.getAttribute("href");
            if (!link.contains("mailto")) {
                i++;
                if (i == size) {
                    Assert.assertTrue("there is no elements 'mailto' in this element", false);
                }
                continue;
            }
        }
    }

}
