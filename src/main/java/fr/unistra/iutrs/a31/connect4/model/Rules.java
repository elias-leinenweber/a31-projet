package fr.unistra.iutrs.a31.connect4.model;

public final class Rules {
    private static int COLUMNS = 7;
    private static final int ROWS = 6;
    private static int IN_A_ROW = 4;

    public static void fourInARow() {
        COLUMNS = 7;
        IN_A_ROW = 4;
    }

    public static void fiveInARow() {
        COLUMNS = 9;
        IN_A_ROW = 5;
    }

    public static int getColumns() {
        return COLUMNS;
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getInARow() {
        return IN_A_ROW;
    }

    /** Rend la classe non instantiable. */
    private Rules() {}
}
