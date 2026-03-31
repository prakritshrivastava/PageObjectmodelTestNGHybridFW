package com.opencart.qa.utils;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.opencart.qa.factory.DriverFactory;

import org.openqa.selenium.support.ui.*;

public class ElementUtil {

	//The variables or methods are not marked as static
    // as they need to be accessed by multithreading later (for parallel execution)
    //The global driver needs to be encapsulated
    private WebDriver driver;
    private Actions act;
    private JavaScriptUtil jsUtil;
    //As nothing is static in this class ,
    // object would be needed to be created to access assets of this class
    //idea is to use the constructor to initialize the driver
    //An initialized instance of the driver would be passed to the constructor
    // which would be linked to the global scope variable.
    //The job of initialization would be taken up by dedicated class (in case of framework)

    public ElementUtil(WebDriver driver){
        this.driver=driver;
        act=new Actions(driver);
        jsUtil=new JavaScriptUtil(driver);
    }

    public List<WebElement> getElements(By locator){
    	return driver.findElements(locator);
    }
    
    //Formula is: <driver>.<find method(By.class.locator)>
    public void performSendKeys(By locator, String sendKeysString){
        getElement(locator).sendKeys(sendKeysString);
    }

    public void performSendKeys(By locator,long timeOut, String sendKeysString){
        getElement(locator,timeOut).sendKeys(sendKeysString);
    }

    public WebElement getElement(By locator){
    	WebElement element=driver.findElement(locator);
        if (Boolean.parseBoolean(DriverFactory.highlight)) {
        	jsUtil.flash(element);
        }
    	return driver.findElement(locator);
    }

    public WebElement getElement(By locator,long timeOut){
        try{
            return driver.findElement(locator);
        }
        catch(NoSuchElementException noEleExp){
            System.out.println("No Element found using locator: "+locator);
            return waitForElementVisible(locator,timeOut);
        }
    }

    //Wait is not initialized on global level like actions , as it is intended to obtain the timeout from the calling method.
    public WebElement waitForElementVisible(By locator,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public boolean isElementDisplayed(By locator){
        try{
            return getElement(locator).isDisplayed();
        }
        catch(NoSuchElementException nsExp){
            System.out.println("No Such Element Exp: "+nsExp.getMessage());
            return false;
        }
    }

    private Select getSelect(By locator){
        return new Select(getElement(locator));
    }

    public void doDropDownSelectByIndex(By locator,int index){
        getSelect(locator).selectByIndex(index);
    }

    public int doDropDownOptionsCount(By locator){
        return getSelect(locator).getOptions().size();
    }

    public List<String> getDropDownOptionsTextList(By locator){
        Select select = new Select(getElement(locator));
        List<WebElement> selectOptions=select.getOptions();
        List<String> optionsTextList = new ArrayList<>();

        for(WebElement e: selectOptions){
            String text= e.getText();
            optionsTextList.add(text);
        }
        return optionsTextList;
    }

    public void handlemultilocators4(By menu1,By menu2,By menu3,By menu4) throws InterruptedException{

        doClick(menu1);
        act.moveToElement(getElement(menu2)).perform();
        act.moveToElement(getElement(menu3)).perform();
        doClick(menu4);

    }

    public void sendKeysWithPause(By locator,String data,long pause) {

        char[] value = data.toCharArray();
        for (char e : value) {
            act.sendKeys(getElement(locator), String.valueOf(e))
                    .pause(pause)
                    .perform();
        }
    }

    public void doClear(By locator) {
    	getElement(locator).clear();
    }
    
    public void doSendKeys(By locator,String value) {
    	doClear(locator);
    	getElement(locator).sendKeys(value);
    }


    public void doClick(By locator){
        getElement(locator).click();
    }

    public void doClick(By locator,long timeOut){
        getElement(locator,timeOut).click();
    }

    ////////////////////////Wait Utils////////////////////////////////////

    public WebElement waitForElementPresence(By locator,long timeout){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementVisibility(By locator,long timeout){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String waitForTitleContains(String titleValue,long timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try{
            wait.until(ExpectedConditions.titleContains(titleValue));
            return driver.getTitle();
        }
        catch(TimeoutException timeoutExp){
            System.out.println("Timeout Exception is not found: "+timeoutExp);
            return null;
        }
    }

    public String waitForTitleIs(String titleValue,long timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try{
            wait.until(ExpectedConditions.titleIs(titleValue));
            return driver.getTitle();
        }
        catch(TimeoutException timeoutExp){
            System.out.println("Timeout Exception is not found: "+timeoutExp);
            return null;
        }
    }

    public String waitForURLContains(String urlValue,long timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try{
            wait.until(ExpectedConditions.urlContains(urlValue));
        }
        catch(TimeoutException timeoutExp){
            System.out.println("Timeout Exception is not found: "+timeoutExp);
        }
        return driver.getCurrentUrl();
    }

    public String waitForURLToBe(String urlValue,long timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try{
            wait.until(ExpectedConditions.urlToBe(urlValue));
        }
        catch(TimeoutException timeoutExp){
            System.out.println("Timeout Exception is not found: "+timeoutExp);
        }
        return driver.getCurrentUrl();
    }

    public void waitForElementClickable(By locator,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private Alert waitForAlert(long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        Alert alert=wait.until(ExpectedConditions.alertIsPresent());
        return alert;
    }

    public String waitForAlertAndAccept(long timeout){
        Alert alert=waitForAlert(timeout);
        String text=alert.getText();
        alert.accept();
        return text;
    }

    public String waitForAlertAndDismiss(long timeout){
        Alert alert=waitForAlert(timeout);
        String text=alert.getText();
        alert.dismiss();
        return text;
    }

    public String waitForAlertAndEnterValue(String textForAlert,long timeout){
        Alert alert=waitForAlert(timeout);
        String text=alert.getText();
        alert.sendKeys(textForAlert);
        alert.accept();
        return text;
    }

    public void waitForFrameAndSwitchToIt(By frameLocator,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameAndSwitchToIt(int frameIndex,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToIt(String frameIDOrName,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
    }

    public void waitForFrameAndSwitchToIt(WebElement frameWebElement, long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameWebElement));
    }


    public List<WebElement> waitForPresenceOfAllElements(By locator,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitForVisibilityOfAllElements(By locator,long timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
        	return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));	
        }
        catch(TimeoutException exp) {
        	return Collections.emptyList();
        }
    }

    public WebElement WaitForElementVisibleWithFluentWait(By locator,long TimeOut,long PollingTime){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TimeOut))
                .pollingEvery(Duration.ofSeconds(PollingTime))
                .ignoring(NoSuchElementException.class)
                .withMessage("No element found !!!");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

    public WebElement waitForElementWithFluentWaitWithoutBy(WebElement element,long timeout,long pollingTime){

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        wait.pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .withMessage("Fluent wait running");

        return wait.until(ExpectedConditions.visibilityOf(element));

    }
    
    public String doElementGetText(By locator) {
    	return getElement(locator).getText();
    }
	
}
