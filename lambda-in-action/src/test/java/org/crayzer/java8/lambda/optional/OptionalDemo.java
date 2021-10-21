package org.crayzer.java8.lambda.optional;

import org.crayzer.java8.Address;
import org.crayzer.java8.Country;
import org.crayzer.java8.User;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class OptionalDemo {

    /**
     * 创建 Optional  实例
     */
    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    /**
     * <p>of value 为 null 时，会报 NPE
     * <p>ofNullable 不会 throw exception，直接返回 Optional.empty();
     */
    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        User user = null;
        Optional<User> opt = Optional.of(user);

//        Optional<User> opt = Optional.ofNullable(user);
    }

    /**
     * 访问 Optional 对象的值
     */
    @Test
    public void whenCreateOfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);
        assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIfPresent_thenOk() {
        User user = new User("john@gmail.com", "1234");
        Optional<User> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());

//        assertEquals(user.getEmail(), opt.get().getEmail());
        opt.ifPresent(u -> assertEquals(user.getEmail(), u.getEmail()));
    }

    /**
     * 返回默认值
     */
    @Test
    public void whenEmptyValue_thenReturnDefault() {
        User user = null;
        User user2 = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);

        assertEquals(user2.getEmail(), result.getEmail());
    }

    @Test
    public void whenValueNotNull_thenIgnoreDefault() {
        User user = new User("john@gmail.com", "1234");
        User user2 = new User("anna@gmail.com", "1234");
//        User result = Optional.ofNullable(user).orElse(user2);
        User result = Optional.ofNullable(user).orElseGet(() -> user2);

        assertEquals("john@gmail.com", result.getEmail());
    }

    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        User user = null;
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println(result);
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        System.out.println(result2);
    }

    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User("john@gmail.com", "1234");
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println(result);
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        System.out.println(result2);
    }

    private User createNewUser() {
        System.out.println("Creating New User");
        return new User("extra@gmail.com", "1234");
    }

    /**
     * 返回异常
     */
    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyValue_thenThrow() {
        User user = null;
//        User result = Optional.ofNullable(user).get();
        User result1 = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 转换值
     */
    @Test
    public void whenMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        String email = Optional.ofNullable(user)
                .map(User::getEmail).orElse("default@gmail.com");

        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenFlatMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        user.setPosition("Developer");
        String position = Optional.ofNullable(user)
                .flatMap(User::getPosition).orElse("default");

        assertEquals(position, user.getPosition().get());
    }

    /**
     * 过滤值
     */
    @Test
    public void whenFilter_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        Optional<User> result = Optional.ofNullable(user)
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

        assertTrue(result.isPresent());
    }

    /**
     * Optional 类的链式方法
     */
    @Test
    public void whenChaining_thenOk() {
        User user = new User("anna@gmail.com", "1234");

//        String result = Optional.ofNullable(user)
//                .flatMap(u -> u.getAddress())
//                .flatMap(a -> a.getCountry())
//                .map(c -> c.getIsocode())
//                .orElse("default");

        String result = Optional.ofNullable(user)
                .flatMap(User::getAddress)
                .flatMap(Address::getCountry)
                .map(Country::getIsocode)
                .orElse("default");

        assertEquals(result, "default");
    }


    @Test(expected = IllegalArgumentException.class)
    public void optional() {
        //通过get方法获取Optional中的实际值
        assertThat(Optional.of(1).get(), is(1));
        //通过ofNullable来初始化一个null，通过orElse方法实现Optional中无数据的时候返回一个默认值
        assertThat(Optional.ofNullable(null).orElse("A"), is("A"));
        //OptionalDouble是基本类型double的Optional对象，isPresent判断有无数据
        assertFalse(OptionalDouble.empty().isPresent());
        //通过map方法可以对Optional对象进行级联转换，不会出现空指针，转换后还是一个Optional
        assertThat(Optional.of(1).map(Math::incrementExact).get(), is(2));
        //通过filter实现Optional中数据的过滤，得到一个Optional，然后级联使用orElse提供默认值
        assertThat(Optional.of(1).filter(integer -> integer % 2 == 0).orElse(null), is(nullValue()));
        //通过orElseThrow实现无数据时抛出异常
        Optional.empty().orElseThrow(IllegalArgumentException::new);
    }

//   以前写法
//    public String getCity(User user)  throws Exception{
//        if(user!=null){
//            if(user.getAddress()!=null){
//                Address address = user.getAddress();
//                if(address.getCity()!=null){
//                    return address.getCity();
//                }
//            }
//        }
//        throw new Excpetion("取值错误");
//    }

    // Java8 写法
//    public String getCity(User user) throws Exception{
//        return Optional.ofNullable(user)
//                .map(u-> u.getAddress())
//                .map(a->a.getCity())
//                .orElseThrow(()->new Exception("取指错误"));
//    }
}
