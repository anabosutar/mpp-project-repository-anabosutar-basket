package utils;

import model.Bilet;

public class TaskEvent implements Event {
    private TaskEventType type;
    private Bilet task;
    public TaskEvent(TaskEventType type, Bilet task) {
        this.task=task;
        this.type=type;
    }

    public Bilet getTask() {
        return task;
    }

    public void setTask(Bilet task) {
        this.task = task;
    }

    public TaskEventType getType() {
        return type;
    }

    public void setType(TaskEventType type) {
        this.type = type;
    }
}
