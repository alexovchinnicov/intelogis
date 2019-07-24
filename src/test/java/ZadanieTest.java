import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ZadanieTest {

private static final String String = null;
private static String DefaultURL = System.getProperty("seleniumURL");
private static String DefaultUname = System.getProperty("seleniumUname");
private static String DefaultUpass = System.getProperty("seleniumUpass");

    @BeforeClass/* Метод, выполняющийся перед началом тест-сьюта */
    public void begin() {
        com.codeborne.selenide.Configuration.browser = "chrome";      //браузер для тестов
        com.codeborne.selenide.Configuration.timeout = 30000;         //максимальный интервал ожидания вебэлементов в милисекундах
        com.codeborne.selenide.Configuration.savePageSource = false;  //не сохранять дополнительные настройки
        ChromeOptions options = new ChromeOptions();  //создать обьект для установки опций браузера хром
        options.addArguments("--disable-infobars");   //убрать в браузере полосу infobars
        options.addArguments("--disable-dev-tools");  //отключить в браузере dev-tools
        WebDriver myWebDriver = new ChromeDriver(options);  //создать вебдрайвер с  указанными выше опциями
        WebDriverRunner.setWebDriver(myWebDriver); //запуск браузера
        myWebDriver.manage().window().maximize();

    }

    @BeforeMethod/* Метод, выполняющийся перед началом каждого теста */
    public void startTest(final Method method) {
        System.out.println("\n>>> TEST " + method.getName() + " <<<");
    }

    @AfterMethod
    public void endTest(ITestResult testResult) {
        if (testResult.isSuccess()) System.out.println(">>> OK <<<\n");
        else System.out.println(">>> FAIL <<<\n");
    }

    @AfterClass
    public void tearDown() {
        getWebDriver().quit();
    }

    @Test(priority = 1)
    public void TestMy() {
//    	Подключиться к системе
    	open(DefaultURL);
//    	авторизоваться
    	setEmailField(DefaultUname);    	
    	setPasswordField(DefaultUpass);   
    	clickEnterButton();
//    	создать проект с именем Test_текущиеврея (вставить текущие время)    	
    	clickNewProject();    
    	nameNewProject();  
    	sleep(5);    	
//    	убедиться что открылась страница с проектом
    	System.out.println(openNewProject());
//    	получить ссылку на сгенерированный проект
    	System.out.println(getLinkNewProject());    	
    }    

    //    @Step("Set login field")
    public void setEmailField(String email) {
        $("#ils-auth-login").setValue(email);
//      System.out.println("Set \"" + email + "\" to email field");
    }

 //   @Step("Set password field")
    public void setPasswordField(String password) {
        $("#ils-auth-password").setValue(password);
//        System.out.println("Set password field");
    }

    public void clickEnterButton() {
        $("#ils-body > div > div > form > input").click();
    }
    
    public void clickNewProject() {
        $("#ils-body > div > div.ils-log-welcome-submit > button").click();        
    }
    
    public void nameNewProject() {
        Date currentDate = new Date();            
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String nameTest = "Test_"+ dateFormat.format( currentDate ); 
//        System.out.println("nameTest" + nameTest);
        $("#newProjectdialog > p:nth-child(1) > input[type=text]").click();     
        $("#newProjectdialog > p:nth-child(1) > input[type=text]").setValue(nameTest);  
        $("body > div.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1)").click();
    }
    
    public String   openNewProject() {
       if ($("#ils-body > div.ils-toolbar").isDisplayed()) {
    	   return "openNewProject";
       }
       else {
    	   return "Not openNewProject";
       }
    }
	
    public static void sleep(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public String  getLinkNewProject() {
    	String url = getWebDriver().getCurrentUrl();
    	System.out.println("url:   " + url); 
    return url;	
    }
}
