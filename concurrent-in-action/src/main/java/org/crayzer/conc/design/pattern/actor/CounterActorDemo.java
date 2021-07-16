package org.crayzer.conc.design.pattern.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterActorDemo {

    static class CountActor extends UntypedAbstractActor {
        private int counter = 0;

        @Override
        public void onReceive(Object message) throws Throwable, Throwable {
            if (message instanceof Number) counter += ((Number) message).intValue();
            else System.out.println(counter);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("HelloSystem");
        ExecutorService es = Executors.newFixedThreadPool(4);
        ActorRef counterActor = system.actorOf(Props.create(CountActor.class));
        //生产4*100000个消息
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                for (int j = 0; j < 100000; j++) {
                    counterActor.tell(1, ActorRef.noSender());
                }
            });
        }
        es.shutdown();
        Thread.sleep(1000);
        counterActor.tell("", ActorRef.noSender());
        system.stop(counterActor);
    }
}
