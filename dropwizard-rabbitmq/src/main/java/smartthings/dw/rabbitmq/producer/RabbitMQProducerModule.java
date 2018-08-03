package smartthings.dw.rabbitmq.producer;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.AbstractDwModule;

public class RabbitMQProducerModule extends AbstractDwModule {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducerManaged.class);

    @Override
    protected void configure() {
        requireBinding(RabbitMQProducerConfiguration.class);
        bind(RabbitMQProducerHealthCheck.class).in(Scopes.SINGLETON);

        registerHealthCheck(RabbitMQProducerHealthCheck.class);
        registerManaged(RabbitMQProducerManaged.class);
    }

    @Provides
    @Singleton
    ConnectionFactory provideConnectionFactory(RabbitMQProducerConfiguration config) {
        return config.buildConnectionFactory();
    }
}
