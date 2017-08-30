package autostack.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import autostack.AutoStack;

@RunWith(Enclosed.class)
public class AutoStackImplTest {

    @RunWith(JUnit4.class)
    public static class AutoStackImplProperTest {

        @Test
        public void testOfEmpty_resultClass() {

            // 事前準備
            Class<?>[] declaredClasses = AutoStackImpl.class.getDeclaredClasses();
            Class<?> emptyStackClass = Arrays.stream(declaredClasses) //
                    .filter(cls -> "EmptyStack".equals(cls.getSimpleName())) //
                    .findAny() //
                    // NoSuchElementExceptionが
                    // throwされるときはテスト失敗
                    .get();

            // テスト
            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();

            // assert
            assertEquals("ofEmpty()メソッドの結果インスタンスの型が正しいこと", emptyStackClass, empty.getClass());

        }

    }

    @RunWith(JUnit4.class)
    public static class EmptyStackTest {

        private static final String EMPTY_STACK_EXCEPTION_MESSAGE = "empty stack";

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Test
        public void testOf_isSingleton() {

            AutoStack<?, ?> empty1 = AutoStackImpl.ofEmpty();
            AutoStack<?, ?> empty2 = AutoStackImpl.ofEmpty();

            assertSame("空のAutoStackは同じインスタンス", empty1, empty2);

        }

        @Test
        public void testPush_Function() {

            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();
            Function<Object, Object> mapper = (obj) -> obj;

            thrown.expect(NoSuchElementException.class);
            thrown.expectMessage(EMPTY_STACK_EXCEPTION_MESSAGE);

            empty.push(mapper);

        }

        @Test
        public void testHead() {

            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();

            thrown.expect(NoSuchElementException.class);
            thrown.expectMessage(EMPTY_STACK_EXCEPTION_MESSAGE);

            empty.head();

        }

        @Test
        public void testPop() {

            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();

            thrown.expect(NoSuchElementException.class);
            thrown.expectMessage(EMPTY_STACK_EXCEPTION_MESSAGE);

            empty.pop();

        }

        @Test
        public void testIsEmpty() {

            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();

            assertTrue("isEmpty()メソッドはtrueを返す", empty.isEmpty());

        }

        @Test
        public void testPush() {

            String str = "string";
            Long lng = Long.valueOf(Long.MIN_VALUE);
            AutoStack<?, ?> empty = AutoStackImpl.ofEmpty();
            AutoStack<String, ?> strStacked = empty.push(str);
            AutoStack<Long, ?> lngStacked = empty.push(lng);

            assertEquals("結果型が正しいこと", AutoStackImpl.class, strStacked.getClass());
            assertEquals("結果型が正しいこと", AutoStackImpl.class, lngStacked.getClass());
            assertNotSame("結果インスタンスが異なること", empty, strStacked);
            assertNotSame("結果インスタンスが異なること", empty, lngStacked);
            assertSame("headを取得するとpushしたインスタンスが取得できること", str, strStacked.head());
            assertSame("headを取得するとpushしたインスタンスが取得できること", lng, lngStacked.head());

        }

    }

}
