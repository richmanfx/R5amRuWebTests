package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class StartPage extends BasePage {

    /**
     * Проверить отображение стартовой страницы
     */
    @Override
    public void checkPageShow() {
        String logo = "//h1[contains(string(),'R5AM')]";
        try {
            $(By.xpath(logo)).shouldBe(visible);
            log.debug("Стартовая страница удачно отобразилась");
        } catch (NoSuchElementException ex) {
            log.error("Стартовая страница не отобразилась: '{}'", ex.toString());
            assert false;
        }

    }

}
