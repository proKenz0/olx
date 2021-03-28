package org.example;


import org.example.services.*;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args ) {

        Starter starter = new Starter(OutputService.getInstance());
        starter.start();

    }
}
