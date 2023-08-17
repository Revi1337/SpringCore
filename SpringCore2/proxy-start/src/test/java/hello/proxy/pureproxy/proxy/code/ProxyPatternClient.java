package hello.proxy.pureproxy.proxy.code;

// TODO Proxy Pattern : 프록시를 사용하는 여러패턴중 하나일 뿐이며 이름에 프록시가 들어갔을 뿐이다. (접근제어가 목적)
public class ProxyPatternClient {

    public Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
