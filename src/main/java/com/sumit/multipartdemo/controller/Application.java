package com.sumit.multipartdemo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Application {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Application app = new Application();
        app.startProcessing();
    }

    private void startProcessing() throws ExecutionException, InterruptedException {
        List<String> result = new ArrayList<>();
        List<CompletableFuture<String>> completableFutures = List.of(startLongRunningOperation(), endLongRunningOperation());
        CompletableFuture<String>[] futures = new CompletableFuture[completableFutures.size()];
        CompletableFuture<Result> mainFuture = CompletableFuture.allOf(completableFutures.toArray(futures))
                .thenApply(notInUse -> {
                    completableFutures.forEach(cf -> result.add(cf.join()));
                    return new Result(result, "OK");
                }).exceptionally(e -> new Result(result, e.getMessage()));
        System.out.println("------> "+ mainFuture.get());
    }

    private CompletableFuture<String> startLongRunningOperation() {
        return CompletableFuture.supplyAsync(this::getHelloData).exceptionally(Throwable::getMessage);
    }

    private CompletableFuture<String> endLongRunningOperation() {
        return CompletableFuture.supplyAsync(this::getWorldData)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return e.getMessage();
                });
        /*.handle((msg,e) -> {
            //System.err.println(e);
            System.out.println("================================================="+msg);
            return msg;
        });*/
    }

    private String getHelloData(){
        try {
            Thread.sleep(1000);
            throw new RuntimeException("Hello-Error");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //return "Hello";
    }

    private String getWorldData() {
        try {
            Thread.sleep(1000);
            throw new RuntimeException("Error");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //return "World";
    }

    public static class Result {
        private List<String> payload;
        private String status;

        public Result(List<String> payload, String status) {
            super();
            this.payload = payload;
            this.status = status;
        }

        public List<String> getPayload() {
            return payload;
        }

        public void setPayload(List<String> payload) {
            this.payload = payload;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Result [payload=" + payload + ", status=" + status + "]";
        }

    }

}
