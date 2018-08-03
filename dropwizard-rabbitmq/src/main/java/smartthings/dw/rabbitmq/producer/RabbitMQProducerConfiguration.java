package smartthings.dw.rabbitmq.producer;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQProducerConfiguration {
    private String username;
    private String password;

    private String host;
    private int port;
    private String virtualHost;

    private boolean automaticRecoveryEnabled;
    private int shutdownTimeout;

    int getShutdownTimeout() {
        return shutdownTimeout;
    }

    ConnectionFactory buildConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setHost(host);
        factory.setPort(port);

        factory.setAutomaticRecoveryEnabled(automaticRecoveryEnabled);

        return factory;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public void setShutdownTimeout(int shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public void setAutomaticRecoveryEnabled(boolean automaticRecoveryEnabled) {
        this.automaticRecoveryEnabled = automaticRecoveryEnabled;
    }
}
