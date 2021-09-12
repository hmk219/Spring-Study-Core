package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // MyLogger의 가짜 프록시 클래스를 생성
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " +
                message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}

/* 기대하는 출력 = 출력 결과
[d06b992f...] request scope bean create
[d06b992f...][http://localhost:8080/log-demo] controller test
[d06b992f...][http://localhost:8080/log-demo] service id = testId
[d06b992f...] request scope bean close
 */

// LogDemoController, LogDemoService는 Provider 사용 전과 완전히 동일하다.
//
// HTTP request 이전에 미리
// 스프링 컨테이너에 "myLogger"라는 이름으로 MyLogger를 상속받은 가짜 프록시 객체를 생성, 등록, 다른 빈에 주입
//
// ac.getBean("myLogger", MyLogger.class)으로 조회하면 가짜 프록시 객체가 조회되는 것을 확인할 수 있다.
//
// 클라이언트가 myLogger.logic()을 호출하면 사실은 가짜 프록시 객체의 메서드를 호출한 것이다.
// 클라이언트 입장에서는 원본인지 아닌지도 모르게, 동일하게 사용할 수 있다 (다형성)
//
// 가짜 프록시 객체 내부에는 request 시점에 실제 빈을 요청하는 위임 로직이 들어있다
// 가짜 프록시 객체는 실제 빈의 request scope와는 관계 없이 싱글톤처럼 동작한다.

