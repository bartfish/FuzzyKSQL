package pl.bartryb.producer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

//        System.out.println(args[0] + " " + args[1]);
//        System.out.println("Chose: " + args[0] + " topicName: " + args[1] + " numberOfMessagesPerSecond: " + args[2]);
        ProducerAGV.Producer();


    }
}
