package com.mastela.wifi.autopassw.router;

import org.openqa.selenium.WebDriver;

public interface GestorRouterService
{

	WebDriver loggin ( );

	void modificacionWifi ( WebDriver paginaWeb, String generatedString );

}