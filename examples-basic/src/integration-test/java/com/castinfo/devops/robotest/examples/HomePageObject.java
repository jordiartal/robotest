package com.castinfo.devops.robotest.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.castinfo.devops.robotest.PageObject;
import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.annot.RobotestStep;
import com.castinfo.devops.robotest.config.RobotestConfiguration;

import utils.Utils;

/**
 * Test Cases related to Cast-Info Home Page
 *
 * @author Jordi.Artal
 *
 */
public class HomePageObject extends PageObject {

	private static final Logger LOG = LoggerFactory.getLogger(RobotestConfiguration.class);
	
	/**
	 * Compares current page title with a literal expression
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_001", description = "Check home title", captureScreenShootAtEndStep = true)
	public void checkTitle() throws RobotestException {
		String parameter = "Cast Info | Cast Info s.a > Soluciones y Servicios tecnológicos de Vanguardia";
		if (!this.getDriver().getTitle().equals(parameter)) {
			throw new RobotestException("Screen doesn't contains this Title: " + parameter);
		}
	}

	/**
	 * Fills Contacto form boxes
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_002", description = "Check Contacto form works", captureScreenShootAtEndStep = true)
	public void checkUseForm() throws RobotestException {
		WebElement name = this.getDriver().findElement(By.xpath("//*[@id=\"input_2_1_3\"]"));
		this.moveToElement(name);
		name.clear();
		name.sendKeys("test_name");
		Assert.assertTrue("insertion error in contact name", name.getAttribute(Utils.VALUE.getStringValue()).equals("test_name"));

		WebElement email = this.getDriver().findElement(By.xpath("//*[@id=\"input_2_2\"]"));
		this.moveToElement(email);
		email.clear();
		email.sendKeys("test_email");
		Assert.assertTrue("insertion error in contact eMail", email.getAttribute(Utils.VALUE.getStringValue()).equals("test_email"));

		WebElement message = this.getDriver().findElement(By.xpath("//*[@id=\"input_2_11\"]"));
		this.moveToElement(message);
		message.clear();
		message.sendKeys(
				"test message: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer mauris erat, vulputate at fermentum ac, lacinia sed sem.");
		Assert.assertTrue("insertion error in contact message", message.getAttribute(Utils.VALUE.getStringValue()).equals(
				"test message: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer mauris erat, vulputate at fermentum ac, lacinia sed sem."));

		WebElement rgpd = this.getDriver().findElement(By.xpath("//*[@id=\"choice_2_10_1\"]"));
		this.moveToElement(rgpd);
		rgpd.click();
		Assert.assertTrue("insertion error in contact message", rgpd.isSelected());

	}

	/**
	 * Verify that html element with id "top-menu" exists
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_003", description = "Check Top Menu exists", captureScreenShootAtEndStep = true)
	public void checkTopMenu() throws RobotestException {
		String parameter = "top-menu";
		this.getDriver().manage().window().maximize();
		Assert.assertTrue("element not found:" + parameter,
				this.getDriver().findElement(By.id(parameter)).isDisplayed());
	}

	/**
	 * go through top menu links and verify it
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_004", description = "Check top Menu Links", captureScreenShootAtEndStep = true)
	public void checkTopMenuLinks() throws RobotestException {
		List<WebElement> linkList = this.getDriver().findElements(By.xpath("//nav/ul/li/a[@href]"));
		if (!linkList.isEmpty()) {
			List<String> hrefList = new ArrayList<>();
			for (WebElement elem : linkList) {
				hrefList.add(elem.getAttribute("href"));
			}
			for (String href : hrefList) {
				this.waitForPageLoaded(1L);
				this.getDriver().navigate().to(href);
				Assert.assertTrue(href.equals(this.getCurrentUrl()));
			}
		} else {
			throw new RobotestException("Ther is no elements in this navigation menu");
		}
	}

	/**
	 * Overview of all Home page inner links
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_005", description = "Check internal Links Home Page", captureScreenShootAtEndStep = true)
	public void checkHomeLinks() throws RobotestException {
		List<WebElement> linkList = this.getDriver().findElements(By.xpath("//a[@href]"));
		if (linkList != null) {
			List<String> hrefList = new ArrayList<>();
			homeLinksFor(linkList, hrefList);
			
			// Pages that exceeds getGeneralWaitTimoutSeconds has been excluded
			int i = 0;
			for (String href : hrefList) {
				if (!href.contains("@") && !href.equals("https://www.cast-info.es/")
						&& href.contains("https://www.cast-info.es/")
						&& !href.equals("https://www.cast-info.es/#contador")
						&& !href.equals("https://www.cast-info.es/productos/outsourcing")
						&& !href.equals("https://www.cast-info.es/castinfo/nota-legal/")) {
					if (!href.endsWith("/")) {
						href += "/";
					}
					i++;
					HomePageObject.LOG.info(i + " - Verifying link: " + href);
					this.getDriver().navigate().to(href);
					this.waitForPageLoaded(2L);
					Assert.assertTrue(("The current URL doesn't contains: " + href),
							this.currentURLContains(href, this.getGeneralWaitTimoutSeconds()));
				}
			}
		} else {
			throw new RobotestException("There is no links in this page");
		}

	}
	
	private List<String> homeLinksFor(List<WebElement> linkList, List<String> hrefList) {
		for (WebElement elem : linkList) {
			String href = "https://www.cast-info.es";
			try {
				href = elem.getAttribute("href");
			} catch (StaleElementReferenceException e) {
				HomePageObject.LOG.error(e.toString());
			}
			if (href != null) {
				hrefList.add(href);
			}
		}
		return hrefList;
	}

	/**
	 * Go to Home page slider buttons and verify its links
	 *
	 * @throws RobotestException
	 * @throws InterruptedException
	 */
	@RobotestStep(tag = "HOME_STEP_006", description = "Check Slider Buttons Home Page", captureScreenShootAtEndStep = true)
	public void checkSliderButtons() throws RobotestException, InterruptedException {

		closePrivacyMessages();

		List<WebElement> listElem = this.getDriver().findElements(By.xpath("//div[@class='et-pb-controllers']/a"));
		if (!listElem.isEmpty()) {
			String parentWindow = this.getDriver().getWindowHandle();
			Boolean assertCondition = false;
			int i = 1;
			for (WebElement elem : listElem) {
				// go to the slide
				if (i > 1) {
					this.getDriver().switchTo().window(parentWindow);
				}
				elem.click();

				// find individual slide and verify that is visible
				String className = "et_pb_slide_" + i;
				WebElement sliderElem = this.getDriver().findElement(By.className(className));
				assertCondition = sliderElem.getCssValue("opacity").equals("1");
				Assert.assertTrue("Slider element num: " + i + " is not visible", assertCondition);

				// open slider link in a new Tab
				this.openLinkInNewTab(By.xpath("//*[@id=\"post-42\"]/div/div[1]/div/div[4]/a[" + i + "]"), 1L);
				this.getDriver().getWindowHandle();
				i++;
			}
		}
	}

	/**
	 * Make a Cookie list and list in log.info object
	 *
	 * @throws RobotestException
	 * @throws InterruptedException
	 */
	@RobotestStep(tag = "HOME_STEP_007", description = "Check Cookies", captureScreenShootAtEndStep = true)
	public void checkCastCookies() throws RobotestException, InterruptedException {

		closePrivacyMessages();

		Set<Cookie> listCookies = this.listOfCookiesAvailable();
		if (!listCookies.isEmpty()) {
			for (Cookie cookie : listCookies) {
				HomePageObject.LOG.info("cookie found: " + cookie.toString());
			}
		} else {
			HomePageObject.LOG.info("No cookies available");
		}
	}

	/**
	 * Create a Cookie
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_008", description = "Check Create Cookie", captureScreenShootAtEndStep = true)
	public void checkCreateCookie() throws RobotestException {
		this.addCookie(Utils.TEST_CREATE_COOKIE.getStringValue(), "description=test value cookie");
		HomePageObject.LOG.info("cookie with: " + Utils.TEST_CREATE_COOKIE.getStringValue() + " and values: "
				+ this.getCookieValueByName(Utils.TEST_CREATE_COOKIE.getStringValue()) + " has been created.");
	}

	/**
	 * Do a Cookie list and erase all cookies one by one
	 *
	 * @throws RobotestException
	 */
	@RobotestStep(tag = "HOME_STEP_009", description = "Check Cast Cookie", captureScreenShootAtEndStep = true)
	public void checkDeleteCastCookies() throws RobotestException {
		Set<Cookie> listCookies = this.listOfCookiesAvailable();
		for (Cookie cookie : listCookies) {
			this.getDriver().manage().deleteCookieNamed(cookie.getName());
			HomePageObject.LOG.info("cookie with name: '" + cookie.getName() + "' has been erased");
		}
	}

	public void checkGotoContacto() throws RobotestException, InterruptedException {
		WebElement contactoLink = this.findElementBy(By.xpath("//*[@id=\"et-secondary-nav\"]/li[3]/a"));
		if (contactoLink != null) {
			contactoLink.click();
			String parameter = "Contacto | Cast Info";
			if (!this.getDriver().getTitle().equals(parameter)) {
				throw new RobotestException("Screen doesn't contains this Title: " + parameter);
			}
			Thread.sleep(2000L);

			closePrivacyMessages();
		} else {
			Assert.assertTrue("there is no 'Contacto' link in this page", false);
		}
	}
	
	public void closePrivacyMessages() throws InterruptedException, RobotestException {
		this.findElementBy(By.xpath("/html/body/div[4]/div/div[2]/div/button[2]")).click();
		Thread.sleep(3500L);

		this.findElementBy(By.xpath("//*[@id=\"cookie_action_close_header\"]")).click();
		Thread.sleep(2000L);
	}

}
