package observateur;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class Sujet {

    private final List<Observateur> observers;

    public Sujet() {
        observers = new ArrayList<>();
    }

    public void register(Observateur o) {
        observers.add(requireNonNull(o));
    }

    public void unregister(Observateur o) {
        observers.remove(o);
    }

    protected void notifyObservers() {
        observers.forEach(Observateur::mettreAjour);
    }

    protected final List<Observateur> getObservers() {
        return observers;
    }

    protected final void clearObservers() {
        observers.clear();
    }
}
