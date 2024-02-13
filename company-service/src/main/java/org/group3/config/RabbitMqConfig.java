package org.group3.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // Exchanges ----------------------------------------------------------------
    @Value("${rabbitmq.exchange.auth}")
    private String authExchange;

    @Value("${rabbitmq.exchange.admin}")
    private String adminExchange;

    @Value("${rabbitmq.exchange.manager}")
    private String managerExchange;

    @Value("${rabbitmq.exchange.personal}")
    private String personalExchange;

    @Value("${rabbitmq.exchange.visitor}")
    private String visitorExchange;

    @Value("${rabbitmq.exchange.company}")
    private String companyExchange;

    @Value("${rabbitmq.exchange.payment}")
    private String paymentExchange;

    @Value("${rabbitmq.exchange.mail}")
    private String mailExchange;

    @Value("${rabbitmq.exchange.sms}")
    private String smsExchange;

    @Value("${rabbitmq.exchange.holiday}")
    private String holidayExchange;

    // Queues ----------------------------------------------------------------
    // auth
    @Value("${rabbitmq.queue.auth.update}")
    private String authUpdateQueueName;
    @Value("${rabbitmq.queue.auth.delete}")
    private String authDeleteQueueName;

    // admin
    @Value("${rabbitmq.queue.admin.save}")
    private String adminSaveQueueName;

    // manager
    @Value("${rabbitmq.queue.manager.save}")
    private String managerSaveQueueName;
    @Value("${rabbitmq.queue.manager.addCompany}")
    private String managerAddCompanyQueueName;
    @Value("${rabbitmq.queue.manager.addPersonal}")
    private String managerAddPersonalQueueName;
    @Value("${rabbitmq.queue.manager.deleteCompany}")
    private String managerDeleteCompanyQueueName;
    @Value("${rabbitmq.queue.manager.deletePersonal}")
    private String managerDeletePersonalQueueName;

    // personal
    @Value("${rabbitmq.queue.personal.addHoliday}")
    private String personalAddHolidayQueueName;
    @Value("${rabbitmq.queue.personal.deleteHoliday}")
    private String personalDeleteHolidayQueueName;

    // visitor
    @Value("${rabbitmq.queue.visitor.save}")
    private String visitorSaveQueueName;

    // company
    @Value("${rabbitmq.queue.company.addHoliday}")
    private String companyAddHolidayQueueName;
    @Value("${rabbitmq.queue.company.addPayment}")
    private String companyAddPaymentQueueName;
    @Value("${rabbitmq.queue.company.deleteHoliday}")
    private String companyDeleteHolidayQueueName;
    @Value("${rabbitmq.queue.company.deletePayment}")
    private String companyDeletePaymentQueueName;

    // payment

    // mail
    @Value("${rabbitmq.queue.mail.sender}")
    private String mailSenderQueueName;

    // sms
    @Value("${rabbitmq.queue.sms.sender}")
    private String smsSenderQueueName;

    // holiday
    @Value("${rabbitmq.queue.holiday.save}")
    private String holidaySaveQueueName;

    // Binding Keys ----------------------------------------------------------------
    // auth
    @Value("${rabbitmq.bindingKey.auth.update}")
    private String authUpdateBindingKey;
    @Value("${rabbitmq.bindingKey.auth.delete}")
    private String authDeleteBindingKey;

    // admin
    @Value("${rabbitmq.bindingKey.admin.save}")
    private String adminSaveBindingKey;

    // manager
    @Value("${rabbitmq.bindingKey.manager.save}")
    private String managerSaveBindingKey;
    @Value("${rabbitmq.bindingKey.manager.addCompany}")
    private String managerAddCompanyBindingKey;
    @Value("${rabbitmq.bindingKey.manager.addPersonal}")
    private String managerAddPersonalBindingKey;
    @Value("${rabbitmq.bindingKey.manager.deleteCompany}")
    private String managerDeleteCompanyBindingKey;
    @Value("${rabbitmq.bindingKey.manager.deletePersonal}")
    private String managerDeletePersonalBindingKey;

    // personal
    @Value("${rabbitmq.bindingKey.personal.addHoliday}")
    private String personalAddHolidayBindingKey;
    @Value("${rabbitmq.bindingKey.personal.deleteHoliday}")
    private String personalDeleteHolidayBindingKey;

    // visitor
    @Value("${rabbitmq.bindingKey.visitor.save}")
    private String visitorSaveBindingKey;

    // company
    @Value("${rabbitmq.bindingKey.company.addHoliday}")
    private String companyAddHolidayBindingKey;
    @Value("${rabbitmq.bindingKey.company.addPayment}")
    private String companyAddPaymentBindingKey;
    @Value("${rabbitmq.bindingKey.company.deleteHoliday}")
    private String companyDeleteHolidayBindingKey;
    @Value("${rabbitmq.bindingKey.company.deletePayment}")
    private String companyDeletePaymentBindingKey;

    // payment

    // mail
    @Value("${rabbitmq.bindingKey.mail.sender}")
    private String mailSenderBindingKey;

    // sms
    @Value("${rabbitmq.bindingKey.sms.sender}")
    private String smsSenderBindingKey;

    // holiday
    @Value("${rabbitmq.bindingKey.holiday.save}")
    private String holidaySaveBindingKey;

    // Direct Exchanges ----------------------------------------------------------------
    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(authExchange);
    }

    @Bean
    DirectExchange exchangeAdmin(){
        return new DirectExchange(adminExchange);
    }

    @Bean
    DirectExchange exchangeManager(){
        return new DirectExchange(managerExchange);
    }

    @Bean
    DirectExchange exchangePersonal(){
        return new DirectExchange(personalExchange);
    }

    @Bean
    DirectExchange exchangeVisitor(){
        return new DirectExchange(visitorExchange);
    }

    @Bean
    DirectExchange exchangeCompany(){
        return new DirectExchange(companyExchange);
    }

    @Bean
    DirectExchange exchangePayment(){
        return new DirectExchange(paymentExchange);
    }

    @Bean
    DirectExchange exchangeMail(){
        return new DirectExchange(mailExchange);
    }

    @Bean
    DirectExchange exchangeSms(){
        return new DirectExchange(smsExchange);
    }

    @Bean
    DirectExchange exchangeHoliday(){
        return new DirectExchange(holidayExchange);
    }

    // Queue Beans ----------------------------------------------------------------
    @Bean
    Queue authUpdateQueue(){
        return new Queue(authUpdateQueueName);
    }

    @Bean
    Queue authDeleteQueue(){
        return new Queue(authDeleteQueueName);
    }

    @Bean
    Queue adminSaveQueue(){
        return new Queue(adminSaveQueueName);
    }

    @Bean
    Queue managerSaveQueue(){
        return new Queue(managerSaveQueueName);
    }

    @Bean
    Queue managerAddCompanyQueue(){
        return new Queue(managerAddCompanyQueueName);
    }

    @Bean
    Queue managerAddPersonalQueue(){
        return new Queue(managerAddPersonalQueueName);
    }

    @Bean
    Queue managerDeleteCompanyQueue(){
        return new Queue(managerDeleteCompanyQueueName);
    }

    @Bean
    Queue managerDeletePersonalQueue(){
        return new Queue(managerDeletePersonalQueueName);
    }

    @Bean
    Queue personalAddHolidayQueue(){
        return new Queue(personalAddHolidayQueueName);
    }

    @Bean
    Queue personalDeleteHolidayQueue(){
        return new Queue(personalDeleteHolidayQueueName);
    }

    @Bean
    Queue visitorSaveQueue(){
        return new Queue(visitorSaveQueueName);
    }

    @Bean
    Queue companyAddHolidayQueue(){
        return new Queue(companyAddHolidayQueueName);
    }

    @Bean
    Queue companyAddPaymentQueue(){
        return new Queue(companyAddPaymentQueueName);
    }

    @Bean
    Queue companyDeleteHolidayQueue(){
        return new Queue(companyDeleteHolidayQueueName);
    }

    @Bean
    Queue companyDeletePaymentQueue(){
        return new Queue(companyDeletePaymentQueueName);
    }

    @Bean
    Queue mailSenderQueue(){
        return new Queue(mailSenderQueueName);
    }

    @Bean
    Queue smsSenderQueue(){
        return new Queue(smsSenderQueueName);
    }

    @Bean
    Queue holidaySaveQueue(){
        return new Queue(holidaySaveQueueName);
    }

    // Binding Beans ----------------------------------------------------------------
    @Bean
    public Binding authUpdateBinding(Queue authUpdateQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(authUpdateQueue).to(exchangeAuth).with(authUpdateBindingKey);
    }

    @Bean
    public Binding authDeleteBinding(Queue authDeleteQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(authDeleteQueue).to(exchangeAuth).with(authDeleteBindingKey);
    }

    @Bean
    public Binding adminSaveBinding(Queue adminSaveQueue, DirectExchange exchangeAdmin){
        return BindingBuilder.bind(adminSaveQueue).to(exchangeAdmin).with(adminSaveBindingKey);
    }

    @Bean
    public Binding managerSaveBinding(Queue managerSaveQueue, DirectExchange exchangeManager){
        return BindingBuilder.bind(managerSaveQueue).to(exchangeManager).with(managerSaveBindingKey);
    }

    @Bean
    public Binding managerAddCompanyBinding(Queue managerAddCompanyQueue, DirectExchange exchangeManager){
        return BindingBuilder.bind(managerAddCompanyQueue).to(exchangeManager).with(managerAddCompanyBindingKey);
    }

    @Bean
    public Binding managerAddPersonalBinding(Queue managerAddPersonalQueue, DirectExchange exchangeManager){
        return BindingBuilder.bind(managerAddPersonalQueue).to(exchangeManager).with(managerAddPersonalBindingKey);
    }

    @Bean
    public Binding managerDeleteCompanyBinding(Queue managerDeleteCompanyQueue, DirectExchange exchangeManager){
        return BindingBuilder.bind(managerDeleteCompanyQueue).to(exchangeManager).with(managerDeleteCompanyBindingKey);
    }

    @Bean
    public Binding managerDeletePersonalBinding(Queue managerDeletePersonalQueue, DirectExchange exchangeManager){
        return BindingBuilder.bind(managerDeletePersonalQueue).to(exchangeManager).with(managerDeletePersonalBindingKey);
    }

    @Bean
    public Binding personalAddHolidayBinding(Queue personalAddHolidayQueue, DirectExchange exchangePersonal){
        return BindingBuilder.bind(personalAddHolidayQueue).to(exchangePersonal).with(personalAddHolidayBindingKey);
    }

    @Bean
    public Binding personalDeleteHolidayBinding(Queue personalDeleteHolidayQueue, DirectExchange exchangePersonal){
        return BindingBuilder.bind(personalDeleteHolidayQueue).to(exchangePersonal).with(personalDeleteHolidayBindingKey);
    }

    @Bean
    public Binding visitorSaveBinding(Queue visitorSaveQueue, DirectExchange exchangeVisitor){
        return BindingBuilder.bind(visitorSaveQueue).to(exchangeVisitor).with(visitorSaveBindingKey);
    }

    @Bean
    public Binding companyAddHolidayBinding(Queue companyAddHolidayQueue, DirectExchange exchangeCompany){
        return BindingBuilder.bind(companyAddHolidayQueue).to(exchangeCompany).with(companyAddHolidayBindingKey);
    }

    @Bean
    public Binding companyAddPaymentBinding(Queue companyAddPaymentQueue, DirectExchange exchangeCompany){
        return BindingBuilder.bind(companyAddPaymentQueue).to(exchangeCompany).with(companyAddPaymentBindingKey);
    }

    @Bean
    public Binding companyDeletePaymentBinding(Queue companyDeletePaymentQueue, DirectExchange exchangeCompany){
        return BindingBuilder.bind(companyDeletePaymentQueue).to(exchangeCompany).with(companyDeletePaymentBindingKey);
    }

    @Bean
    public Binding companyDeleteHolidayBinding(Queue companyDeleteHolidayQueue, DirectExchange exchangeCompany){
        return BindingBuilder.bind(companyDeleteHolidayQueue).to(exchangeCompany).with(companyDeleteHolidayBindingKey);
    }

    @Bean
    public Binding mailSenderBinding(Queue mailSenderQueue, DirectExchange exchangeMail){
        return BindingBuilder.bind(mailSenderQueue).to(exchangeMail).with(mailSenderBindingKey);
    }

    @Bean
    public Binding smsSenderBinding(Queue smsSenderQueue, DirectExchange exchangeSms){
        return BindingBuilder.bind(smsSenderQueue).to(exchangeSms).with(smsSenderBindingKey);
    }

    @Bean
    public Binding holidaySaveBinding(Queue holidaySaveQueue, DirectExchange exchangeHoliday){
        return BindingBuilder.bind(holidaySaveQueue).to(exchangeHoliday).with(holidaySaveBindingKey);
    }

    // MessageConverter Bean ----------------------------------------------------------------
    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate Bean ----------------------------------------------------------------
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
