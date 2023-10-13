package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /**
     * 메서드 실행의 주변에서 실행된다. 메서드 실행 전후의 작업을 수행한다는 의미이며 가장 강력한 Advice 이다.
     * - JoinPoint 실행 여부 선택 가능
     * - 전달 값 변환 가능. joinPoint.proceed(args[])
     * - 반환 값 변환 가능
     * - 예외 변환 가능
     * - 트랜잭션처럼 try catch finally  모두 들어가는 구문 처리 가능
     * Advice 의 첫번째 파라미터에는 ProceedingJoinPoint 를 꼭 사용해야 한다.
     * proceed() 를 통해 대상을 실행하며 proceed() 를 여러번 실행할 수도 있다.(재시도) --> 꼭 한번은 호출해야한다. --> 호출하지 않으면 타겟이호출되지 않는 버그가 발생한다.
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // ProceedingJoinPoint 는 @Around 에서만 쓸 수 있다.
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", proceedingJoinPoint.getSignature());

            Object result = proceedingJoinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", proceedingJoinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", proceedingJoinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", proceedingJoinPoint.getSignature());
        }
    }

    /**
     * `@Before` 에서는 JoinPoint 만 쓸 수 있다. (JoinPoint 쓸일이 없으면 쓰지 않아도 된다.)
     * `@Around` 와 다르게 작업 흐름을 변경할 수 는 없다.
     * `@Around` 는 ProceedingJoinPoint.proceed() 를 호출해야 다음 대상이 호출되지만, 호출하지 않으면 다음 대상이 호출되지 않는다.
     *  반면에 @Before 는 ProceedingJoinPoint.proceed() 자체를 사용하지 않는다.
     * @param joinPoint
     */
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     * `@AfterReturning` 메서드의 실행이 정상적으로 반환활 때 실행된다.
     * `@AfterReturning` 의 returning 값과, Advice 메서드 안에 들어오는 매개변수 이름이 같아야 한다.
     * `@Around` 와 다르게 return 값을 바꿔서 보낼 수는 없다.
     *  리턴타입을 꼭 Object 타입으로 받을 필요는 없다. Object 타입은 모든 객체의 최상의 타입이기 때문에 모든 메서드에서 Trigger 되지만
     *  예를 들어 리턴타입을 String 으로 설정하면 String 을 리턴하는 메서드만 Trigger 된다. --> (부모 타입을 지정하면 모든 자식 타입은 인정된다.)
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return = {}", joinPoint.getSignature(), result);
    }

    /**
     * `@AfterThrowing` 의 throwing 값과, 메서드안에 들어오는 매개변수 이름이 같아야 한다.
     * `@Around` 에서는 Exception 을 직접 throw 해주었지만, @AfterThrowing 에서는 알아서 Exception 을 throw 해준다.
     * (부모타입을 지정하면 모든 자식 타입은 인정된다.)
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message = {}", ex);
    }

    /**
     * finally 로직이라고 생각하면되며 메서드 실행이 종료되면 실행한다.
     * 정상 및 예외 반환 조건을 모두 처리한다.
     * 일반적으로 리소스 및 유사한 목적을 해제하는 데 사용한다.
     * @param joinPoint
     */
    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
