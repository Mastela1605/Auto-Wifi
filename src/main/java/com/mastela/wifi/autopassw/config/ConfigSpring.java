package com.mastela.wifi.autopassw.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mastela.wifi.autopassw.mail.GestorMailService;
import com.mastela.wifi.autopassw.mail.GestorMailServiceImpl;
import com.mastela.wifi.autopassw.router.GestorRouterService;
import com.mastela.wifi.autopassw.router.GestorRouterServiceImpl;

@Configuration
@PropertySource ( "classpath:mailProperties.properties" )
@PropertySource ( "classpath:routerProperties.properties" )
public class ConfigSpring
{
	/**
	 * Protocolo Smtp a usar
	 */
	@Value ( "${mail.protocoloSmtp}" )
	private String protocoloSmtp;

	/**
	 * Puerto
	 */
	@Value ( "${mail.puerto}" )
	private String puertoEnvio;

	/**
	 * Uso del Tls (Booleano)
	 */
	@Value ( "${mail.tls.activo}" )
	private String tlsActivo;

	/**
	 * Es necesaria autenticación (Booleano)
	 */
	@Value ( "${mail.autenticacion}" )
	private String autenticacion;

	@Value ( "${router.username}" )
	private String userName;
	@Value ( "${router.password}" )
	private String passWord;
	@Value ( "${router.linkwifi}" )
	private String linkWifi;

	/**
	 * Bean que crea la configuracion del correo
	 * 
	 * @return java.util.Properties que contiene la configuracion a usar del correo
	 */
	@Bean ( name = "properties" )
	public Properties crearProperties ( )
	{

		Properties properties = new Properties ( );
		properties.put ( "mail.smtp.auth", autenticacion );
		properties.put ( "mail.smtp.starttls.enable", tlsActivo );
		properties.put ( "mail.smtp.host", protocoloSmtp );
		properties.put ( "mail.smtp.port", puertoEnvio );

		return properties;
	}

	/**
	 * Creación del bean encargado de enviar mails
	 */
	@Bean ( name = "gestorMail" )
	public GestorMailService gestorMail ( )
	{
		return new GestorMailServiceImpl ( );
	}

	@Bean ( name = "gestorRouter" )
	public GestorRouterService gestorRouter ( )
	{
		return new GestorRouterServiceImpl ( );
	}
}
