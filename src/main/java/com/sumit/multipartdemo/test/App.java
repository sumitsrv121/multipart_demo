package com.sumit.multipartdemo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        List<CompletableFuture<Integer>> futures = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(App::calculate).collect(Collectors.toList());

        CompletableFuture<Integer>[] arr = new CompletableFuture[futures.size()];

        List<Integer> results = new ArrayList<>();
        CompletableFuture<Result> mainFuture = CompletableFuture.allOf(futures.toArray(arr))
                .thenApply(notUsed -> {
                    futures.forEach(f -> {
                        results.add(f.join());
                    });
                    return new Result(results, "OK");
                }).exceptionally(e -> new Result(results, e.getMessage()));

        System.out.println("Main " + mainFuture.get() + " | " + (System.currentTimeMillis() - start));
    }

    public static CompletableFuture<Integer> calculate(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            // System.out.println("Start " + id + " | " + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			if (id == 3)
				throw new RuntimeException("boom");
            System.out.println("Done " + id);
            return id;
        });
    }

    public static class Result {
        private List<Integer> payload;
        private String status;

        public Result(List<Integer> payload, String status) {
            super();
            this.payload = payload;
            this.status = status;
        }

        public List<Integer> getPayload() {
            return payload;
        }

        public void setPayload(List<Integer> payload) {
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
