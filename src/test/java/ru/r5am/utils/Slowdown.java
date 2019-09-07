package ru.r5am.utils;


import org.openqa.selenium.By;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;


/**
 * Listener замедления перед любым действием
 */
public class Slowdown extends AbstractWebDriverEventListener {

    private final long timeout;
    private final Logger log = LogManager.getLogger();

    public Slowdown(long timeout, TimeUnit unit) {
        this.timeout = TimeUnit.MILLISECONDS.convert(Math.max(0, timeout), unit);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log.debug(String.format("Start method: %s", Thread.currentThread().getStackTrace()[1].getMethodName()));
        sleep();
    }


    @Override
    public void beforeNavigateBack(WebDriver driver) {
        sleep();
    }


    @Override
    public void beforeNavigateForward(WebDriver driver) {
        sleep();
    }


    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        sleep();
    }


    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        sleep();
    }


    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        sleep();
    }


    @Override
    public void beforeScript(String script, WebDriver driver) {
        sleep();
    }


    private void sleep() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ex) {
            log.error("Exception: {}", ex.toString());
        }
    }

}
