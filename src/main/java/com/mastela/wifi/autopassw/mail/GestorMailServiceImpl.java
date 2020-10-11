package com.mastela.wifi.autopassw.mail;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource ( "classpath:mail.properties" )
@Service
@Configurable
public class GestorMailServiceImpl
		implements GestorMailService
{
	/**
	 * Correo del remitente
	 */
	@Value ( "${mail.remitente}" )
	private String correoRemitente;

	/**
	 * Contraseña del remitente
	 */
	@Value ( "${mail.pass}" )
	private String contrasennaRemitente;

	/**
	 * Mail del destinatario
	 */
	@Value ( "${mail.destinatario}" )
	private String correoDestinatario;

	/**
	 * Bean de la configuracion del properties
	 */
	@Resource ( name = "properties" )
	private Properties configuracionProperties;

	@Override
	public void envioCorreo ( String asunto, String mensaje )
	{

		// Iniciamos la sesion del correo
		Session session = Session.getInstance ( configuracionProperties, autenticarCorreo ( ) );

		try
		{

			// Creamos el mensaje
			Message message = new MimeMessage ( session );
			// Seteamos el remitente
			message.setFrom ( new InternetAddress ( correoRemitente ) );
			// Seteamos el destinatario o destinatarios
			message.setRecipients ( Message.RecipientType.TO, InternetAddress.parse ( correoDestinatario ) );
			// Seteamos el asunto
			message.setSubject ( asunto );

			// Por si quieres adjuntar ficheros...
			// DataSource fuente = new FileDataSource(adjunto);
			// texto.setDataHandler(new DataHandler(fuente));
			// texto.setFileName(adjunto);

			// Creamos las partes del correo
			MimeMultipart multiParte = new MimeMultipart ( );

			// Creamos el cuerpo del mensaje
			BodyPart texto = new MimeBodyPart ( );
			// Seteamos el cuerpo del mensaje
			texto.setText ( mensaje );
			// Seteamos el cuerpo que contiene el mensaje
			multiParte.addBodyPart ( texto );
			// Lo añadimos al correo
			message.setContent ( multiParte );

			// Lo Enviamos
			Transport.send ( message );

		}
		catch ( MessagingException exception )
		{
			exception.printStackTrace ( );
		}
	}

	/**
	 * Método usado para la autenticación del correo
	 * 
	 * @return javax.mail.Authenticator objeto para iniciar sesion en el correo
	 */
	private Authenticator autenticarCorreo ( )
	{

		return new Authenticator ( )
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication ( )
			{
				return new PasswordAuthentication ( correoRemitente, contrasennaRemitente );
			}
		};

	}
}
