package hello

import groovy.transform.TypeChecked
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@TypeChecked
@RestController
class GreetingController {

    @MessageMapping(value = '/hello')
    @SendTo('/topic/greetings')
    Greeting greeting(@RequestBody final HelloMessage message) {
        Thread.sleep(1000)
        new Greeting(content: 'Hello, ' + message.name + '!');
    }
}
