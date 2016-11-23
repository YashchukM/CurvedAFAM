import java.util.*;

public class Minimizer {
    public static void main(String[] args) throws Exception {
        Automata automata = new Automata();
        automata.addStates(12);

        Map<Character, Integer> tr = new HashMap<>();
        tr.put('a', 1);
        tr.put('b', 2);
        automata.addTransitionsForState(0, tr);

        tr = new HashMap<>();
        tr.put('c', 3);
        tr.put('d', 4);
        automata.addTransitionsForState(1, tr);

        tr = new HashMap<>();
        tr.put('c', 5);
        tr.put('d', 6);
        automata.addTransitionsForState(2, tr);

        tr = new HashMap<>();
        tr.put('e', 11);
        automata.addTransitionsForState(3, tr);

        tr = new HashMap<>();
        tr.put('f', 11);
        automata.addTransitionsForState(4, tr);

        tr = new HashMap<>();
        tr.put('c', 7);
        tr.put('d', 8);
        automata.addTransitionsForState(5, tr);

        tr = new HashMap<>();
        tr.put('c', 9);
        tr.put('d', 10);
        automata.addTransitionsForState(6, tr);

        tr = new HashMap<>();
        tr.put('e', 11);
        automata.addTransitionsForState(7, tr);

        tr = new HashMap<>();
        tr.put('f', 11);
        automata.addTransitionsForState(8, tr);

        tr = new HashMap<>();
        tr.put('e', 11);
        automata.addTransitionsForState(9, tr);

        tr = new HashMap<>();
        tr.put('f', 11);
        automata.addTransitionsForState(10, tr);

        minimize(automata);
    }

    public static void minimize(Automata automata) {
        System.out.println(automata);

        Partition partition = getMinimizedPartition(automata);
        System.out.println("Partition after minimization: " + partition);

        for (int i = 0; i < partition.getSize(); i++) {
            for (Set<Integer> subClass: partition.getLevel(i)) {
                if (subClass.size() > 1) {
                    Iterator<Integer> iterator = subClass.iterator();
                    Integer main = iterator.next();

                    while (iterator.hasNext()) {
                        Integer duplicate = iterator.next();
                        removeDuplicate(automata, main, duplicate);
                    }
                }
            }
        }

        System.out.println(automata);
    }

    private static Partition getMinimizedPartition(Automata automata) {
        Partition partition = Partitioner.getPartition(automata);
        System.out.println("Partition before minimization: " + partition);

        for (int i = 0; i < partition.getSize(); i++) {
            Set<Integer> partK = partition.getLevelSet(i);
            for (Character x: automata.getAlphabet()) {
                for (int j = i + 1; j < partition.getSize(); j++) {
                    List<Set<Integer>> partL = partition.getLevel(j);
                    int partLSize = partL.size();

                    // TODO: change
                    for (int subclassIndex = 0; subclassIndex < partLSize; subclassIndex++) {
                        Set<Integer> subClassL = partL.get(subclassIndex);
                        Set<Integer> belongsK = new HashSet<>();
                        Set<Integer> notBelongsK = new HashSet<>();

                        for (Integer a: subClassL) {
                            if (partK.contains(automata.goTransition(a, x))) {
                                belongsK.add(a);
                            } else if (!partK.contains(automata.goTransition(a, x))) {
                                notBelongsK.add(a);
                            }
                        }

                        if (!belongsK.isEmpty() && !notBelongsK.isEmpty()) {
                            partL.set(subclassIndex, belongsK);
                            partL.add(notBelongsK);
                        }
                    }
                }
            }
        }
        return partition;
    }

    private static void removeDuplicate(Automata automata, Integer main, Integer duplicate) {
        Map<Integer, Map<Character, Integer>> transitions = automata.getTransitions();

        transitions.get(main).putAll(transitions.get(duplicate));
        transitions.remove(duplicate);
        for (Map<Character, Integer> transition: transitions.values()) {
            for (Map.Entry<Character, Integer> entry: transition.entrySet()) {
                if (entry.getValue().equals(duplicate)) {
                    entry.setValue(main);
                }
            }
        }
    }
}
