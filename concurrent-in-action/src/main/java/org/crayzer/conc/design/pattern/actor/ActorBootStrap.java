package org.crayzer.conc.design.pattern.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class ActorBootStrap {

    static class HelloActor extends UntypedAbstractActor {
        @Override
        public void onReceive(Object message) throws Throwable, Throwable {
            System.out.println("Hello " + message);
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("HelloSystem");
        ActorRef helloActor = system.actorOf(Props.create(HelloActor.class));
        helloActor.tell("Actor", ActorRef.noSender());
    }
}
