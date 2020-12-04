package com.example.javabytebuddy.space;

public interface Shooter {
    default String lazerShot(){
        return "Bzzzzz";
    }
}
