package prisonLight;

// Az udvari lámpa, mely egyetlen adattagjának 2 állapota lehet, fel vagy le van kapcsolva.
public class YardLamp {

    private boolean isOn = false;

    public boolean getIsOn() {
        return isOn;
    }

    public void turnOff() {
        isOn = false;
    }

    public void turnOn() {
        isOn = true;
    }
}
