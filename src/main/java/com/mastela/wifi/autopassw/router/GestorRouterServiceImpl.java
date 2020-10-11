package com.mastela.wifi.autopassw.router;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource ( "classpath:router.properties" )
public class GestorRouterServiceImpl
		implements GestorRouterService

{
	@Value ( "${router.username}" )
	private String userName;
	@Value ( "${router.password}" )
	private String passWord;
	@Value ( "${router.linkwifi}" )
	private String linkWifi;

	@Override
	public WebDriver loggin ( )
	{

		System.setProperty ( "webdriver.gecko.driver", "C:\\dev\\geckodriver.exe" );
		WebDriver driver = new FirefoxDriver ( );

		driver.get ( "http://192.168.1.1" );
		WebElement username = driver.findElement ( By.name ( "Username" ) );
		username.sendKeys ( userName );
		WebElement password = driver.findElement ( By.name ( "Password" ) );
		password.sendKeys ( passWord );
		WebElement loginBotton = driver.findElement ( By.id ( "LoginId" ) );
		loginBotton.click ( );

		return driver;
	}

	@Override
	public void modificacionWifi ( WebDriver paginaWeb, String generatedString )
	{

		paginaWeb.get ( linkWifi );

		WebElement passWifi = paginaWeb.findElement ( By.id ( "Frm_KeyPassphrase" ) );
		passWifi.clear ( );
		passWifi.sendKeys ( generatedString );
		WebElement submit = paginaWeb.findElement ( By.id ( "Btn_Submit" ) );
		submit.click ( );
		paginaWeb.close ( );
	}
}
