package Order;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class PlaceOrder {
    private WebDriver driver;
    private WebElement element;

    public void setUp() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();

        //Launch chrome driver pending on type of users's Operating System
        if(os.contains("mac")){
            System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        }
        else{
            System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        }

        //initialize the Chrome driver object
        driver = new ChromeDriver();

        //maximize the window
        driver.manage().window().maximize();

        //visit base URL page
        driver.get("https://www.konga.com/");

        Thread.sleep(5000);

        Actions builder = new Actions(driver);

        //Click on Login/Signup
        driver.findElement(By.linkText("Login / Signup")).click();

        //Enter the user's username
        driver.findElement(By.id("username")).sendKeys("");

        //Enter the user's password
        driver.findElement(By.id("password")).sendKeys("");

        //Click on Login button
        driver.findElement(By.xpath("//button[contains(.,'Login')]")).click();

        //Pause period before going to next operation
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Get the Computer and Accessories menu
        WebElement Comp_Accessories = driver.findElement(By.linkText("Computers and Accessories"));

        //Display in debug window (Login Successful)
        System.out.println("Login Successful");

        //Mouse over the Computer Accessories menu
        builder.moveToElement(Comp_Accessories).build().perform();
        Thread.sleep(3000);

        //Click on Computer Peripherals and wait
        driver.findElement(By.linkText("Computer Peripherals")).click();
        //Pause period before going to next operation
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //Display in debug window (Category > SubCategory Selected)
        System.out.println("Category > SubCategory Selected");

        //search for an item #jsSearchInput
        driver.findElement(By.xpath("/html/body/div[1]/div/section/div[2]/nav/div[1]/div/div[2]/div/form/div[1]/input")).sendKeys("Samsung Phones");

        //click on the search button
        driver.findElement(By.cssSelector("#app-content-wrapper > div.e5dc4_DR8xw > nav > div._2d4bb_2rbWX > div > div._63857_1TmYU > div > form > button")).click();

        //Pause period before going to next operation
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Display in debug window (Item searched)
        System.out.println("Item successfully searched");

        //Click item to add to cart
        try {
            WebElement addToCart =  driver.findElement(By.cssSelector("#mainContent > section._9cac3_2I9l4 > section > section > section > section > ul > li:nth-child(1) > div > div > div._4941f_1HCZm > form > div._2aac2_3bwnD._549f7_zvZ8u._49c0c_3Cv2D._977c5_2vBMq > button"));
            addToCart.click();
            //b49ee_2pjyI
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            System.out.println("element becomes state");
            WebElement addToCart =  driver.findElement(By.cssSelector("#mainContent > section._9cac3_2I9l4 > section > section > section > section > ul > li:nth-child(1) > div > div > div._4941f_1HCZm > form > div._2aac2_3bwnD._549f7_zvZ8u._49c0c_3Cv2D._977c5_2vBMq > button"));
            addToCart.click();
        }

        Thread.sleep(3000);

        //click to view cart preview
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement addToCart =  driver.findElement(By.cssSelector("#app-content-wrapper > div.e5dc4_DR8xw > nav > div._2d4bb_2rbWX > div > div.e5d46_2l87X._16536_xxIKG > a"));
        addToCart.click();

        //Display in debug window (Item added to cart)
        System.out.println("Item added to cart");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("get the cart increment button");
        //get the cart increment button
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement incCart =  driver.findElement(By.cssSelector("#js-cart-items-wrapper > div > div > div.ed23a_3OozF > div.bb31e_ax8ka > form > div > div > button.c4079_DW1vB"));


        System.out.println("Start increment button");
        //increment cart item
        for(int i=0; i<3; i++){
            incCart.click();
            Thread.sleep(5000);
        }

        System.out.println("Done increment button");

        //checkout order

        Thread.sleep(3000);

        //checking out the order
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement checkOut =  driver.findElement(By.cssSelector("#app-content-wrapper > div.c6dfe_HidJc > section > section > aside > div.fb90d_2mSyi > div > div._2aac2_3bwnD._841f1_1RZK9 > button"));
        checkOut.click();
        System.out.println("checkout order");

        //Check if there is a delivery address already selected
        String text = "Select Delivery Address";
        String bodyText = driver.findElement(By.linkText("Select Delivery Address")).getText();
        System.out.println(bodyText);
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

        //Display in debug window (Address selected)
        System.out.println("Address selected");

        //Select the iframe for selecting the card payment option
        WebElement iframe = driver.findElement(By.xpath("//iframe[@id='kpg-frame-component']"));
        driver.switchTo().frame(iframe);

        //Confirm the iframe targetted exists
        int i = driver.findElements(By.xpath("//div[@id='channel-template']/div[2]/div[1]/div[1]/button[1]")).size();
        if(i == 0)
            System.out.println("Iframe element is not present");
        else
            System.out.println("Iframe element is present");

        //Click on the first item on the list (Card payment option)
        driver.findElement(By.xpath("//div[@id='channel-template']/div[2]/div[1]/div[1]/button[1]")).click();

        //Display in debug window (Item added to cart)
        System.out.println("About entering card details");

        //Enter the card details
        driver.findElement(By.xpath("//input[@id='card-number']")).sendKeys("53393234176232134");

        //Enter card PIN
        driver.findElement(By.xpath("(//input[@class='date input_class'])[1]")).sendKeys("1222");

        //Enter card CVV
        driver.findElement(By.xpath("(//input[@class='cvv input_class'])[1]")).sendKeys("213");

        //Click on PIN input
        driver.findElement(By.id("card-pin-new")).click();

        //Clikc on PIN to be inputed
        driver.findElement(By.xpath("(//button[text()='1'])[1]")).click();
        driver.findElement(By.xpath("(//button[text()='3'])[1]")).click();
        driver.findElement(By.xpath("(//button[text()='6'])[1]")).click();
        driver.findElement(By.xpath("(//button[text()='0'])[1]")).click();

        //Display in debug window (Item added to cart)
        System.out.println("Done entering card details");

        //Display the Error of Invalid Card number label after entering a wrong card detail in debug console
        System.out.println(driver.findElement(By.xpath("//label[@id='card-number_unhappy']")).getText());

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

    public static void main(String args[]) throws InterruptedException {
        PlaceOrder test= new PlaceOrder();
        test.setUp();

    }
    }

