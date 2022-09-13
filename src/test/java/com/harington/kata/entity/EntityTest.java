package com.harington.kata.entity;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

public class EntityTest {

    @Test
    public void TestEntities(){
        assertPojoMethodsForAll(Customer.class, Account.class).quickly()
                .testing(Method.CONSTRUCTOR)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .areWellImplemented();
    }

}
