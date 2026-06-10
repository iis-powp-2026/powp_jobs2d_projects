package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.optionals.DecoratorDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;


public class DriverChainUtils {

    private DriverChainUtils() {}

    public static boolean isInChain(VisitableDriver root, VisitableDriver target) {
        VisitableDriver current = root;
        while (current != null) {
            if (current == target) {
                return true;
            }
            if (current instanceof DecoratorDriver) {
                current = ((DecoratorDriver) current).getTarget();
            } else {
                break;
            }
        }
        return false;
    }

    public static VisitableDriver removeFromChain(VisitableDriver root, DecoratorDriver toRemove) {
        if (root == toRemove) {
            return toRemove.getTarget();
        }
        if (root instanceof DecoratorDriver) {
            DecoratorDriver dec = (DecoratorDriver) root;
            dec.setTarget(removeFromChain(dec.getTarget(), toRemove));
        }
        return root;
    }
}
