package card;

public enum Level {
    High_Card(1),
    Pair(2),
    Two_Pairs(3),
    Three_of_a_Kind(4),
    Straight(5),
    Flush(6),
    Full_House(7),
    Four_of_a_Kind(8),
    Straight_Flush(9);


    private int carLevel;

    Level(int carLevel) {
        this.carLevel = carLevel;
    }

    public int code() {
        return carLevel;
    }
}
