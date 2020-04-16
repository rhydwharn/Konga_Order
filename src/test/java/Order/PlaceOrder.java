package Order;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class PlaceOrder {
    private WebDriver driver;

    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/resources/chromedriver.exe");

        //Initiating the chromedriver application
        driver = new ChromeDriver();

        //The test URL
        driver.get("https://konga.com");

        //Wait period
        Thread.sleep(5000);

        //Display the web title on the debug window
        System.out.println(driver.getTitle());

        //Wait time before every step is performed
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //Maximize the window
        driver.manage().window().maximize();

        Actions builder = new Actions(driver);

        //Target the link for Login/Signup
        driver.findElement(By.linkText("Login / Signup")).click();

        //Enter the user's username
        driver.findElement(By.id("username")).sendKeys("*************");

        //Enter the user's password
        driver.findElement(By.id("password")).sendKeys("**********");

        //Click on Login button
        driver.findElement(By.xpath("//button[contains(.,'Login')]")).click();

        //Wait period before going to next operation
        Thread.sleep(5000);

        //Get the Computer and Accessories menu
        WebElement Comp_Accessories = driver.findElement(By.linkText("Computers and Accessories"));

        //Mouse over the Computer Accessories menu
        builder.moveToElement(Comp_Accessories).build().perform();

        //Click on Computer Peripherals and wait
        driver.findElement(By.xpath("//a[@href='/category/peripherals-5238']")).click();
        Thread.sleep(3000);

        //Enter the search item in the search field
        driver.findElement(By.cssSelector(".f6ed2_25oVd #jsSearchInput")).sendKeys("Flash drive");

        //Press enter key after typing the search item
        driver.findElement(By.cssSelector(".f6ed2_25oVd #jsSearchInput")).sendKeys(Keys.ENTER);

        //Click the item to be purchased
        driver.findElement(By.xpath("//h3[contains(text(),'SanDisk 32gb Cruzer Blade Usb Flash D...')]")).click();

        //Click on BUY NOW after the item has displayed
        driver.findElement(By.xpath("//div[contains(@class,'_7bc9f_Ef1Zw')]//button[@class='_0a08a_3czMG _6d187_pzjfk'][contains(text(),'Buy Now')]")).click();

        //Click on the increase button (+)
        driver.findElement(By.xpath("//div[contains(@class,'_99e8b_GvAfB')]//form[contains(@class,'d4e80_37RCm')]//div[contains(@class,'c1256_3sLN7')]//button[contains(text(),'+')]")).click();
        driver.findElement(By.xpath("//button[contains(@name,'increment')]")).click();
        driver.findElement(By.xpath("//button[contains(@name,'increment')]")).click();

        //Click on check out
        driver.findElement(By.linkText("Continue to Checkout")).click();

        //Check if there is a delivery address already selected
        String text = "Select Delivery Address";
        String bodyText = driver.findElement(By.linkText("Select Delivery Address")).getText();
        if(bodyText.contains(text) == true){
            //Select the delivery address that already exist on user account
            driver.findElement(By.linkText("Select Delivery Address")).click();

            //Pick address from the list
            driver.findElement(By.xpath("//html//section[@id='app-content-wrapper']/div[@class='c6dfe_HidJc']/section[@class='_0863a_3x99A']/section//form[@method='POST']/button[@name='selectDeliveryAddress']")).click();

            //Select Use this address to continue shopping
            driver.findElement(By.linkText("Use this Address")).click();

            //Select the PAY NOW button
            driver.findElement(By.xpath("//button[@name='selectPaymentMethod']")).click();

            //Select the complete order button
            driver.findElement(By.xpath("//button[@name='placeOrder']")).click();
        }

        //Operation to perform if there is no pre-selected delivery date
        else {

            //Select the PAY NOW button
            driver.findElement(By.xpath("//button[@name='selectPaymentMethod']")).click();

            //Select the complete order button
            driver.findElement(By.xpath("//button[@name='placeOrder']")).click();
        }

        //Select the iframe for selecting the card payment option
        WebElement iframe = driver.findElement(By.xpath("//iframe[@id='kpg-frame-component']"));
        driver.switchTo().frame(iframe);

        //Confirm the iframe targetted exists
        int i = driver.findElements(By.xpath("//div[@id='channel-template']/div[2]/div[1]/div[1]/button[1]")).size();
        if(i == 0)
            System.out.println("Element is not present");
        else
            System.out.println("Element is present");

        //Click on the first item on the list (Card payment option)
        driver.findElement(By.xpath("//div[@id='channel-template']/div[2]/div[1]/div[1]/button[1]")).click();

        //Enter the card details
        driver.findElement(By.xpath("//input[@id='card-number']")).sendKeys("53393234176232134");

        //Enter card PIN
        driver.findElement(By.xpath("(//input[@class='date input_class'])[1]")).sendKeys("1222");

        //Enter card CVV
        driver.findElement(By.xpath("(//input[@class='cvv input_class'])[1]")).sendKeys("213");

        //Display the Error of Invalid Card number label after entering a wrong card detail in debug console
        System.out.println(driver.findElement(By.xpath("//label[@id='card-number_unhappy']")));

        //Use Dialog box to display the error of card
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setVisible(false);

        JOptionPane.showMessageDialog(frame,
                driver.findElement(By.xpath("//label[@id='card-number_unhappy']")).getText());


        driver.quit();
    }

    public  static void main (String args[]) throws InterruptedException {
        PlaceOrder RunOrder = new PlaceOrder();
        RunOrder.setUp();
    }
}
