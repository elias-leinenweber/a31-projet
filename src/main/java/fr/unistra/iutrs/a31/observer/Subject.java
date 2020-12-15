package fr.unistra.iutrs.a31.observer;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class Subject {
    private final List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public void register(Observer o) {
        observers.add(requireNonNull(o));
    }

    public void unregister(Observer o) {
        observers.remove(o);
    }

    protected void notifyObservers() {
        observers.forEach(Observer::update);
    }

    protected final List<Observer> getObservers() {
        return observers;
    }

    protected final void clearObservers() {
        observers.clear();
    }
}
