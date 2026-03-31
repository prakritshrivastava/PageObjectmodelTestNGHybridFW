package com.opencart.qa.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {


    private WebDriver driver;


    // get driver from user who is using the class
    public JavaScriptUtil(WebDriver driver){
        this.driver=driver;
    }
    
    public void flash(WebElement element) {
    	String bgColor = element.getCssValue("backgroundColor");
    	for(int i=0;i<7;i++) {
    		changeColor("rgb(0,200,0)",element);
    		changeColor(bgColor,element);
    	}
    }
    
    private void changeColor(String color, WebElement element) {
    	JavascriptExecutor js = ((JavascriptExecutor)driver);
    	js.executeScript("arguments[0].style.backgroundColor='"+color+"'", element);
    	try {
    		Thread.sleep(20);
    	}
    	catch(InterruptedException exp){
    		System.out.println("Interrupted");
    	}
    } 
    

    public String jsTitle(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        return jsExec.executeScript("return document.title;").toString();
    }

    public String jsURL(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        return jsExec.executeScript("return document.URL;").toString();
    }

    public void jsAlert(String message){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("alert('"+message+"');");
        try{
            Thread.sleep(3000);
            driver.switchTo().alert().accept();
        }
        catch (InterruptedException intExp){
            System.out.println("Exception: "+intExp.getMessage());
        }
    }

    public void jsRefresh(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("history.get(0);");
    }


    public void jsForwardPage(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("history.get(1);");
    }

    public void jsBackwardPage(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("history.get(-1);");
    }

    public void jsSpecificPage(int pageNumber){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("history.get('"+pageNumber+"');");
    }

    public void jsGetAllTexts(int pageNumber){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("document.documentElement.innerText;");
    }

    public void jsScrollToBottom(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void jsScrollToCertainBottom(String height){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("window.scrollTo(0,"+height+")");
    }


	
}
