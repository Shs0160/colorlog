package color_log.colorlog;

import org.junit.jupiter.api.*;


public class TestLifeCycle {

    @BeforeAll
    static void beforeAll(){
        System.out.println("BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll(){
        System.out.println("AfterAll Annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("BeforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach(){
        System.out.println("AfterEach Annotation 호출 ##");
        System.out.println();
    }
}
