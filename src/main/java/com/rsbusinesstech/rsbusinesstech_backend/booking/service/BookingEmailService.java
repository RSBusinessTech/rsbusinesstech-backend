package com.rsbusinesstech.rsbusinesstech_backend.booking.service;

import ch.qos.logback.core.util.StringUtil;
import com.rsbusinesstech.rsbusinesstech_backend.booking.model.BookingRequest;
import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingEmailService
{
//    @Autowired
//    private JavaMailSender mailSender;

    private final JavaMailSender rsBusinessTechMailSender;
    private final JavaMailSender lashMapBeautyStudioMailSender;

    public BookingEmailService(@Qualifier("rsBusinessTechMailSender") JavaMailSender rsBusinessTechMailSender,
                               @Qualifier("lashMapBeautyStudioMailSender") JavaMailSender lashMapBeautyStudioMailSender) {
        this.rsBusinessTechMailSender = rsBusinessTechMailSender;
        this.lashMapBeautyStudioMailSender = lashMapBeautyStudioMailSender;
    }

    //method to send email to RS BusinessTech from "Book Appointment" section.
    public void sendBookingEmail(BookingRequest bookingRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rsbusinesstech@gmail.com");
        message.setSubject("Appointment booked by Customer || "+ bookingRequest.getName() + " || " + bookingRequest.getPhone()+ " || " + bookingRequest.getEmail());
        message.setText(
                "You have a new Appointment from your website:\n\n" +
                        "Name: " + bookingRequest.getName() + "\n"+
                        "Email: " + bookingRequest.getEmail() + "\n"+
                        "Phone: " + bookingRequest.getPhone() + "\n"+
                        "ServiceId: " + bookingRequest.getServiceId() + "\n"+
                        "Stylist: " + bookingRequest.getStylist() + "\n"+
                        "Date: " + bookingRequest.getDate() + "\n"+
                        "Time: " + bookingRequest.getTime());
        message.setFrom("rsbusinesstech@gmail.com");

        rsBusinessTechMailSender.send(message);
    }

    //method to send email to LashMapBeautyStudio from "Book Appointment" section.
    public void sendBookingEmailLashMapBeautyStudio(BookingRequest bookingRequest){
        SimpleMailMessage lashmapMessage = new SimpleMailMessage();
        SimpleMailMessage userMessage = new SimpleMailMessage();

        //sending mail to LashMapBeautyStudio/RS BusinessTech.
        lashmapMessage.setTo("lashmap.beautystudio@gmail.com","rsbusinesstech@gmail.com");
        lashmapMessage.setSubject("New Appointment Booked || "+ bookingRequest.getDate() + " || " + bookingRequest.getTime());
        lashmapMessage.setText(
                        "Hello LashMap Team,\n\n" +

                        "A new appointment has been booked. Please find the details below:\n\n" +

                        "Name: " + bookingRequest.getName() + "\n"+
                        "Email: " + bookingRequest.getEmail() + "\n"+
                        "Phone: " + bookingRequest.getPhone() + "\n"+
                        "Service: " + bookingRequest.getServiceName() + "\n"+
                        "Stylist: " + bookingRequest.getStylist() + "\n"+
                        "Date: " + bookingRequest.getDate() + "\n"+
                        "Time Slot: " + bookingRequest.getTime()+ "\n\n" +

                        "Please ensure the booking is prepared accordingly.\n\n" +

                        "Thank you for choosing us\n\n" +
                        "Warm regards,\n" +
                         "RS BusinessTech\n" +
                         "www.rsbusinesstech.com");

        lashmapMessage.setFrom("lashmap.beautystudio@gmail.com");
        rsBusinessTechMailSender.send(lashmapMessage);

        // Sending email to Customer (optional)
        if (!StringUtil.isNullOrEmpty(bookingRequest.getEmail())) {
            try {
                userMessage.setTo(bookingRequest.getEmail());
                userMessage.setSubject("Your Booking is Confirmed");

                String messageBody = "Hi " + bookingRequest.getName() + ",\n\n" +
                        "Thank you for choosing us.\n\n" +
                        "Your booking details are as below:\n" +
                        "Service: " + bookingRequest.getServiceName() + "\n" +
                        "Stylist: " + bookingRequest.getStylist() + "\n" +
                        "Booking Date: " + bookingRequest.getDate() + "\n" +
                        "Time Slot: " + bookingRequest.getTime() + "\n\n" +
                        "We appreciate your trust in LashMap Beauty Studio.\n" +
                        "We're excited to welcome you soon!\n\n" +
                        "Warm regards,\n" +
                        "LashMap Beauty Studio\n" +
                        "www.lashmapbeautystudio.com";

                userMessage.setText(messageBody);
                userMessage.setFrom("lashmap.beautystudio@gmail.com");

                lashMapBeautyStudioMailSender.send(userMessage);
             //catch block to log the error, but does not interrupt the booking process.
            } catch (MailException e) {
            }
        }

    }
}
