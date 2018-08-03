package smartthings.dw.rabbitmq.producer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@Singleton
public class RabbitMQProducerManaged implements Managed {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducerManaged.class);
    private ConnectionFactory connectionFactory;
    private Channel channel;
    private Connection connection;
    private final RabbitMQProducerConfiguration config;


    @Inject
    public RabbitMQProducerManaged(RabbitMQProducerConfiguration config, ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.config = config;
    }

    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public void start() throws IOException, TimeoutException {
        this.connection = connectionFactory.newConnection();
        log.trace("RabbitMQ Producer Connection Created");
        this.channel = this.connection.createChannel();
        log.trace("RabbitMQ Producer Channel Created");
    }

    @Override
    public void stop() throws IOException {
        try {
            if (channel.isOpen()) {
                channel.close();
                log.trace("RabbitMQ Producer Channel Closed");
            }
        } catch (Exception e) {
            log.error("Can't Close RabbitMQ Producer Channel Cleanly", e);
            channel.abort();
            log.trace("RabbitMQ Producer Channel Aborted");
        }
        try {
            if (connection.isOpen()) {
                connection.close(config.getShutdownTimeout());
                log.trace("RabbitMQ Producer Connection Closed");
            }
        } catch (Exception e) {
            log.error("Can't Close RabbitMQ Producer Connection Cleanly", e);
            connection.abort(config.getShutdownTimeout());
            log.trace("RabbitMQ Producer Connection Aborted");
        }
    }
}
