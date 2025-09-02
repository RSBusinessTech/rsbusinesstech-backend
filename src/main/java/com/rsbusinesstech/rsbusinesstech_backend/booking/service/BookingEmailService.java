package com.rsbusinesstech.rsbusinesstech_backend.booking.service;

import com.rsbusinesstech.rsbusinesstech_backend.booking.model.BookingRequest;
import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

        message.setTo("rsbusinesstech@gmail.com");                                                                     //setting "to".
        message.setSubject("Appointment booked by Customer || "+ bookingRequest.getName() + " || " + bookingRequest.getPhone()+ " || " + bookingRequest.getEmail());
        message.setText(
                "You have a new Appointment from your website:\n\n" +                                                     //setting "body".
                        "Name:" + bookingRequest.getName() + "\n"+
                        "Email:" + bookingRequest.getEmail() + "\n"+
                        "Phone:" + bookingRequest.getPhone() + "\n"+
                        "ServiceId:" + bookingRequest.getServiceId() + "\n"+
                        "Stylist:" + bookingRequest.getStylist() + "\n"+
                        "Date:" + bookingRequest.getDate() + "\n"+
                        "Time:" + bookingRequest.getTime());
        message.setFrom("rsbusinesstech@gmail.com");                                                                  //setting "from".

        rsBusinessTechMailSender.send(message);
    }

    //method to send email to LashMapBeautyStudio from "Book Appointment" section.
    public void sendBookingEmailLashMapBeautyStudio(BookingRequest bookingRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("lashmap.beautystudio@gmail.com");                                                                     //setting "to".
        message.setSubject("Appointment booked by Customer || "+ bookingRequest.getName() + " || " + bookingRequest.getPhone()+ " || " + bookingRequest.getEmail());
        message.setText(
                "You have a new Appointment from your website:\n\n" +                                                     //setting "body".
                        "Name:" + bookingRequest.getName() + "\n"+
                        "Email:" + bookingRequest.getEmail() + "\n"+
                        "Phone:" + bookingRequest.getPhone() + "\n"+
                        "ServiceId:" + bookingRequest.getServiceId() + "\n"+
                        "Stylist:" + bookingRequest.getStylist() + "\n"+
                        "Date:" + bookingRequest.getDate() + "\n"+
                        "Time:" + bookingRequest.getTime());
        message.setFrom("lashmap.beautystudio@gmail.com");                                                                  //setting "from".

        lashMapBeautyStudioMailSender.send(message);
    }
}
