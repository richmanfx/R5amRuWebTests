package ru.r5am.utils;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Locatable;
import static com.codeborne.selenide.Selenide.sleep;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;


/**
 * Листенер подсветки web-элемента
 */
public class FlashElement extends AbstractWebDriverEventListener {

    final private long interval;
    private final int count;
    private final String color;

    public FlashElement(String color, int count, long interval, TimeUnit unit) {
        this.color = color;
        this.count = count;
        this.interval = TimeUnit.MILLISECONDS.convert(Math.max(0, interval), unit);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        if (element != null) {        // Исключение для $$ в Selenide
            ((Locatable) element).getCoordinates().inViewPort();        // Скролить страницу до элемента
            flash(element, driver);
        }
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        flash(element, driver);
    }


    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        flash(element, driver);
    }

    /**
     * Мигать цветом фона веб-элемента
     * @param element Веб-элемент селениумный
     * @param driver Веб-драйвер
     */
    private void flash(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String bgcolor = element.getCssValue("backgroundColor");
        for (int i = 0; i < count; i++) {
            changeColor(color, element, js);
            changeColor(bgcolor, element, js);
        }
    }

    /**
     * Изменить цвет фона веб-элемента
     */
    private void changeColor(String color, WebElement element, JavascriptExecutor js) {
        js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
        sleep(interval);
    }

}
