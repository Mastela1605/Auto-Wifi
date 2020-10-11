package com.mastela.wifi.autopassw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mastela.wifi.autopassw.config.ConfigSpring;
import com.mastela.wifi.autopassw.generadorclave.GeneradorClaveWifi;
import com.mastela.wifi.autopassw.mail.GestorMailService;
import com.mastela.wifi.autopassw.mail.GestorMailServiceImpl;
import com.mastela.wifi.autopassw.router.GestorRouterService;
import com.mastela.wifi.autopassw.router.GestorRouterServiceImpl;

@SpringBootApplication
public class Main
{
	public static void main ( String[ ] args )
	{
		try ( AnnotationConfigApplicationContext contexto = new AnnotationConfigApplicationContext ( ConfigSpring.class ) )
		{
			String claveWifi;

			GestorRouterService gestorRouter = ( GestorRouterServiceImpl ) contexto.getBean ( "gestorRouter" );
			GestorMailService gestorMail = ( GestorMailServiceImpl ) contexto.getBean ( "gestorMail" );

			GeneradorClaveWifi generador = new GeneradorClaveWifi ( );

			claveWifi = generador.passwordWifi ( );
			gestorRouter.modificacionWifi ( gestorRouter.loggin ( ), claveWifi );
			gestorMail.envioCorreo ( "Password generado por AutoWifi", claveWifi );

		}

	}

}
