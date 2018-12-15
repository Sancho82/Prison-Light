package prisonLight;

// A számláló rab, aki mindent tud, amit a sima rab, plusz számolja, hogy mennyi jelet látott eddig.
public class CounterPrisoner extends Prisoner{

    private int headCount = 0;

    public int getHeadCount() {
        return headCount;
    }

    public void increaseHeadCount() {
        this.headCount++;
    }

    public String toString() {
        return super.toString() + "\t I'm the Counter.";
    }
}
