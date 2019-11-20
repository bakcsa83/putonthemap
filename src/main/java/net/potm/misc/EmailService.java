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

package net.potm.misc;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailService implements Serializable {
    private static final String SENDER_EMAIL="noreply@putonthemap.net";

    @Inject
    Logger log;

    @Resource(name = "java:/putonthemap-mail")
    private Session session;

    public void send(String addresses, String topic, String textMessage) {
        new Thread(new EmailSender(addresses, topic, textMessage)).start();
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
                Message message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
                message.setSubject(subject);
                message.setText(msg);
                message.setFrom(new InternetAddress(SENDER_EMAIL,"Put on the map"));

                Transport.send(message);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Email could not be sent", e);
            }
        }
    }
}
