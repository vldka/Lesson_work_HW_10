import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SelenideTest {
    private static final String REPOSITORY = "vldka/demoqa-test-30";

    @Test
    @DisplayName("Проверка на чистом Selenide")
    public void testIssueSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").sendKeys(REPOSITORY);
        $("#query-builder-test").submit();

        $(linkText(REPOSITORY)).click();
        $("#issues-tab").shouldHave(text("Issues"));
    }

    @Test
    @DisplayName("Проверка по Steps Lambda")
    public void testIssueSearchLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем главную страницу", () -> open("https://github.com"));
        step("Поиск " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").sendKeys(REPOSITORY);
            $("#query-builder-test").submit();
        });
        step("Клик репозитория " + REPOSITORY, () -> $(linkText(REPOSITORY)).click());
        step("Проверяем наличие вкладки Issue", () -> {
            $("#issues-tab").shouldHave(text("Issues"));
        });
    }

    @Test
    @DisplayName("Проверка по анотации @Steps")
    public void testIssueSearchAnnotationSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.shouldSeeIssueWith("Issue");
    }

}
