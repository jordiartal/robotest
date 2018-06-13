package com.castinfo.devops.robotest.examples;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.TestCase;
import com.castinfo.devops.robotest.annot.RobotestCase;
import com.castinfo.devops.robotest.annot.RobotestConfig;
import com.castinfo.devops.robotest.annot.RobotestSuite;
import com.castinfo.devops.robotest.junit.JUnitCaseRunner;

import utils.Utils;

/**
 * Robotest Shop Suite that runs a series of tests of Cast-info web page
 *
 * @author Jordi.Artal
 *
 */
@RobotestSuite(	tag = "CASTINFO_SUITE_001", 
				description = "Standad navigation through Cast-Info page", 
				configElements = { @RobotestConfig(	key = "WEB_TO_TEST", 
													type = Properties.class, 
													resource = "system://CAST-INFO-WEB")})
@RunWith(JUnitCaseRunner.class)
public class ITCastExamples extends TestCase {
	
    /**
     * Checks Home Page title
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_001", description = "Home Case Check Title")
    public void checkHomeTitle() throws RobotestException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        preHome.waitForPageLoaded(1L);
        preHome.checkTitle();
    }

    /**
     * Checks Home page's access to Contacto form ant fills its content
     *
     * @throws RobotestException
     * @throws InterruptedException 
     * @throws XPathExpressionException
     * @throws RobotestApiTestingException
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_002", description = "Home Case Check Form Contacto")
    public void checkHomeContacto() throws RobotestException, InterruptedException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        preHome.waitForPageLoaded(1L);
        preHome.checkGotoContacto();
        preHome.checkUseForm();
    }

    /**
     * Checks Home page top menu items
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_003", description = "Home Case Check top-menu-items")
    public void checkHomeTopMenuItems() throws RobotestException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        preHome.waitForPageLoaded(1L);
        preHome.checkTopMenu();
        preHome.checkTopMenuLinks();
    }

    /**
     * Checks Home page Links URL equals resulting page
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_004", description = "Home Case Check all links")
    public void checkHomeInternalLinks() throws RobotestException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        preHome.waitForPageLoaded(1L);
        preHome.checkHomeLinks();
    }

    /**
     * Checks Home page Slider buttons
     *
     * @throws RobotestException
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_005", description = "Home Case Slider Controls")
    public void checkHomeSliderControls() throws RobotestException, InterruptedException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        preHome.getDriver().manage().window().maximize();
        Thread.sleep(1000L);
        preHome.checkSliderButtons();
    }

    /**
     * Checks Home page Cookies
     *
     * @throws RobotestException
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "HOME_CASE_006", description = "Home Case Cookies")
    public void checkHomeCookies() throws RobotestException, InterruptedException {
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue());
        preHome.openURL(urlToTest);
        Thread.sleep(2000L);
        preHome.checkCastCookies();
        preHome.checkCreateCookie();
        preHome.checkDeleteCastCookies();
    }

    /**
     * Checks Empresa page title coincidence with literal expression
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "EMPRESA_CASE_001", description = "Empresa Case Title")
    public void checkEmpTitle() throws RobotestException {
        EmpresaPageObject preEmp = this.buildPageObject(EmpresaPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPRESA.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkTitle();
    }

    /**
     * Check Empresa's page inner sections
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "EMPRESA_CASE_002", description = "Empresa Case Sections")
    public void checkEmpSections() throws RobotestException {
        EmpresaPageObject preEmp = this.buildPageObject(EmpresaPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPRESA.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkSections();
    }

    /**
     * Check Empresa page Calidad links
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "EMPRESA_CASE_003", description = "Empresa Case Calidad Links")
    public void checkEmpCalidad() throws RobotestException {
        EmpresaPageObject preEmp = this.buildPageObject(EmpresaPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPRESA.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkCalidadLinks();
    }

    /**
     * Check Empresa page Clientes links
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "EMPRESA_CASE_004", description = "Empresa Case Clients")
    public void checkEmpClients() throws RobotestException {
        EmpresaPageObject preEmp = this.buildPageObject(EmpresaPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPRESA.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkClientesArrows();
        preEmp.checkClientesLinks();
    }

    /**
     * Check Empresa Google's Maps Frame Pins
     *
     * @throws RobotestException
     * @throws InterruptedException
     */
    @Test
    @RobotestCase(tag = "EMPRESA_CASE_005", description = "Empresa Case Google Maps")
    public void checkEmpGoogleMaps() throws RobotestException, InterruptedException {
        EmpresaPageObject preEmp = this.buildPageObject(EmpresaPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPRESA.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkMaps();
    }

    /**
     * Check Soluciones page inner links
     *
     * @throws RobotestException
     */
    @Test
    @RobotestCase(tag = "SOLUCIONES_CASE_001", description = "Soluciones Case Link List")
    public void checkSolucionesLinkList() throws RobotestException {
        SolucionesPageObject preSol = this.buildPageObject(SolucionesPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+"/soluciones/";
        preSol.openURL(urlToTest);
        preSol.waitForPageLoaded(1L);
        preSol.checkLinkList();
    }

    /**
     * Check Clientes page access to contacto form an fill in
     *
     * @throws RobotestException
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "CLIENTES_CASE_001", description = "Clientes Case Link List")
    public void checkClientesContacto() throws RobotestException, InterruptedException {
        ClientesPageObject preCli = this.buildPageObject(ClientesPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.CLIENTES.getStringValue();
        preCli.openURL(urlToTest);
        Thread.sleep(3000L);
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        preCli.checkContacto(preHome);
    }

    /**
     * Check Clientes page inner links
     *
     * @throws RobotestException
     * @throws InterruptedException 
     * @throws URISyntaxException 
     */
    @Test
    @RobotestCase(tag = "CLIENTES_CASE_002", description = "Clientes Case Link List")
    public void checkClientesLinks() throws RobotestException, InterruptedException, URISyntaxException {
        ClientesPageObject preCli = this.buildPageObject(ClientesPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.CLIENTES.getStringValue();
        preCli.openURL(urlToTest);
        Thread.sleep(1000L);
        preCli.checkClientesLinks();
    }

    /**
     * Check Empleo page links related to Delegacion inner section
     *
     * @throws RobotestException
     * @throws URISyntaxException 
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "EMPLEO_CASE_001", description = "Empleo Case Delegacion")
    public void checkEmpleoDelegacion() throws RobotestException, URISyntaxException, InterruptedException {
        EmpleoPageObject preEmp = this.buildPageObject(EmpleoPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPLEO.getStringValue();
        preEmp.openURL(urlToTest);
        preEmp.waitForPageLoaded(1L);
        preEmp.checkEmpleoDelegacion();
    }

    /**
     * Check Empleo page eMail buttons
     *
     * @throws RobotestException
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "EMPLEO_CASE_002", description = "Empleo Case eMail")
    public void checkEmpleoEmail() throws RobotestException, InterruptedException {
        EmpleoPageObject preEmp = this.buildPageObject(EmpleoPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.EMPLEO.getStringValue();
        preEmp.openURL(urlToTest);
        Thread.sleep(1000L);
        HomePageObject preHome = this.buildPageObject(HomePageObject.class);
        preEmp.checkEmpleoEmail(preHome);
    }

    /**
     * Check Noticias page Post links
     *
     * @throws RobotestException
     * @throws InterruptedException 
     * @throws URISyntaxException 
     */
    @Test
    @RobotestCase(tag = "NOTICIAS_CASE_001", description = "Noticias Case Post")
    public void checkNoticiasPost() throws RobotestException, InterruptedException, URISyntaxException {
        NoticiasPageObject preNot = this.buildPageObject(NoticiasPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.NOTICIAS.getStringValue();
        preNot.openURL(urlToTest);
        Thread.sleep(1000L);
        preNot.checkNoticiasPost();
    }

    /**
     * Check Noticias page search form and performa search action
     *
     * @throws RobotestException
     * @throws InterruptedException 
     */
    @Test
    @RobotestCase(tag = "NOTICIAS_CASE_001", description = "Noticias Case Search")
    public void checkNoticiasSearch() throws RobotestException, InterruptedException {
        NoticiasPageObject preNot = this.buildPageObject(NoticiasPageObject.class);
        String urlToTest = this.getSuiteTestPropertyCfg(Utils.WEB_TO_TEST.getStringValue(), Utils.CAST_INFO_WEB.getStringValue())+Utils.NOTICIAS.getStringValue();
        preNot.openURL(urlToTest);
        preNot.waitForPageLoaded(1L);
        preNot.checkNoticiasSearch();
    }
}