package hello.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaSingletonContainerTest {

    static class SingletonClass {
        private static final SingletonClass singletonClass = new SingletonClass();
        public int price = 10000;
        private SingletonClass() {}
        public static SingletonClass getSingletonClass() { return singletonClass; }
        public void printPrice() { System.out.println("price = " + price); }
        public void setPrice(int price) { this.price = price; }
    }

    @Test
    @DisplayName(value = "순수한 Java Singleton Pattern Test")
    public void singletonPatternTest() {
        SingletonClass singletonClass1 = SingletonClass.getSingletonClass();
        SingletonClass singletonClass2 = SingletonClass.getSingletonClass();
        System.out.println("singletonClass1 = " + singletonClass1);
        System.out.println("singletonClass2 = " + singletonClass2);
        assertThat(singletonClass1).isEqualTo(singletonClass2);
    }

    @Test
    @DisplayName(value = "Singleton 패턴에서는 값의 공유를 조심해야한다.")
    public void singletonCautionTest() {
        SingletonClass singletonClass1 = SingletonClass.getSingletonClass();
        SingletonClass singletonClass2 = SingletonClass.getSingletonClass();
        System.out.println("singletonClass1 = " + singletonClass1);
        System.out.println("singletonClass2 = " + singletonClass2);
        assertThat(singletonClass1).isEqualTo(singletonClass2);

        singletonClass1.printPrice();
        singletonClass2.printPrice();

        singletonClass1.setPrice(10);

        singletonClass1.printPrice();
        singletonClass2.printPrice();
    }

}
