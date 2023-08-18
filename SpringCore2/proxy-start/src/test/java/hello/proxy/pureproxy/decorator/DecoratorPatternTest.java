package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.Component;
import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    @DisplayName(value = "Decorator 패턴 사용 전")
    public void noDecorator() {
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);

        client.execute();
    }

    @Test
    @DisplayName(value = "Decorator 패턴 사용 후 1")
    public void messageDecoratorTest() {
        RealComponent realComponent = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(messageDecorator);

        decoratorPatternClient.execute();
    }
}
