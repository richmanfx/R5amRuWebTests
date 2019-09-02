package ru.r5am.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage {

    public abstract void checkPageShow();
    static final Logger log = LogManager.getLogger();

}
