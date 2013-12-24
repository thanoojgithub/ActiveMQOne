package com.activemqone;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	// URL of the JMS server. DEFAULT_BROKER_URL will just mean
	// that JMS server is on localhost
	public static final String DEFAULT_BROKER_BIND_URL = "tcp://127.1.1.0:61616";
	public static final String DEFAULT_BROKER_URL = "failover://"
			+ DEFAULT_BROKER_BIND_URL;

	// Name of the queue we will be sending messages to
	private static String subject = "QUEUEone";

	public static void main(String[] args) throws JMSException {
		// Getting JMS connection from the server and starting it
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				DEFAULT_BROKER_URL);
		javax.jms.Connection connection = connectionFactory.createConnection();
		connection.start();

		// JMS messages are sent and received using a Session. We will
		// create here a non-transactional session object. If you want
		// to use transactions you should set the first parameter to 'true'
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// Destination represents here our queue 'TESTQUEUE' on the
		// JMS server. You don't have to do anything special on the
		// server to create it, it will be created automatically.
		Destination destination = session.createQueue(subject);

		// MessageProducer is used for sending messages (as opposed
		// to MessageConsumer which is used for receiving them)
		MessageProducer producer = session.createProducer(destination);

		// We will send a small text message saying 'Hello ActiveMQ World!'
		TextMessage message = session
				.createTextMessage("Hello ActiveMQ World!!");

		// Here we are sending the message!
		producer.send(message);
		System.out.println("Sent message ::  '" + message.getText() + "'");

		connection.close();
	}
}
