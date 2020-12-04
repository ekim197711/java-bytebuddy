package com.example.javabytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static net.bytebuddy.matcher.ElementMatchers.named;

@SpringBootTest
class JavaBytebuddyApplicationTests {

    @Test
    void contextLoads() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<? extends MyCoolClass> loaded = new ByteBuddy()
                .subclass(MyCoolClass.class)
                .name("dk.Mike")
                .implement(Jumping.class)
                .method(named("saySomethingCool"))
                .intercept(FixedValue.value("Lame stuff"))
                .defineMethod("pee", String.class).intercept(MethodDelegation.to(JumpingLow.class))
                .make()
                .load(this.getClass().getClassLoader())
                .getLoaded()
                ;

        MyCoolClass myCoolClass = loaded.getConstructor().newInstance();
        System.out.println(myCoolClass.saySomethingCool());
        System.out.println(((Jumping)myCoolClass).jump());
        System.out.println( myCoolClass.getClass().getName());


    }

}
