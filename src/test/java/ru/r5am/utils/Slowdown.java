package ru.r5am.utils;


import org.openqa.selenium.By;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.sleep;
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
        sleep(timeout);
    }


    @Override
    public void beforeNavigateBack(WebDriver driver) {
        sleep(timeout);
    }


    @Override
    public void beforeNavigateForward(WebDriver driver) {
        sleep(timeout);
    }


    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        sleep(timeout);
    }


    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        sleep(timeout);
    }


    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        sleep(timeout);
    }


    @Override
    public void beforeScript(String script, WebDriver driver) {
        sleep(timeout);
    }

}
