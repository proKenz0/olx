package org.example;


import org.example.olx_config.UrlAdress;
import org.example.requests.HttpReq;
import org.example.services.*;
import org.example.interfaces.IAccountService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args ) {

        Starter starter = new Starter(new OutputService());
        starter.start();

    }
}
