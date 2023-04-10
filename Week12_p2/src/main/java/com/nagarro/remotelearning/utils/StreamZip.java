package com.nagarro.remotelearning.utils;

import java.util.Iterator;
import java.util.stream.Stream;

public class StreamZip {
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> secondIterator = second.iterator();
        Stream.Builder<T> streamBuilder = Stream.builder();
        first.forEach((element) -> {
                    if(secondIterator.hasNext()){
                        streamBuilder.accept(element);
                        streamBuilder.accept(secondIterator.next());
                    }
                    else {
                        first.close();
                    }
                }
        );
        return streamBuilder.build();
    }
}
