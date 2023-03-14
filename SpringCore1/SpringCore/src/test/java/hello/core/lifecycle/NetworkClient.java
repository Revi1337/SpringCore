package hello.core.lifecycle;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


public class NetworkClient implements InitializingBean, DisposableBean {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private String url;

    public NetworkClient() {
        System.out.println(ANSI_GREEN + "2. ========= NetworkClient No Args Construct called =======" + ANSI_RESET);
        System.out.println(this.getClass().getSimpleName() + " No Args Called");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect : " + url );
    }

    public void call(String message) {
        System.out.println("call : " + url + ", message = " + message);
    }

    public void disconnect() {
        System.out.println("disconnect : " + url);
    }

    @Override       // 의존관계 주입이 다 끝나고 호출됨.
    public void afterPropertiesSet() throws Exception {
        System.out.println(ANSI_GREEN + "4. ========= Initialize Callback : afterPropertiesSet() =======" + ANSI_RESET);
        connect();
        call("초기화 연결 메시지");
    }

    @Override       // Bean 이 종료될 때 호출
    public void destroy() throws Exception {
        System.out.println(ANSI_GREEN + "6. ======= Destroy Callback : destroy() =======" + ANSI_RESET);
        disconnect();
    }
}
