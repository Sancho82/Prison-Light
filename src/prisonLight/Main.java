package prisonLight;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Prisoner> prisonerList = new ArrayList<>();
    public static YardLamp yardlamp = new YardLamp();

    public static int numberOfPrisonersWhoHaveAlreadyBeen = 0;

    public static void main (String[] args) {

        prisonLoader(50);
        // showPrisonerList();

        // ez számolja hányszor viszik ki a rabokat összesen, mire szól a számoló rab, hogy mindenki tuti volt
        int round = 0;

        while (countersHeadCountAtTheMoment() != prisonerList.size()) {
            round++;
            System.out.println("Step " + round);
            takeSomeBastardForAWalk();
            System.out.println("\t\t" + numberOfPrisonersWhoHaveAlreadyBeen + " prisoners have been so far.");
            System.out.println("\t\t" + countersHeadCountAtTheMoment() + " got counted so far.\n");
        }

        System.out.println("(Finished in " + round + " rounds with all " + numberOfPrisonersWhoHaveAlreadyBeen + " prisoners.)\n");
        System.out.println("Guard Barry: The Counter says all " + countersHeadCountAtTheMoment() + " have been outside.");
        System.out.println("Guard Larry: Lemme' check... yep, he's right, they've all been at least once.");
        System.out.println("Guard Barry: About time, that was " + round + " rounds, damn it. So who walked most?");
        System.out.println("Guard Larry: Hang on, checking...\n");
        showPrisonerList();
        prisonerList.sort(new SortByWalks());
        System.out.print("\nGuard Larry: Right, here we go... it' number " + prisonerList.get(prisonerList.size() - 1).getSerial() + ".");
        System.out.println(" We took the bastard outside a " + prisonerList.get(prisonerList.size() - 1).getHowManyTimes() + " times. Ha ha ha...");
        System.out.println("Guard Barry: Ah common, he loves it. Ha ha ha...");
    }

    // Ez a függvény tölti fel a börtönt rabokkal, majd létrehoz egy számláló rabot és beteszi őt is random sorszámmal.
    public static void prisonLoader(int numberOfPrisoners) {
        int i = 1;
            while ( i <= numberOfPrisoners) {
                Prisoner prisoner = new Prisoner();
                prisoner.setSerial(i);
                prisonerList.add(prisoner);
                i++;
            }
        int whichIsTheCounter = (int)(Math.random() * numberOfPrisoners);
        CounterPrisoner counterPrisoner = new CounterPrisoner();
        counterPrisoner.setSerial(whichIsTheCounter + 1);
        prisonerList.set(whichIsTheCounter, counterPrisoner);
    }

    /* Ez a sétáltató függvény
          A számláló rab ha égő lámpát lát, lekapcsolja és számol egyet, fel sose kapcsol.
            Ha ő előszer kerül ki és nem ég a lámpa, akkor se kapcsolja fel, csak magára is számol egyet
          A többi rab, ha először van kinn és nem ég a lámpa, felkapcsolja.
            Ha már ég, akkor legközelebb kapcsolja fel, mikor kikerül és a lámpa épp nem ég.
            Le sose kapcsol.
    */
    public static void takeSomeBastardForAWalk() {
        int luckyStrike = (int)(Math.random() * prisonerList.size());   // Random szám
        Prisoner mrX = prisonerList.get(luckyStrike);                   // Ő a random szám alapján kiválasztott rab
        // Ha a számláló rab kerül ki, még nem volt kinn és a lámpa nem ég.
        if (mrX instanceof CounterPrisoner && !mrX.getWasOut() && !yardlamp.getIsOn()) {
            mrX.setWasOut();
            mrX.addToWalkTimes();
            numberOfPrisonersWhoHaveAlreadyBeen++;
            ((CounterPrisoner) mrX).increaseHeadCount();
            System.out.println("\t Case 1. Counter (num" + mrX.getSerial() + "): First time, no signal, counted myself only (light was off).");

        // Ha a számláló rab kerül ki, még nem volt kinn, de a lámpa ég.
        } else if (mrX instanceof CounterPrisoner && !mrX.getWasOut() && yardlamp.getIsOn()) {
            mrX.setWasOut();
            mrX.addToWalkTimes();
            numberOfPrisonersWhoHaveAlreadyBeen++;
            yardlamp.turnOff();
            ((CounterPrisoner) mrX).increaseHeadCount();
            ((CounterPrisoner) mrX).increaseHeadCount();
            System.out.println("\t Case 2. Counter (num" + mrX.getSerial() + "): First time, counted new signal and myself (light was on, switched it off).");

        // Ha a számláló rab kerül ki, már volt kinn és a lámpa ég.
        } else if (mrX instanceof CounterPrisoner && mrX.getWasOut() && yardlamp.getIsOn()) {
            mrX.addToWalkTimes();
            yardlamp.turnOff();
            ((CounterPrisoner) mrX).increaseHeadCount();
            System.out.println("\t Case 3. Counter (num" + mrX.getSerial() + "): Not the first time, counted new signal (light was on, switched it off).");

        // Ha a számláló rab kerül ki, már volt kinn és a lámpa nem ég.
        } else if (mrX instanceof CounterPrisoner && mrX.getWasOut() && !yardlamp.getIsOn()) {
            mrX.addToWalkTimes();
            System.out.println("\t Case 4. Counter (num" + mrX.getSerial() + "): Not the first time, no signal (light was off).");

        // Ha mezei rab kerül ki, még nem volt kinn és a lámpa nem ég.
        } else if (!(mrX instanceof CounterPrisoner) && !mrX.getWasOut() && !yardlamp.getIsOn()) {
            mrX.setWasOut();
            mrX.addToWalkTimes();
            numberOfPrisonersWhoHaveAlreadyBeen++;
            yardlamp.turnOn();
            mrX.setSwitchedOnOnce();
            System.out.println("\t Case 5. Prisoner (num" + mrX.getSerial() + "): First time, succesful signaling, light was off.");

        // Ha mezei rab kerül ki, még nem volt kinn és a lámpa ég.
        } else if (!(mrX instanceof CounterPrisoner) && !mrX.getWasOut() && yardlamp.getIsOn()) {
            mrX.setWasOut();
            mrX.addToWalkTimes();
            numberOfPrisonersWhoHaveAlreadyBeen++;
            System.out.println("\t Case 6. Prisoner (num" + mrX.getSerial() + "): First time, but no chance for signaling, light was on.");

        // Ha mezei rab kerül ki, már volt kinn, a lámpa ég, de már korábban tudta jelezni, hogy volt kinn.
        } else if (!(mrX instanceof CounterPrisoner) && mrX.getWasOut() && yardlamp.getIsOn() && (mrX.getSwitchedOnOnce() == 1)) {
            mrX.addToWalkTimes();
            System.out.println("\t Case 7. Prisoner (num" + mrX.getSerial() + "): Not the first time, can't signal, but no need, signalled before (light was on).");

        // Ha mezei rab kerül ki, már volt kinn, a lámpa nem ég, de már korábban tudta jelezni, hogy volt kinn.
        } else if (!(mrX instanceof CounterPrisoner) && mrX.getWasOut() && !yardlamp.getIsOn() && (mrX.getSwitchedOnOnce() == 1)) {
            mrX.addToWalkTimes();
            System.out.println("\t Case 8. Prisoner (num" + mrX.getSerial() + "): Not the first time, could have signalled, but no need, done it before (light was off).");

        // Ha mezei rab kerül ki, már volt kinn, a lámpa ég, de már korábban sem tudta jelezni, hogy volt kinn.
        } else if (!(mrX instanceof CounterPrisoner) && mrX.getWasOut() && yardlamp.getIsOn() && (mrX.getSwitchedOnOnce() == 0)) {
            mrX.addToWalkTimes();
            System.out.println("\t Case 9. Prisoner (num" + mrX.getSerial() + "): Not the first time, needed to signal, but couldn't (light was on).");

        // Ha mezei rab kerül ki, már volt kinn, a lámpa nem ég és korábban nem tudta jelezni, hogy volt kinn.
        }  else if (!(mrX instanceof CounterPrisoner) && mrX.getWasOut() && !yardlamp.getIsOn() && (mrX.getSwitchedOnOnce() == 0)) {
            yardlamp.turnOn();
            mrX.setSwitchedOnOnce();
            mrX.addToWalkTimes();
            System.out.println("\t Case 10. Prisoner (num" + mrX.getSerial() + "): Not the first time, needed to signal, successfully signalled (light was off).");
        }
    }

    // Ez a lista függvény, ami kírja az összes rab adatait.
    public static void showPrisonerList() {
        for (Prisoner prisoner: prisonerList) {
            System.out.println(prisoner.toString());
        }
    }

    // Ez a füügvény visszaadja, hogy a számláló rab mennyi jelet tudott megszámolni, mielőtt meghívtuk ez a függvényt.
    public static int countersHeadCountAtTheMoment() {
        int headCountAtTheMoment = 0;
        for (Prisoner prisoner : prisonerList) {
            if (prisoner instanceof CounterPrisoner) {
                headCountAtTheMoment = ((CounterPrisoner)(prisoner)).getHeadCount();
            }
        }
        return headCountAtTheMoment;
    }
}

