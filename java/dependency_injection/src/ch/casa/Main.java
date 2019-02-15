package ch.casa;


import com.google.inject.Guice;
import com.google.inject.Injector;


public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ApplicationModule());
        Communication communication = injector.getInstance(Communication.class);
        communication.sendMessage("hello world");
    }
}

