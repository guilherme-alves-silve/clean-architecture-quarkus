package br.com.guilhermealvessilve;

import io.smallrye.mutiny.Multi;

public class Main {

    public static void main(String[] args) {

        Multi.createFrom().emitter(emitter -> {
            // ...
            emitter.emit("a");
            emitter.emit("b");
            emitter.complete();
            //...
        });

        Multi.createFrom().items("a", "b", "c")
                .onItem().apply(String::toUpperCase)
                .subscribe().with(
                item -> System.out.println("Received: " + item),
                failure -> System.out.println("Failed with " + failure.getMessage()),
                () -> System.out.println("Completed all the process"));
    }
}
