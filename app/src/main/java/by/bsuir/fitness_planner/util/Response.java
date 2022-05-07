package by.bsuir.fitness_planner.util;

public enum Response {
    DONE("HEY! You Are The Winner\nOf Today"),
    ALMOST_DONE("Your Daily Tasks\nAlmost Done!"),
    KEEP_GOING("You Have To\nPush Harder!"),
    PLEASE_STOP("OAH MY GOD!\nPLEASE, STOP!!!");

    Response(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
