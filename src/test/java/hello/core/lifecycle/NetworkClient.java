package hello.core.lifecycle;

// 가짜 네트워크 클라이언트 만들었다.
public class NetworkClient {

    // 접속해야 할 서버 url
    private String url;

    // 디폴트 생성자 만든다.
    public NetworkClient() {
        // 객체를 생성할 때
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    // 실제 네트워크 붙는 건 아니고 이 url부터라는 의미
    public void connect() {
        System.out.println("connect: " + url);
    }

    // 연결된 상태에서 콜 부를 수 있다.
    // 연결한 서버에 메시지 던질 수 있다.
    public void call(String message) {
        // 어떤 url에 call을 했고 message는 이거다.를 출력
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    // 그래야 안전하게 서비스 연결이 끊어진다.
    public void disConnect() {
        //
        System.out.println("close: " + url);
    }
}
