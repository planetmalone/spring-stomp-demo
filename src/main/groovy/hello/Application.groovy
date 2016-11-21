package hello

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.amqp.core.Queue
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import receiver.Receiver

@SpringBootApplication
class Application {

    final static String queueName = 'spring-boot'

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        new WebMvcConfigurerAdapter() {
            @Override
            void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
            }
        }
    }

    @Bean
    Queue queue() {
        new Queue(queueName, false)
    }

    @Bean
    TopicExchange exchange() {
        new TopicExchange('spring-boot-exchange')
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        BindingBuilder.bind(queue).to(exchange).with(queueName)
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.setQueueNames(queueName)
        container.setupMessageListener(listenerAdapter)
        container
    }

    @Bean
    Receiver receiver() {
        new Receiver()
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        new MessageListenerAdapter(receiver, 'receiveMessage')
    }
}