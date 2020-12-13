// Omar Kanawati
package ca.sheridancollege.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailServiceImpl  {
  
    @Autowired
    public JavaMailSender emailSender;
 
    public void sendSimpleMessage(
      String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
    @Autowired
	private TemplateEngine templateEngine;

	public void sendMailWithInline(String title, String header, String messageBody, 
			String footer, String to, String subject)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context();
		ctx.setVariable("title", title);
		ctx.setVariable("header", header);
		ctx.setVariable("message", messageBody);
		ctx.setVariable("footer", footer);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		final MimeMessageHelper message = 
				new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
		message.setSubject(subject);
		message.setFrom("account@gmail.com");
		message.setTo(to);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine
				.process("emailTemplate.html", ctx);
		message.setText(htmlContent, true); // true = isHtml

		// Send mail
		this.emailSender.send(mimeMessage);
	}

}
