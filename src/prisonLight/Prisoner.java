package prisonLight;

// A mezei rab.
public class Prisoner{
    private boolean wasOut = false;     // Ez az adattag azt mutatja, hogy volt e már kinn a rab.
    private int switchedOnOnce = 0;     // Ez az adattag azt mutatja, hogy ha volt már kinn, tudott e jelezni (ha igen, értéke 1 lesz, kivéve a számláló rabnak, nála mindig 0 marad.).
    private int serial;                 // Ez az adattag a rab egyedi sorszámát tartalmazza, mellyel őt azonosítani lehet.
    private int walkTimes = 0;          // Ez az adattag azt mutatja, hányszor volt már kinn a rab.

    public boolean getWasOut() {
        return wasOut;
    }

    public int getSwitchedOnOnce() {
        return this.switchedOnOnce;
    }

    public int getSerial() {
        return this.serial;
    }

    public int getHowManyTimes() {
        return this.walkTimes;
    }

    public void setWasOut() {
        this.wasOut = true;
    }

    // Ez a setter nem áttállítja az adatagot, amire vonatkozik, hanem növeli eggyel.
    public void setSwitchedOnOnce() {
        this.switchedOnOnce++;
    }

    public void setSerial(int szam) {
        this.serial = szam;
    }

    // Ez a setter nem áttállítja az adatagot, amire vonatkozik, hanem növeli eggyel.
    public void addToWalkTimes() {
        this.walkTimes++;
    }

    public String toString() {
        return "serial: " + this.getSerial() + " \t was already outside? : " + this.getWasOut() +
                "\t switchedOnOnce: " + this.getSwitchedOnOnce() + "\t walk-times: " + this.getHowManyTimes();
    }
}
