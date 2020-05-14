package dev.swayamraina.flashcard.core;

import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.storage.Storage;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service public class QueueHandler <E> {


    private Storage storage;
    private Queue<E> queue;
    private Pendulum pendulum;
    private Thread daemon;


    private Runnable task = () -> {
        SCode code;
        while (true) {
            E card = queue.poll();
            if (Objects.nonNull(card)) {
                code = storage.add((FlashCard) card);
                if (SCode.ERROR == code) {
                    queue.failed().add(card);
                }
            }
            try { Thread.sleep(pendulum.time()); } catch (InterruptedException e) {}
        }
    };


    @Autowired public QueueHandler (Storage storage) {
        this.storage = storage;
        this.queue = new Queue<>();
        this.pendulum = new Pendulum();

        this.daemon = new Thread(task);
        this.daemon.start();
    }


    public QCode handle (E element) {
        return queue.add(element);
    }


}
