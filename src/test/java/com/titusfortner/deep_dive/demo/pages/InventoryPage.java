package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {
    public static final String URL = "https://www.saucedemo.com/inventory.html";
    private final WebDriver driver;

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By item1Link = By.id("item_1_title_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By shoppingCartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnPage() {
        return URL.equals(driver.getCurrentUrl());
    }

    public void logOut() {
        driver.findElement(menuButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(logoutLink).click();
    }

    public ProductPage viewBoltTShirtProduct() {
        driver.findElement(item1Link).click();
        return new ProductPage(driver);
    }

    public void addItem(Product product) {
        String cssSelector = "button[data-test='add-to-cart-" + product.getId() + "']";
        driver.findElement(By.cssSelector(cssSelector)).click();
    }

    public void removeItem(Product product) {
        String cssSelector = "button[data-test='remove-" + product.getId() + "']";
        driver.findElement(By.cssSelector(cssSelector)).click();
    }

    public Integer getNumberItemsInCart() {
        List<WebElement> cartNumberElements = driver.findElements(shoppingCartBadge);
        if (cartNumberElements.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(cartNumberElements.get(0).getText());
        }
    }

    public CartPage goToCart() {
        driver.findElement(shoppingCartLink).click();
        return new CartPage(driver);
    }
}