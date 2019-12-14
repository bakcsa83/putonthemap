/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.business.util;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailService {

    static Logger log=Logger.getLogger(TextService.class.getName());

    private Session mailSession;
    private String senderAddress;
    private String senderName;

    @PostConstruct
    public void init() {
        prepareSession();
    }

    private void prepareSession() {
        Properties prop = new Properties();
        String propFileName = "email.properties";
        InputStream inputStream;

        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream == null) {
            mailSession = null;
            throw new EmailServiceException("email.properties could not be loaded");
        }
        try {
            prop.load(inputStream);
        } catch (Exception e) {
            mailSession = null;
            throw new EmailServiceException("Properties object could not be populated");
        }

        var auth = new javax.mail.Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.username"), prop.getProperty("mail.password"));
            }
        };

        mailSession = Session.getInstance(prop, auth);
        senderAddress=prop.getProperty("mail.sender_address");
        senderName=prop.getProperty("mail.sender_name");
    }

    public void send(String addresses, String topic, String textMessage) {
        new Thread(new EmailSender(addresses, topic, textMessage)).start();
    }

    public static class EmailServiceException extends RuntimeException {
        private static final long serialVersionUID = -767386851177634591L;

        public EmailServiceException(String msg) {
            super(msg);
        }
    }

    public class EmailSender implements Runnable {

        String addresses;
        String subject;
        String msg;

        public EmailSender(String addresses, String topic, String textMessage) {
            this.addresses = addresses;
            subject = topic;
            msg = textMessage;
        }

        @Override
        public void run() {
            try {
                if (mailSession == null) prepareSession();
                Message message = new MimeMessage(mailSession);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
                message.setSubject(subject);
                message.setText(msg);
                message.setFrom(new InternetAddress(senderAddress, senderName));
                Transport.send(message);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Email could not be sent", e);
            }
        }
    }
}
