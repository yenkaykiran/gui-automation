package com.cba.sdgui.service;

import com.cba.sdgui.enums.BrowserType;
import com.cba.sdgui.enums.StepResultType;
import com.cba.sdgui.enums.WaitType;
import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.repository.SDTestRepository;
import com.gargoylesoftware.htmlunit.WebClient;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.browserlaunchers.CapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RunHelperService {

    private WebDriver webDriver;

    @Autowired
    private StepInstanceService stepInstanceService;

    @Autowired
    private SDTestRepository sdTestRepository;

    @Value("${phantomjs.binary.path}")
    private String phantomDriverPath;

    public void startTest(Integer id, TestRun run, Boolean headless, Boolean maximizeWindow) {
        SDTest test = sdTestRepository.findById(id);
        BrowserType browserType = run.getBrowser().getType();

        DesiredCapabilities capabilities = null;
        switch (browserType) {
        case Chrome:
            capabilities = DesiredCapabilities.chrome();
            break;
        default:
            break;
        }
        Proxy proxy = new Proxy();
        WebClient webClient = null;
        if (null != run.getWithProxy() && run.getWithProxy()) {
            proxy.setProxyType(translateProxy(run.getProxy().getType()));
            proxy.setHttpProxy(run.getProxy().getHttpHost() + ":" + run.getProxy().getHttpPort());
            proxy.setSslProxy(run.getProxy().getSslHost() + ":" + run.getProxy().getSslPort());
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }

        switch (browserType) {
        case Chrome:
            if (headless) {
                /* webDriver = new HtmlUnitDriver(BrowserVersion.CHROME);

                HtmlUnitDriver htmlUnitDriver = (HtmlUnitDriver) webDriver;
                if (null != run.getWithProxy() && run.getWithProxy()) {
                    htmlUnitDriver.setProxySettings(proxy);
                }
                htmlUnitDriver.setJavascriptEnabled(true);
                try {
                    Method method = htmlUnitDriver.getClass().getDeclaredMethod("getWebClient");
                    method.setAccessible(true);
                    webClient = (WebClient) method.invoke(htmlUnitDriver);
                    webClient.setCssErrorHandler(new SilentCssErrorHandler());
                    webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
                        
                        @Override
                        public void timeoutError(InteractivePage page, long allowedTime, long executionTime) {
                            
                        }
                        
                        @Override
                        public void scriptException(InteractivePage page, ScriptException scriptException) {
                            
                        }
                        
                        @Override
                        public void malformedScriptURL(InteractivePage page, String url, MalformedURLException malformedURLException) {
                            
                        }
                        
                        @Override
                        public void loadScriptError(InteractivePage page, URL scriptUrl, Exception exception) {
                            
                        }
                    });
                    webClient.setIncorrectnessListener(new IncorrectnessListener() {
                        @Override
                        public void notify(String message, Object origin) {
                            
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } */
                //                webDriver = new JBrowserDriver(capabilities);
                //                if (null != run.getWithProxy() && run.getWithProxy()) {
                //                    System.setProperty("http.proxyHost", run.getProxy().getHttpHost());
                //                    System.setProperty("http.proxyPort", run.getProxy().getHttpPort().toString());
                //                    System.setProperty("https.proxyHost", run.getProxy().getHttpHost());
                //                    System.setProperty("https.proxyPort", run.getProxy().getHttpPort().toString());
                //                }
                System.setProperty("phantomjs.binary.path", phantomDriverPath);
                webDriver = new PhantomJSDriver(capabilities);
            } else {
                System.setProperty(run.getBrowser().getType().getDriverProperty(), run.getBrowser().getDriverPath());
                webDriver = new ChromeDriver(capabilities);
            }
            break;
        default:
            break;
        }
        List<StepInstance> insts = new ArrayList<StepInstance>();
        try {
            if (maximizeWindow) {
                webDriver.manage().window().maximize();
            }
            webDriver.get(run.getUrl().getAddress());
            List<SDTestStep> testSteps = test.getSteps();
            Collections.sort(testSteps);
            for (SDTestStep eachStep : testSteps) {
                StepInstance stepInstance = new StepInstance();
                long s = 0;
                try {
                    BeanUtils.copyProperties(eachStep, stepInstance, new String[] { "test", "id" });
                    stepInstance.setTest(test);
                    stepInstance.setRun(run);
                    stepInstance.setStepId(eachStep.getId());
                    s = System.currentTimeMillis();
                    if (eachStep.isEnabled()) {
                        stepInstance.setStatus(StepResultType.IN_PROGRESS.name());
                        System.out.println(eachStep.getName());
                        By element = getElement(eachStep);
                        WebElement webElement = null;
                        if (null != element) {
                            webElement = webDriver.findElement(element);
                        }
                        if (eachStep.getIsWait()) {
                            if (WaitType.NONE != eachStep.getWaitType()) {
                                WebDriverWait wait = new WebDriverWait(webDriver, eachStep.getWaitTime(), TimeUnit.SECONDS.toMillis(eachStep.getWaitTime()));
                                wait.until(getWaitCondition(element, eachStep));
                            } else {
                                try {
                                    TimeUnit.SECONDS.sleep(eachStep.getWaitTime());
                                } catch (InterruptedException e) {
                                    stepInstance.setStatus(StepResultType.WAIT_FAILED.name());
                                    stepInstance.setException(e.getMessage());
                                }
                            }
                        }
                        if (eachStep.getIsAction()) {
                            performAction(eachStep, element, webDriver, webElement, webClient, headless);
                        }
                        boolean needsVerification = null != eachStep.getNeedVerification() && eachStep.getNeedVerification() == true;
                        if (needsVerification) {
                            startVerification(eachStep, stepInstance, webElement);
                        }
                        if (null != eachStep.getExtractData() && eachStep.getExtractData()) {
                            extractRequiredDataFromPage(eachStep, stepInstance, element);
                        }
                        stepInstance.setStatus(StepResultType.FINISHED.name());
                    } else {
                        stepInstance.setStatus(StepResultType.SKIPPED.name());
                    }
                } catch (Exception e) {
                    stepInstance.setStatus(StepResultType.EXECUTION_FAILED.name());
                    stepInstance.setException(e.getMessage());
                } finally {
                    stepInstance.setTimeConsumed(System.currentTimeMillis() - s);
                    insts.add(stepInstance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished Test");
            if (null != webDriver) {
                webDriver.quit();
            }
            stepInstanceService.saveAll(insts);
        }
    }

    private void extractRequiredDataFromPage(SDTestStep eachStep, StepInstance stepInstance, By by) {
        StringBuilder data = new StringBuilder();
        switch (eachStep.getExtractType()) {
        case TEXT:
            data.append(webDriver.findElement(by).getText());
            break;
        case SIZE:
            data.append(webDriver.findElements(by).size());
        case TABLE_DATA:
            Map<Integer, String[]> tableDataAsMap = new HashMap<Integer, String[]>();
            int rowCount = webDriver.findElements(By.xpath(eachStep.getElement().getIdentity() + "/tr")).size();
            int colCount = webDriver.findElements(By.xpath(eachStep.getElement().getIdentity() + "/tr[1]/td")).size();
            String firstPart = eachStep.getElement().getIdentity() + "/tr[";
            String secondPart = "]/td[";
            String thirdPart = "]";
            for (int i = 1; i <= rowCount; i++) {
                String[] rowData = new String[colCount];
                for (int j = 1; j <= colCount; j++) {
                    String finalXpath = firstPart + i + secondPart + j + thirdPart;
                    rowData[j - 1] = webDriver.findElement(By.xpath(finalXpath)).getText();
                }
                tableDataAsMap.put(i - 1, rowData);
            }
            Gson json = new Gson();
            data.append(json.toJson(tableDataAsMap));
        default:
            break;
        }
        stepInstance.setExtractedData(data.toString());
    }

    private void startVerification(SDTestStep eachStep, StepInstance inst, WebElement webElement) {
        if (null != eachStep.getVisibility() && eachStep.getVisibility()) {
            if (webElement.isDisplayed()) {
                inst.setVisibilityCheck(StepResultType.VERIFY_PASSED.name());
            } else {
                inst.setVisibilityCheck(StepResultType.VERIFY_FAILED.name());
            }
        }
        if (null != eachStep.getEnabledisable() && eachStep.getEnabledisable()) {
            if (webElement.isEnabled()) {
                inst.setEnableCheck(StepResultType.VERIFY_PASSED.name());
            } else {
                inst.setEnableCheck(StepResultType.VERIFY_FAILED.name());
            }
        }
    }

    private ProxyType translateProxy(com.cba.sdgui.enums.ProxyType type) {
        return ProxyType.valueOf(ProxyType.class, type.toString());
    }

    protected void performAction(SDTestStep eachStep, By by, WebDriver driver, WebElement webElement, WebClient webClient, Boolean headless) {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        switch (eachStep.getActionType()) {
        case SendKeys:
            webElement.sendKeys(eachStep.getKeys());
            break;
        case Click:
            webElement.click();
            break;
        case MOVE_TO_ELEMENT:
            Actions action = new Actions(driver);
            action.moveToElement(webElement).build().perform();
        default:
            break;
        }
        //        if(headless && null != webClient) {
        //            webClient.waitForBackgroundJavaScript(30000);
        //        }
    }

    protected ExpectedCondition<WebElement> getWaitCondition(By element, SDTestStep eachStep) {
        ExpectedCondition<WebElement> condition = null;
        switch (eachStep.getWaitType()) {
        case UNTIL_CLICKABLE:
            condition = ExpectedConditions.elementToBeClickable(element);
            break;
        case ELEMENT_AVAILABLE:
            condition = ExpectedConditions.presenceOfElementLocated(element);
            break;
        default:
            break;
        }
        return condition;
    }

    public By getElement(SDTestStep eachStep) {
        By element = null;
        Element elem = eachStep.getElement();
        String elementIdentity = elem.getIdentity();
        if (StringUtils.isNotBlank(elementIdentity)) {
            switch (elem.getIdentificationType()) {
            case Id:
                element = By.id(elementIdentity);
                break;
            case Class:
                element = By.className(elementIdentity);
                break;
            case CssSelector:
                element = By.cssSelector(elementIdentity);
                break;
            case Link:
                element = By.linkText(elementIdentity);
                break;
            case Name:
                element = By.name(elementIdentity);
                break;
            case PartialText:
                element = By.partialLinkText(elementIdentity);
                break;
            case TagName:
                element = By.tagName(elementIdentity);
                break;
            case XPath:
                element = By.xpath(elementIdentity);
                break;
            default:
                break;
            }
        }
        return element;
    }
}