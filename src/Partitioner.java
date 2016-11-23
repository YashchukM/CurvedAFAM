import java.util.*;
import java.util.stream.Collectors;

public class Partitioner {
    private Partitioner() {}

    public static Partition getPartition(Automata automata) {
        Partition partition = new Partition();

        Set<Integer> prevLevel = null;
        Set<Integer> nextLevel = getFirstLevel(automata);
        Set<Integer> prevUnion = new HashSet<>();

        do {
            partition.addLevel(nextLevel);
            prevUnion.addAll(nextLevel);
            prevLevel = nextLevel;
            nextLevel = getNextLevel(automata, prevLevel, prevUnion);
        } while (!nextLevel.isEmpty() && !prevLevel.equals(nextLevel));

        return partition;
    }

    private static Set<Integer> getFirstLevel(Automata automata) {
        Set<Integer> allStates = automata.getStates();
        Set<Integer> usedStates = automata.usedStates();

        return allStates.stream()
                .filter(state -> !usedStates.contains(state))
                .collect(Collectors.toSet());
    }

    private static Set<Integer> getNextLevel(Automata automata, Set<Integer> prevLevel, Set<Integer> prevUnion) {
        Set<Integer> usedStates = automata.usedStates();
        Set<Integer> nextLevel = new HashSet<>();

        for (Integer state: usedStates) {
            if (belongsToNextLevel(automata.getOutcomeStates(state), prevLevel, prevUnion)) {
                nextLevel.add(state);
            }
        }

        return nextLevel;
    }

    private static boolean belongsToNextLevel(Collection<Integer> outcomeStates,
                                              Set<Integer> prevLevel, Set<Integer> prevUnion) {
        if (!prevUnion.containsAll(outcomeStates)) {
            return false;
        } else {
            for (Integer state: outcomeStates) {
                if (prevLevel.contains(state)) {
                    return true;
                }
            }
            return false;
        }
    }
}
