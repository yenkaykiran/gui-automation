package com.cba.sdgui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cba.sdgui.enums.BrowserType;
import com.cba.sdgui.enums.StepResultType;
import com.cba.sdgui.enums.WaitType;
import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;
import com.gargoylesoftware.htmlunit.BrowserVersion;

@Service
public class RunHelperService {

	private WebDriver webDriver;

	@Autowired
	private StepInstanceService stepInstanceService;

	public void startTest(SDTest test, TestRun run, Boolean headless) {
		BrowserType browserType = run.getBrowser().getType();

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		if (null != run.getWithProxy() && run.getWithProxy()) {
			Proxy proxy = new Proxy();
			proxy.setProxyType(translateProxy(run.getProxy().getType()));
			proxy.setHttpProxy(run.getProxy().getHttpHost() + ":" + run.getProxy().getHttpPort());
			proxy.setSslProxy(run.getProxy().getSslHost() + ":" + run.getProxy().getSslPort());
			if (headless) {
				((HtmlUnitDriver) webDriver).setProxySettings(proxy);
			}
		}

		switch (browserType) {
		case Chrome:
			if (headless) {
				webDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
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
			webDriver.get(run.getUrl().getAddress());
			for (SDTestStep eachStep : test.getSteps()) {
				StepInstance inst = new StepInstance();
				long s = 0;
				try {
					BeanUtils.copyProperties(eachStep, inst, new String[] { "test", "id" });
					inst.setTest(test);
					inst.setRun(run);
					inst.setStepId(eachStep.getId());
					s = System.currentTimeMillis();
					if (eachStep.isEnabled()) {
						inst.setStatus(StepResultType.IN_PROGRESS);
						System.out.println(eachStep.getName());
						By element = getElement(eachStep);
						if (eachStep.getIsWait()) {
							if (WaitType.NONE != eachStep.getWaitType()) {
								WebDriverWait wait = new WebDriverWait(webDriver, eachStep.getWaitTime());
								wait.until(getWaitCondition(element, eachStep));
							} else {
								try {
									TimeUnit.SECONDS.sleep(eachStep.getWaitTime());
								} catch (InterruptedException e) {
								}
							}
						} else if (eachStep.getIsAction()) {
							performAction(eachStep, element, webDriver);
						}
					} else {
						inst.setStatus(StepResultType.SKIPPED);
					}
				} catch (Exception e) {
					inst.setStatus(StepResultType.FAILURE);
					inst.setException(e.getMessage());
				} finally {
					inst.setTimeConsumed(System.currentTimeMillis() - s);
					insts.add(inst);
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

	private ProxyType translateProxy(com.cba.sdgui.enums.ProxyType type) {
		return ProxyType.valueOf(ProxyType.class, type.toString());
	}

	protected void performAction(SDTestStep eachStep, By by, WebDriver driver) {
		WebElement element = driver.findElement(by);
		switch (eachStep.getActionType()) {
		case SendKeys:
			element.sendKeys(eachStep.getKeys());
			break;
		case Click:
			element.click();
			break;
		default:
			break;
		}
	}

	protected ExpectedCondition<WebElement> getWaitCondition(By element, SDTestStep eachStep) {
		ExpectedCondition<WebElement> condition = null;
		switch (eachStep.getWaitType()) {
		case UNTIL_CLICKABLE:
			condition = ExpectedConditions.elementToBeClickable(element);
			break;
		default:
			break;
		}
		return condition;
	}

	public By getElement(SDTestStep eachStep) {
		By element = null;
		if (null != eachStep.getElementIdentity()) {
			switch (eachStep.getIdentificationType()) {
			case Id:
				element = By.id(eachStep.getElementIdentity());
				break;
			case Class:
				element = By.className(eachStep.getElementIdentity());
				break;
			case CssSelector:
				element = By.cssSelector(eachStep.getElementIdentity());
				break;
			case Link:
				element = By.linkText(eachStep.getElementIdentity());
				break;
			case Name:
				element = By.name(eachStep.getElementIdentity());
				break;
			case PartialText:
				element = By.partialLinkText(eachStep.getElementIdentity());
				break;
			case TagName:
				element = By.tagName(eachStep.getElementIdentity());
				break;
			case XPath:
				element = By.xpath(eachStep.getElementIdentity());
				break;
			default:
				break;
			}
		}
		return element;
	}
}