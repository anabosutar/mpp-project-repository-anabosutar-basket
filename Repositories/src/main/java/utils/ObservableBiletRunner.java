package utils;

import model.Bilet;
import model.TaskExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObservableBiletRunner implements IBiletRunner, Observable<TaskEvent> {
    protected Container container;


    public ObservableBiletRunner(Container container) {
        this.container = container;
    }

    protected List<Observer<TaskEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<TaskEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<TaskEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(TaskEvent t) {
        for (Observer<TaskEvent> ovs : observers)
            ovs.update(t);

    }


    @Override
    public void executeOneBilet() {
        Bilet bilet = container.remove();
        notifyObservers(new TaskEvent(TaskEventType.StartingTaskExecution, bilet));
        bilet.execute();
        notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCompleted, bilet));
    }

    ExecutorService executor;

    @Override
    public void executeAll() {
        //while(!container.isEmpty())
        executor = Executors.newFixedThreadPool(container.size());
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Bilet t : container.getAll()) {
            Callable<Void> callable = () -> {
                try {
                    System.out.println("notificat observer a " + t.getId());
                    notifyObservers(new TaskEvent(TaskEventType.StartingTaskExecution, t));
                    // System.out.println("notificat observer b "+t.getTaskID());
                    t.execute();
                    // System.out.println("notificat observer c"+t.getTaskID());
                    notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCompleted, t));
                    System.out.println("notificat observer d" + t.getId());
                } catch (TaskExecutionException te) {
                    notifyObservers(new TaskEvent(TaskEventType.TaskExecutionCancelled, t));
                }
                return null;
            };
            tasks.add(callable);
        }
        try {
            executor.invokeAll(tasks).stream().map(future -> {
                try {
                    future.get();
                } catch (Exception e) {
                    System.out.println("eroare " + e);
                }
                return null;
            });
        } catch (InterruptedException ex) {
            System.out.println("executeAll interrupted ...");
        }

        executor.shutdown();
    }

    @Override
    public void addBilet(Bilet t) {
        container.add(t);
    }

    public void cancel() {
        if ((executor != null) && (!executor.isTerminated())) {
            executor.shutdown();
        }
    }

    public void close() {
        if ((executor != null) && (!executor.isTerminated())) {
            System.out.println("Shutting down executor ...");
            executor.shutdownNow();
        }
    }
}
