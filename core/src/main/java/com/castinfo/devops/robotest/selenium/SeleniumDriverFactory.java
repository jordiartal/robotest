/********************************************************************************
 * ROBOTEST
 * Copyright (C) 2018 CAST-INFO, S.A. www.cast-info.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.castinfo.devops.robotest.selenium;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.ssl.SSLInitializationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.config.BrowserStackConfig;
import com.castinfo.devops.robotest.config.DockerConfig;
import com.castinfo.devops.robotest.config.RobotestBrowserConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.WebDriverManagerException;

/**
 * Selenium Web Driver factory.
 */
public class SeleniumDriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(SeleniumDriverFactory.class);

    private static final int NATIVE_ONE_MINUTE_TIMEOUT_IN_MILLIS = 60000;
    private static final int NATIVE_ONE_MINUTE_TIMEOUT_IN_SECONDS = 60;

    /**
     * Flag if DriverManager load native CHROME browserCfg for avoid repetitive driver manager downloads
     */
    private static boolean nativeChromeInitied = false;
    /**
     * Flag if DriverManager load native FIREFOX browserCfg for avoid repetitive driver mananger downloads
     */
    private static boolean nativeFirefoxInitied = false;
    /**
     * Flag if DriverManager load native internet explorer browserCfg for avoid repetitive driver mananger downloads
     */
    private static boolean nativeInternetExplorerInitied = false;

    /**
     * BrowserConfig
     */
    private RobotestBrowserConfig browserCfg = null;

    /**
     * Constructor with basic params.
     *
     * @param browserCfg
     *            robotest config of browserCfg
     */
    public SeleniumDriverFactory(final RobotestBrowserConfig browserCfg) {
        this.browserCfg = browserCfg;
    }

    /**
     * Getter method for nativeChromeInitied.
     *
     * @return the nativeChromeInitied
     */
    public static boolean isNativeChromeInitied() {
        return SeleniumDriverFactory.nativeChromeInitied;
    }

    /**
     * Setter method for the nativeChromeInitied.
     *
     * @param nativeChromeInitied
     *            the nativeChromeInitied to set
     */
    public static void setNativeChromeInitied(final boolean nativeChromeInitied) {
        SeleniumDriverFactory.nativeChromeInitied = nativeChromeInitied;
    }

    /**
     * Getter method for nativeFirefoxInitied.
     *
     * @return the nativeFirefoxInitied
     */
    public static boolean isNativeFirefoxInitied() {
        return SeleniumDriverFactory.nativeFirefoxInitied;
    }

    /**
     * Setter method for the nativeFirefoxInitied.
     *
     * @param nativeFirefoxInitied
     *            the nativeFirefoxInitied to set
     */
    public static void setNativeFirefoxInitied(final boolean nativeFirefoxInitied) {
        SeleniumDriverFactory.nativeFirefoxInitied = nativeFirefoxInitied;
    }

    /**
     * Getter method for nativeInternetExplorerInitied.
     *
     * @return the nativeInternetExplorerInitied
     */
    public static boolean isNativeInternetExplorerInitied() {
        return nativeInternetExplorerInitied;
    }

    /**
     * Setter method for the nativeInternetExplorerInitied.
     *
     * @param nativeInternetExplorerInitied
     *            the nativeInternetExplorerInitied to set
     */
    public static void setNativeInternetExplorerInitied(final boolean nativeInternetExplorerInitied) {
        SeleniumDriverFactory.nativeInternetExplorerInitied = nativeInternetExplorerInitied;
    }

    /**
     * Build a Selenium WebDriver of a browserCfg type to associated by Robotest Docker Selenium HUB.
     * Docker CHROME &amp; FIREFOX are supported
     *
     * @param dockerCfg
     *            docker config with hub info.
     * @return WebDriver the build driver.
     * @throws RobotestException
     *             Errors creating driver.
     */
    public WebDriver buildDockerHubWebDriver(final DockerConfig dockerCfg) throws RobotestException {
        WebDriver webdriver = null;
        DesiredCapabilities capabilities = null;
        try {
            if (SeleniumBrowser.FIREFOX.name().equalsIgnoreCase(this.browserCfg.getBrowserName())) {
                capabilities = DesiredCapabilities.firefox();
            } else if (SeleniumBrowser.CHROME.name().equalsIgnoreCase(this.browserCfg.getBrowserName())) {
                capabilities = DesiredCapabilities.chrome();
            } else {
                throw new RobotestException("NOT DOCKER AVAILABLE FOR THIS BROWSER. REVISE ROBOTEST_BROWSER CONFIG: "
                        + this.browserCfg.getBrowserName());
            }
            if (null == dockerCfg || StringUtils.isEmpty(dockerCfg.getHub())) {
                throw new RobotestException("SELENIUM DRIVER DOCKER HUB CONFIG NOT FOUND.");
            }

            this.preCreationDriverCapabilities(capabilities);

            webdriver = new RemoteWebDriver(new URL(dockerCfg.getHub()), capabilities);

            this.postCreationDriverCapabilities(webdriver, capabilities, false);

        } catch (IOException | UnreachableBrowserException e) {
            throw new RobotestException("DOCKER HUB SELENIUM DRIVER CREATION ERROR", e);
        }
        return webdriver;
    }

    /**
     * BrowserStack build remote WebDriver for real Android &amp; iOs.
     *
     * @param suiteName
     *            suite or category identifier.
     * @param caseName
     *            case name identifier.
     * @param browserStackCfg
     *            BrowserStack configuration params (platform, divice, browserCfg, username, accesskey...)
     * @return WebDriver
     * @throws RobotestException
     *             error in WebDriver creation.
     */
    public WebDriver
           buildBrowserStackRealDeviceWebDriver(final String suiteName, final String caseName,
                                                final BrowserStackConfig browserStackCfg) throws RobotestException {
        if (null == browserStackCfg) {
            throw new RobotestException("BROWSERSTACK CONFIG IS MANDATORY! REVISE BASIC CONFIG.");
        }
        this.browserStackCfgValidations(suiteName, caseName, browserStackCfg);
        WebDriver webdriver = null;
        DesiredCapabilities capabilities = this.browserStackCapabilities(suiteName, caseName, browserStackCfg);
        try {
            webdriver = new RemoteWebDriver(new URL("http://" + browserStackCfg.getLogin() + ":"
                    + browserStackCfg.getAccessKey() + "@hub-cloud.browserstack.com/wd/hub"), capabilities);
        } catch (WebDriverException | IOException | SSLInitializationException e) {
            throw new RobotestException("BROWSER STACK SELENIUM DRIVER CRETION ERROR", e);
        }
        return webdriver;
    }

    /**
     * Browser stack config validations.
     *
     * @param suiteName
     *            suite
     * @param caseName
     *            case
     * @param browserStackCfg
     *            cfg
     * @throws RobotestException
     *             validations errors
     */
    protected void browserStackCfgValidations(final String suiteName, final String caseName,
                                              final BrowserStackConfig browserStackCfg) throws RobotestException {
        if (!"IPHONE".equals(this.browserCfg.getBrowserName()) && !"IPAD".equals(this.browserCfg.getBrowserName())
                && !"ANDROID".equals(this.browserCfg.getBrowserName())) {
            throw new RobotestException("BROWSERSTACK BROWSER NOT SUPORTED: " + this.browserCfg.getBrowserName()
                    + " [ONLY IPAD,IPHONE,ANDROID] REVISE BASIC CONFIG.");
        }
        if (StringUtils.isEmpty(browserStackCfg.getDevice())) {
            throw new RobotestException("BROWSERSTACK DEVICE IS MANDATORY! REVISE BASIC CONFIG.");
        }
        if (StringUtils.isEmpty(browserStackCfg.getPlatform())) {
            throw new RobotestException("BROWSERSTACK PLATFORM IS MANDATORY! REVISE BASIC CONFIG.");
        }
        if (StringUtils.isEmpty(browserStackCfg.getLogin())) {
            throw new RobotestException("BROWSERSTACK USERNAME IS MANDATORY! REVISE BASIC CONFIG.");
        }
        if (StringUtils.isEmpty(browserStackCfg.getAccessKey())) {
            throw new RobotestException("BROWSERSTACK ACCESSKEY IS MANDATORY! REVISE BASIC CONFIG.");
        }
        this.browserStackCfgValidationsNames(suiteName, caseName);
    }

    private void browserStackCfgValidationsNames(final String suiteName,
                                                 final String caseName) throws RobotestException {
        if (StringUtils.isEmpty(suiteName)) {
            throw new RobotestException("BROWSERSTACK SUITE NAME IS MANDATORY.");
        }
        if (StringUtils.isEmpty(caseName)) {
            throw new RobotestException("BROWSERSTACK CASE NAME IS MANDATORY.");
        }
    }

    /**
     * Construct BS capabilities.
     *
     * @param suiteName
     *            suite
     * @param caseName
     *            case
     * @param browserStackCfg
     *            bs cfg
     * @return Capabilities
     */
    protected DesiredCapabilities browserStackCapabilities(final String suiteName, final String caseName,
                                                           final BrowserStackConfig browserStackCfg) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String bsAdaptedBroserName;
        if ("IPHONE".equals(this.browserCfg.getBrowserName())) {
            bsAdaptedBroserName = "iPhone";
        } else if ("IPAD".equals(this.browserCfg.getBrowserName())) {
            bsAdaptedBroserName = "iPad";
        } else {
            bsAdaptedBroserName = "android";
        }
        capabilities.setCapability("browserName", bsAdaptedBroserName);
        capabilities.setCapability("device", browserStackCfg.getDevice());
        capabilities.setCapability("os_version", browserStackCfg.getPlatform());
        capabilities.setCapability("build", suiteName);
        capabilities.setCapability("project", caseName);
        capabilities.setCapability("realMobile", Boolean.TRUE.toString());
        capabilities.setCapability("browserstack.console", "disable");
        capabilities.setCapability("browserstack.networkLogs", Boolean.FALSE.toString());
        capabilities.setCapability("browserstack.debug", Boolean.FALSE.toString());
        capabilities.setCapability("browserstack.local", Boolean.FALSE.toString());
        capabilities.setCapability("browserstack.video", Boolean.FALSE.toString());
        capabilities.setCapability("acceptSslCerts", Boolean.FALSE.toString());
        return capabilities;
    }

    /**
     * Build a local browserCfg instance with https://github.com/bonigarcia/webdrivermanager facilities for development.
     *
     * IMPORTANT NOTES!
     * CI *nix systems not suport native browsers without X systems.
     * Native browserCfg version have is correspondent native driver managed
     *
     * @return WebDriver
     * @throws RobotestException
     *             error in driver creation.
     */
    public WebDriver buildLocalNativeWebDriver() throws RobotestException {
        Pair<WebDriver, DesiredCapabilities> localDriver;
        LOG.info("TRY TO UP NATIVE DRIVER: {}", this.browserCfg);
        try {
            if (SeleniumBrowser.CHROME.name().equalsIgnoreCase(this.browserCfg.getBrowserName())) {
                if (!SeleniumDriverFactory.isNativeChromeInitied()) {
                    WebDriverManager.chromedriver().setup();
                    SeleniumDriverFactory.setNativeChromeInitied(true);
                }
                localDriver = this.buildChromeNativeDriver();
            } else if (SeleniumBrowser.FIREFOX.name().equalsIgnoreCase(this.browserCfg.getBrowserName())) {
                if (!SeleniumDriverFactory.isNativeFirefoxInitied()) {
                    WebDriverManager.firefoxdriver().setup();
                    SeleniumDriverFactory.setNativeFirefoxInitied(true);
                }
                localDriver = this.buildFirefoxNativeDriver();
            } else if (SeleniumBrowser.INTERNET_EXPLORER.name().equalsIgnoreCase(this.browserCfg.getBrowserName())) {
                if (!SeleniumDriverFactory.isNativeInternetExplorerInitied()) {
                    WebDriverManager.iedriver().setup();
                    SeleniumDriverFactory.setNativeInternetExplorerInitied(true);
                }
                localDriver = this.buildInternetExplorerNativeDriver();
            } else {
                throw new RobotestException("NATIVE DRIVER NOT IMPLEMENTED. REVISE ROBOTEST_BROWSER BASE CONFIG.");
            }
            LOG.info("NATIVE DRIVER CREATED: {} HEADLESS: {}", this.browserCfg.getBrowserName(),
                     this.browserCfg.getHeadLess());
            this.postCreationDriverCapabilities(localDriver.getLeft(), localDriver.getRight(), true);
        } catch (WebDriverManagerException | WebDriverException | IllegalStateException e) {
            throw new RobotestException("NATIVE DRIVER CREATION ERROR", e);
        }
        return localDriver.getLeft();
    }

    /**
     * Build native CHROME driver.
     *
     * @return WebDriver and capabilities for CHROME.
     */
    private Pair<WebDriver, DesiredCapabilities> buildChromeNativeDriver() {
        WebDriver driver;
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        this.preCreationDriverCapabilities(capabilities);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(capabilities);
        List<String> arguments = new ArrayList<>();
        arguments.add("--no-sandbox");
        arguments.add("--no-proxy-server");
        if ("true".equals(this.browserCfg.getHeadLess())) {
            arguments.add("--headless");
            arguments.add("--disable-gpu");
        }
        arguments.add("--window-size=" + this.browserCfg.getWindowWidth() + "," + this.browserCfg.getWindowHeight());
        chromeOptions.addArguments(arguments);
        driver = new ChromeDriver(chromeOptions);
        return new ImmutablePair<>(driver, capabilities);
    }

    /**
     * Build native FIREFOX driver.
     *
     * @return WebDriver and capabilities for FIREFOX.
     */
    private Pair<WebDriver, DesiredCapabilities> buildFirefoxNativeDriver() {
        WebDriver driver;
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        this.preCreationDriverCapabilities(capabilities);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.merge(capabilities);
        List<String> arguments = new ArrayList<>();
        if ("true".equals(this.browserCfg.getHeadLess())) {
            arguments.add("-headless");
        }
        arguments.add("-width " + this.browserCfg.getWindowWidth());
        arguments.add("-height " + this.browserCfg.getWindowHeight());
        firefoxOptions.addArguments(arguments);
        driver = new FirefoxDriver(firefoxOptions);
        return new ImmutablePair<>(driver, capabilities);
    }

    /**
     * Build native internet explorer driver.
     *
     * @return WebDriver and capabilities for internet explorer.
     */
    private Pair<WebDriver, DesiredCapabilities> buildInternetExplorerNativeDriver() {
        WebDriver driver;
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability("ignoreZoomSetting", true);
        this.preCreationDriverCapabilities(capabilities);
        InternetExplorerOptions ieOptions = new InternetExplorerOptions();
        ieOptions.merge(capabilities);
        driver = new InternetExplorerDriver(ieOptions);
        return new ImmutablePair<>(driver, capabilities);
    }

    /**
     * Pre creation driver common capabilities.
     *
     * @param capabilities
     *            current capabilities
     */
    protected void preCreationDriverCapabilities(final DesiredCapabilities capabilities) {
        this.configureBrowserLogs(capabilities);
        this.preDriverCreationChromeCapabilites(capabilities);
        this.preDriverCreationFirefoxCapabilities(capabilities);
        if (!SeleniumBrowser.INTERNET_EXPLORER.name().equalsIgnoreCase(capabilities.getBrowserName())) {
            capabilities.setAcceptInsecureCerts(true);
        }
        capabilities.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        com.google.gson.JsonObject timeouts = new com.google.gson.JsonObject();
        timeouts.addProperty("implicit", 0);
        timeouts.addProperty("pageLoad", SeleniumDriverFactory.NATIVE_ONE_MINUTE_TIMEOUT_IN_MILLIS);
        timeouts.addProperty("script", SeleniumDriverFactory.NATIVE_ONE_MINUTE_TIMEOUT_IN_MILLIS);
        capabilities.setCapability("timeouts", timeouts);
    }

    /**
     * Configure browserCfg logs for all browsers except browserStack
     *
     * @param capabilities
     *            current capabilities
     */
    protected void configureBrowserLogs(final DesiredCapabilities capabilities) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.parse(this.browserCfg.getConsoleLogLevel()));
        logs.enable(LogType.CLIENT, Level.parse(this.browserCfg.getConsoleLogLevel()));
        logs.enable(LogType.DRIVER, Level.parse(this.browserCfg.getConsoleLogLevel()));
        logs.enable(LogType.PERFORMANCE, Level.parse(this.browserCfg.getConsoleLogLevel()));
        logs.enable(LogType.PROFILER, Level.parse(this.browserCfg.getConsoleLogLevel()));
        logs.enable(LogType.SERVER, Level.parse(this.browserCfg.getConsoleLogLevel()));
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
    }

    /**
     * Pre driver creation Firefox capabilities
     *
     * @param capabilities
     *            current capabilities
     */
    protected void preDriverCreationFirefoxCapabilities(final DesiredCapabilities capabilities) {
        if (capabilities.getBrowserName().equalsIgnoreCase(SeleniumBrowser.FIREFOX.name())) {
            capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
            capabilities.setCapability("acceptSslCerts", true);
            capabilities.setCapability("marionette", true);
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            FirefoxProfile fp = new FirefoxProfile();
            // fp.setPreference("media.autoplay.enabled", false); // video-off
            // fp.setPreference("media.ogg.enabled", false); // video-off
            // fp.setPreference("media.webm.enabled", false); // video-off
            // fp.setPreference("media.windows-media-foundation.enabled", false); // video-off
            fp.setPreference("toolkit.startup.max_resumed_crashes", -1); // Desactivar el Safe Mode
            fp.setPreference("browserCfg.sessionstore.postdata", -1); // Desactivar el "Document Expired"
            fp.setPreference("network.proxy.type", 0); // Sin proxy (1 -> Con proxy de sistema)
            fp.setPreference("browserCfg.cache.disk.enable", true);
            fp.setPreference("browserCfg.cache.memory.enable", true);
            fp.setPreference("browserCfg.cache.offline.enable", true);
            fp.setPreference("network.http.use-cache", true);
            fp.setPreference("startup.homepage_welcome_url.additional", "");
            fp.setPreference("startup.homepage_welcome_url", "");
            capabilities.setCapability(FirefoxDriver.PROFILE, fp);
        }
    }

    /**
     * Pre driver creation Chrome capabilities
     *
     * @param capabilities
     *            current capabilities
     */
    protected void preDriverCreationChromeCapabilites(final DesiredCapabilities capabilities) {
        if (capabilities.getBrowserName().equalsIgnoreCase(SeleniumBrowser.CHROME.name())) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(Arrays.asList("--no-sandbox", "--no-proxy-server", "--start-maximized"));
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
    }

    /**
     * Post cration driver common configurations.
     *
     * @param webdriver
     *            webdriver created
     * @param capabilities
     *            current capabilities
     * @param localNative true if is local native browser
     */
    protected void postCreationDriverCapabilities(final WebDriver webdriver, final DesiredCapabilities capabilities,
                                                  final boolean localNative) {
        this.deleteCookiesAndSetTimemouts(webdriver);
        if ("true".equals(this.browserCfg.getMaximized())) {
            this.forzeMaximizeWindow(webdriver, capabilities, localNative);
        }
    }

    /**
     * Forze max window for all browsers, except CHROME.
     *
     * @param webdriver
     *            webdriver created
     * @param capabilities
     *            current capabilities
     * @param localNative true if is local native browser
     */
    protected void forzeMaximizeWindow(final WebDriver webdriver, final DesiredCapabilities capabilities,
                                       final boolean localNative) {
        if (localNative || !capabilities.getBrowserName().equalsIgnoreCase(SeleniumBrowser.CHROME.name())) {
            webdriver.manage().window().maximize();
        }
    }

    /**
     * Manage delete Cookies and set timeouts.
     *
     * @param webdriver
     *            webdriver created
     */
    protected void deleteCookiesAndSetTimemouts(final WebDriver webdriver) {
        webdriver.manage().deleteAllCookies();
        webdriver.manage().timeouts().pageLoadTimeout(SeleniumDriverFactory.NATIVE_ONE_MINUTE_TIMEOUT_IN_SECONDS,
                                                      TimeUnit.SECONDS);
        webdriver.manage().timeouts().setScriptTimeout(SeleniumDriverFactory.NATIVE_ONE_MINUTE_TIMEOUT_IN_SECONDS,
                                                       TimeUnit.SECONDS);
        webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

}
