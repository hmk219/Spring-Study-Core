package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeProviderTest {

    @Test
    void providerTest() {

        AnnotationConfigApplicationContext ac = new
                AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }


//    // 1. 싱글톤 빈이 프로토타입을 사용할 때마다 스프링 컨테이너에 새로 요청
//    static class ClientBean {
//
//        @Autowired
//        private ApplicationContext ac;
//
//        public int logic() {
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
//    }

//    // 2. ObjectFactory, ObjectProvider 사용
//    // ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존
//    // ObjectProvider: ObjectFactory 상속 받아 옵션, 스트림 처리등 편의 기능 추가, 별도의 라이브러리 필요없음, 스프링에 의존
//    static class ClientBean {
//
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
//    }

    // 3. JSR-330 Provider 사용
    // get() 메서드 하나로 기능이 매우 단순하다.
    // 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
    // 별도의 라이브러리(javax.inject:javax.inject:1)가 필요하다.
    static class ClientBean {

        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;
        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }
}
