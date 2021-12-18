package com.rahul.lld.Utils;


import javafx.util.Pair;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Implementation of Singleton Queue [ThreadSafe, ReflectionSafe, SerializationSafe]
 */

public class SharedQueue  {
    private ConcurrentLinkedQueue<Pair<URL,Integer>> queue;
    private Set<String> urlVisited;
    private static volatile SharedQueue sharedQueue;

    private SharedQueue(){
        if(sharedQueue!=null)
            throw new RuntimeException("Use SharedQueue.getInstance() to obtain SharedQueue instance");
        this.queue = new ConcurrentLinkedQueue<>();
        this.urlVisited = new HashSet<>();
    }

    public static SharedQueue getInstance(){
        if(sharedQueue==null){
            synchronized (SharedQueue.class){
                if(sharedQueue==null)
                    sharedQueue = new SharedQueue();
            }
        }
        return sharedQueue;
    }
    protected SharedQueue readResolve(){
        return getInstance();
    }

    public synchronized  void put(URL url,Integer level) { this.queue.add(new Pair(url,level)); }

    public synchronized void visitURL(URL url){ this.visitURL(url.toString()); }

    public synchronized void visitURL(String url) { this.urlVisited.add(url); }

    public synchronized  Pair<URL,Integer> get(){
        return this.queue.poll();
    }

    public synchronized boolean empty(){
        return this.queue.isEmpty();
    }

    public boolean alreadyVisited(URL url){
        return alreadyVisited(url.toString());
    }

    public boolean alreadyVisited(String url){
        return urlVisited.contains(url);
    }
}
