package ru.r5am.tests.smoke;


import org.testng.annotations.Test;

import ru.r5am.tests.BaseTest;
import ru.r5am.pageobjects.StartPage;


public class Smoke extends BaseTest {

    Smoke() {
        super();
    }

    @Test(description = "Проверка отображения стартовой страницы")
    public void startPageDisplayCheck() {
//        StartPage startPage = new StartPage();
        StartPage startPage = (StartPage) context.getBean("startPage");

        startPage.checkPageShow();
    }
}
