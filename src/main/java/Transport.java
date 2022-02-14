public enum Transport{
    FEW(3),
    NONE(0),
    LITTLE(1),
    ENOUGH(2);

    private final int count;

    Transport(int count) {
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
}
