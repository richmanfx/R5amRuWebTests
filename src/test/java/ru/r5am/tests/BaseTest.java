package ru.r5am.tests;


import org.apache.logging.log4j.Logger;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import com.codeborne.selenide.Configuration;

import ru.r5am.config.AppConfig;

import static com.codeborne.selenide.Selenide.sleep;


public class BaseTest {

    BaseTest() {}

    private static final Logger log = LogManager.getLogger();
    private static AppConfig config = ConfigFactory.create(AppConfig.class);

    /**
     * Сконфигурировать Selenide
     */
    @BeforeSuite
    public static void selenideSetUp() {

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
     * Смотреть после тестов на результат глазами
     */
    @AfterSuite(alwaysRun = true)
    public static void withMansEyesView() {
        if (!config.ciServerFlag()) {
            int sleepTime = config.eyesViewTimeout();
            log.info("Смотрим глазами на результат '{}' секунд", sleepTime);
            sleep(sleepTime * 1000L);        // в миллисекундах
        }
    }
}
