package hello

import groovy.transform.TypeChecked
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@TypeChecked
@RestController
class GreetingController {

    private SimpMessagingTemplate template;

    GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping(value = 'app/hello', method = [RequestMethod.POST])
    @SendTo('/topic/greetings')
    Greeting greeting(@RequestBody final HelloMessage message) {
        Greeting greeting = new Greeting(content: 'Hello, ' + message.name + '!');
        template.convertAndSend('/topic/greetings', greeting);
        greeting.content = greeting.content + ':RESPONSE';
        greeting;
    }
}
