package com.intheeast.springframe.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaDynamicProxyApp {
    
    public static void main(String[] args) {
    	
    	
        
        Person person = new Person("HauChee");
        Animal animal = new Animal();
        Object proxy = Proxy.newProxyInstance(
                MyInvocationHandler.class.getClassLoader(),
                // foremost interface
                new Class[] {IAnimal.class, IPerson.class /*IPerson.class, IAnimal.class*/},
                new MyInvocationHandler2(person, animal));
        
        System.out.println(proxy instanceof Object); // output: true
        System.out.println(proxy instanceof IPerson); // output: true
        System.out.println(proxy instanceof IAnimal); // output: true
        
        /**
         * Proxy class implements the interfaces not the concrete class
         * therefore, any public method in Person such as think() will never
         * be invoked from proxy instance.
         */
        System.out.println(proxy instanceof Person); // output: false
        System.out.println(proxy instanceof Animal); // output: false
        
        IPerson proxiedPerson = (IPerson) proxy;
        proxiedPerson.getName(); // output: Intercepted.. Person name..
        proxiedPerson.eat(); // output: Intercepted.. Person eat..
        
        IAnimal proxiedAnimal = (IAnimal) proxy;
        proxiedAnimal.eat(); // output: Intercepted.. Person eat.. 
        /**
         * WAIT A MINUTE! ISN't IT SHOULD SHOW "Animal eat.." INSTEAD?
         * 
         * Although eat() method is called based on IAnimal interface
         * but because there is a duplicate method eat() in IPerosn
         * therefore Method object passed into MyInvocationHandler.invoke()
         * method always take from the foremost interface which is IPerson
         * in this case.
         */
    }
}

class MyInvocationHandler2 implements InvocationHandler {
    
    private Object proxiedPerson;
    private Object proxiedAnimal;

    public MyInvocationHandler2(Object person, Object animal) {
        this.proxiedPerson = person;
        this.proxiedAnimal = animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.print("Intercepted.. ");
        
        if (method.getDeclaringClass() == IPerson.class) {
            // Invoke real method of Person object
            return method.invoke(proxiedPerson, args); 
        }
        
        // Invoke real method of Animal object
        return method.invoke(proxiedAnimal, args);      
        
    }
}

class Person implements IPerson {
    
    private String name;
    
    Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        System.out.println("Person name..");
        return name;
    }

    @Override
    public void eat() {
        System.out.println("Person eat..");
    }
    
    public void think() {
        System.out.println("Person think..");
    }
}

class Animal implements IAnimal {

    @Override
    public void eat() {
        System.out.println("Animal eat..");
    }
}

interface IPerson {
    String getName();
    void eat();
}

interface IAnimal {
    void eat();
}
