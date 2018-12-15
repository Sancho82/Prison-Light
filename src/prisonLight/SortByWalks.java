package prisonLight;

import java.util.Comparator;

// Comporator, ami bármely két rabot képes összehasonlítani az alapján, hogy hányszor voltak kinn.
public class SortByWalks implements Comparator<Prisoner>{

    @Override
    public int compare(Prisoner p1, Prisoner p2) {
        return p1.getHowManyTimes() - p2.getHowManyTimes();
    }
}
