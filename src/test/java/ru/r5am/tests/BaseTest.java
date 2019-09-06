package ru.r5am.tests;


import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import org.springframework.context.ApplicationContext;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.r5am.config.AppConfig;
import ru.r5am.utils.FlashElement;


public class BaseTest {

    protected BaseTest() {}

    private static final Logger log = LogManager.getLogger();
    private static AppConfig config = ConfigFactory.create(AppConfig.class);
    protected ApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");

    /**
     * Сконфигурировать Selenide
     */
    @BeforeSuite
    public static void selenideSetUp() {

        log.info(String.format("Start method: %s", Thread.currentThread().getStackTrace()[1].getMethodName()));

        Configuration.timeout = 1000 * config.failTestTimeout();
        Configuration.browser = config.browser();
        Configuration.browserSize = config.browserSize();
        Configuration.screenshots = false;      // Не делать скриншоты средствами Selenide

        // Для работы через Selenium Grid
        log.info("remoteBrowserFlag: {}", config.remoteBrowser());
        if (config.remoteBrowser()) {

            String remoteSeleniumHub = config.remoteSeleniumHub();
            log.info("Remote Selenium Hub: {}", config.remoteSeleniumHub());

            String remoteSeleniumHubPort = config.remoteSeleniumHubPort();
            log.info("Remote port of Selenium Hub: {}", remoteSeleniumHubPort);

            Configuration.remote = String.format("http://%s:%s/wd/hub", remoteSeleniumHub, remoteSeleniumHubPort);
            Configuration.browserCapabilities.setCapability("enableVNC", true);
        }
    }

    /**
     * TODO: Временно!!!
     */
    @BeforeSuite(dependsOnMethods="setHighlighting")
    public static void openSite() {
        log.info(String.format("Start method: %s", Thread.currentThread().getStackTrace()[1].getMethodName()));
        open(config.testUrl());
    }

    /**
     * Смотреть после тестов на результат глазами
     */
    @AfterSuite(alwaysRun = true)
    public static void withMansEyesView() {
        log.info(String.format("Start method: %s", Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (!config.ciServerFlag()) {
            int sleepTime = config.eyesViewTimeout();
            log.info("Смотрим глазами на результат '{}' секунд", sleepTime);
            sleep(sleepTime * 1000L);        // в миллисекундах
        }
    }

    /**
     * Подсветка элементов
     */
    @BeforeSuite
    public static void setHighlighting() {

        log.info(String.format("Start method: %s", Thread.currentThread().getStackTrace()[1].getMethodName()));

        int uiActionInterval = config.uiActionInterval();
        String uiFlashColor = config.uiFlashColor();
        int uiFlashCount = config.uiFlashCount();
        if (config.uiFlashSwitch()) {
            addListener(new FlashElement(uiFlashColor, uiFlashCount, uiActionInterval, TimeUnit.MILLISECONDS)
            );
        }
    }

}
