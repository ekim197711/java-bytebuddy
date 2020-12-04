package com.example.javabytebuddy.space;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.jupiter.api.Assertions.*;

public class SpaceShipTest {

    @Test
    public void flyAround() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SpaceShip mikesShip = new ByteBuddy()
                .subclass(SpaceShip.class)
                .name("dk.mike.SlowSpaceShip")
                .method(named("flyAround"))
                .intercept(FixedValue.value("Swooooshhh.... Cough cough"))
                .implement(Shooter.class)
                .method(named("lazerShot"))
                .intercept(MethodDelegation.to(MyStaticCode.class))
                .make()
                .load(SpaceShipTest.class.getClassLoader())
                .getLoaded()
                .getConstructor().newInstance();


        System.out.println("fly: " + mikesShip.flyAround());
        System.out.println("shoot: " + ((Shooter)mikesShip).lazerShot());
        System.out.println("land: " + mikesShip.land());
        System.out.println("class name: " + mikesShip.getClass().getName());


    }
}