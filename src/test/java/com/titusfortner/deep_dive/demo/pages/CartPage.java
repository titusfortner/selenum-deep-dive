package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class CartPage extends BasePage {
    private final By checkoutButton = By.cssSelector("button[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void checkout() {
        click(checkoutButton);
    }

    public void removeItemSuccessfully(Product product) {
        HeaderSection headerSection = new HeaderSection(driver);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeItem(product);

        try {
            wait.until((Function<WebDriver, Object>) driver -> {
                return expected.equals(headerSection.getNumberItemsInCart());
            });
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }

    private void removeItem(Product product) {
        String cssSelector = "button[data-test='remove-" + product.getId() + "']";
        driver.findElement(By.cssSelector(cssSelector)).click();
    }
}
