package ru.netology.test;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.*;


public class CardDeliveryTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCreateOrderToDelivery() {
        $("[data-test-id = city] input").setValue(DataGenerator.Registration.generate().getCity());
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.CONTROL + "A"), Keys.DELETE);
        $("[data-test-id = date] input").setValue(DataGenerator.Registration.getDayVisit(3));
        $("[data-test-id = name] input").setValue(DataGenerator.Registration.generate().getName());
        $("[data-test-id = phone] input").setValue(DataGenerator.Registration.generate().getPhone());
        $("[data-test-id = agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id = success-notification]")
                .shouldBe(visible, ofMillis(15000))
                .shouldHave(text(DataGenerator.Registration.getDayVisit(3)));
    }

    @Test
    void shouldCreateOrderToDeliveryWithReplanning() {
        $("[data-test-id = city] input").setValue(DataGenerator.Registration.generate().getCity());
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.CONTROL + "A"), Keys.DELETE);
        $("[data-test-id = date] input").setValue(DataGenerator.Registration.getDayVisit(3));
        $("[data-test-id = name] input").setValue(DataGenerator.Registration.generate().getName());
        $("[data-test-id = phone] input").setValue(DataGenerator.Registration.generate().getPhone());
        $("[data-test-id = agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id = success-notification]")
                .shouldBe(visible, ofMillis(15000))
                .shouldHave(text(DataGenerator.Registration.getDayVisit(3)));
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.CONTROL + "A"), Keys.DELETE);
        $("[data-test-id = date] input").setValue(DataGenerator.Registration.getDayVisit(7));
        $(withText("Запланировать")).click();
        $("[data-test-id = replan-notification]").shouldBe(visible, ofMillis(15000));
        $("[data-test-id = replan-notification] .button").click();
        $("[data-test-id = success-notification]")
                .shouldBe(visible)
                .shouldHave(text(DataGenerator.Registration.getDayVisit(7)));
    }

}
