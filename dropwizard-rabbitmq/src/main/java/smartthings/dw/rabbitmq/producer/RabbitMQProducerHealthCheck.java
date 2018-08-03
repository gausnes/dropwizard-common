package smartthings.dw.rabbitmq.producer;

import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.NamedHealthCheck;

import javax.inject.Inject;

public class RabbitMQProducerHealthCheck extends NamedHealthCheck {
    private final static Logger log = LoggerFactory.getLogger(RabbitMQProducerHealthCheck.class);
    private final Connection connection;

    @Inject
    public RabbitMQProducerHealthCheck(RabbitMQProducerManaged rabbitMQProducerManaged) {
        this.connection = rabbitMQProducerManaged.getChannel().getConnection();
    }

    @Override
    public String getName() {
        return "rabbitmq";
    }

    @Override
    protected Result check() {
        try {
            if (connection.isOpen()) {
                return Result.healthy();
            } else {
                return Result.unhealthy("RabbitMQ Connection is Closed");
            }
        } catch (Exception e) {
            log.error("RabbitMQ Health Check Error", e);
            return Result.unhealthy("RabbitMQ Connection Error");
        }
    }

}
