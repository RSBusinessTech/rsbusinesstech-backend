package com.rsbusinesstech.rsbusinesstech_backend.email.service;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.email.model.RentalPaymentReminderRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
//    @Autowired
//    private JavaMailSender mailSender;

    private final JavaMailSender rsBusinessTechMailSender;
    private final JavaMailSender lashMapBeautyStudioMailSender;

    public EmailService(@Qualifier("rsBusinessTechMailSender") JavaMailSender rsBusinessTechMailSender,
                        @Qualifier("lashMapBeautyStudioMailSender") JavaMailSender lashMapBeautyStudioMailSender) {
        this.rsBusinessTechMailSender = rsBusinessTechMailSender;
        this.lashMapBeautyStudioMailSender = lashMapBeautyStudioMailSender;
    }

    //method to send email to RS BusinessTech from "Contact" section.
    public void sendEmail(EmailRequest emailRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rsbusinesstech@gmail.com");                                                                     //setting "to".
        message.setSubject("Enquiry from Customer || "+emailRequest.getName() +" || "+emailRequest.getEmail());       //setting "subject".
        message.setText(
                "You have a new message from your website:\n\n" +                                                     //setting "body".
                 "Message:\n" + emailRequest.getMessage());
        message.setFrom("rsbusinesstech@gmail.com");                                                                  //setting "from".

//        mailSender.send(message);
        rsBusinessTechMailSender.send(message);
    }

    //method to send email to LashMapBeautyStudio from "Contact" section.
    public void sendEmailLashMapBeautyStudio(EmailRequest emailRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("lashmap.beautystudio@gmail.com");
        message.setSubject("Enquiry from Customer || "+emailRequest.getName() +" || "+emailRequest.getEmail());
        message.setText(
                "You have a new message from your website:\n\n" +
                        "Message:\n" + emailRequest.getMessage());
        message.setFrom("lashmap.beautystudio@gmail.com");

        lashMapBeautyStudioMailSender.send(message);
    }

    //method to send email to Tenant as a Payment Reminder from Property Management System (PMS).
    public void sendRentalPaymentReminderEmail(@Valid RentalPaymentReminderRequest rentalPaymentReminderRequest) throws MessagingException {
        MimeMessage mimeMessage = rsBusinessTechMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlTemplate = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Rental Payment Reminder</title>\n" +
                "</head>\n" +
                "<body style=\"margin:0; padding:0; background-color:#f4f6f8; font-family: Arial, sans-serif; color: #333333;\">\n" +
                "\n" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#f4f6f8;\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "\n" +
                "            <!-- Main container -->\n" +
                "            <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background:#ffffff; margin:0px auto; border-radius: 8px;\">\n" +
                "\n" +
                "                <!-- Header with Logo -->\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 20px; text-align: center; border-bottom: 2px solid #0a2c7d; background-color:#000;\">\n" +
                "                        <img src=\"https://res.cloudinary.com/divjzb0t3/image/upload/v1767110889/Logo__RS_BusinessTech_g0plfe.png\" alt=\"RS BusinessTech Logo\" height=\"50\" style=\"display: block; margin: 0 10px 10px 0;\" />\n" +
                "                        <h1 style=\"color: #FFD700; margin: 0; font-weight: bold;\">Rental Payment Reminder</h1>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- Body -->\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 30px; color: #333333; background-color:#f0eeee; font-size: 16px; line-height: 1.5;\">\n" +
                "\n" +
                "                        <p>Dear <strong>{{CUSTOMER_NAME}}</strong>,</p>\n" +
                "\n" +
                "                        <p>\n" +
                "                            This is a friendly reminder that your rental payment of <strong>RM {{RENTAL_AMOUNT}}</strong> for\n" +
                "                            <strong>{{PROPERTY_NAME}}</strong> has been due on <strong>{{RENTAL_START_DATE}}</strong> and is currently outstanding.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p>\n" +
                "                            Please arrange payment at your earliest convenience and pay before <strong>{{RENTAL_DUE_DATE}}</strong>.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p>\n" +
                "                            If you have already made the payment, kindly disregard this message.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p>\n" +
                "                            For any queries or assistance, please feel free to contact us..\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p>Thank you for your prompt attention.</p>\n" +
                "\n" +
                "                        <br/>\n" +
                "\n" +
                "                        <p>\n" +
                "                            Thanks & Best regards,<br/>\n" +
                "                            <strong>{{AGENT_NAME}}</strong><br/>\n" +
                "                            Mobile No: {{AGENT_MOBILE_NO}} <br/>\n" +
                "                            Email: {{AGENT_EMAIL_ID}} <br/>\n" +
                "                        </p>\n" +
                "\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- Footer -->\n" +
                "                <tr>\n" +
                "                    <td style=\"background:#000; padding: 15px; text-align:center; font-size: 12px; color: white;\">\n" +
                "                        © 2025 RS BusinessTech · All Rights Reserved<br/>\n" +
                "                        <a href=\"https://www.rsbusinesstech.com\" style=\"color: white; text-decoration: none;\">\uD83C\uDF10 www.rsbusinesstech.com</a>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "            </table>\n" +
                "\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";


        helper.setTo(rentalPaymentReminderRequest.getCustomerEmail());
        helper.setCc(rentalPaymentReminderRequest.getAgentEmail());
        helper.setSubject("Rental Payment Reminder – " + rentalPaymentReminderRequest.getPropertyName() +" - Due on "+ rentalPaymentReminderRequest.getRentalStartDate());

        String htmlContent = htmlTemplate
                .replace("{{CUSTOMER_NAME}}", rentalPaymentReminderRequest.getCustomerName())
                .replace("{{RENTAL_AMOUNT}}", rentalPaymentReminderRequest.getRentalAmount().toString())
                .replace("{{PROPERTY_NAME}}", rentalPaymentReminderRequest.getPropertyName())
                .replace("{{RENTAL_START_DATE}}", rentalPaymentReminderRequest.getRentalStartDate())
                .replace("{{RENTAL_DUE_DATE}}", rentalPaymentReminderRequest.getRentalDueDate())
                .replace("{{AGENT_NAME}}", rentalPaymentReminderRequest.getAgentName())
                .replace("{{AGENT_MOBILE_NO}}", rentalPaymentReminderRequest.getAgentMobileNo())
                .replace("{{AGENT_EMAIL_ID}}", rentalPaymentReminderRequest.getAgentEmail());

        helper.setText(htmlContent, true); // true = HTML
        helper.setFrom("rsbusinesstech@gmail.com");

        rsBusinessTechMailSender.send(mimeMessage);

    }
}
