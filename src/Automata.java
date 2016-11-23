import java.util.*;

public class Automata {
    public static final int INDEFINITE = Integer.MAX_VALUE;
    private Set<Character> alphabet;

    private Set<Integer> states;
    private Map<Integer, Map<Character, Integer>> transitions;

    public Automata() {
        this.states = new HashSet<>();
        this.transitions = new HashMap<>();
        this.alphabet = new HashSet<>();
        for (char c = 'a'; c < 'z'; c++) {
            alphabet.add(c);
        }
    }

    public Automata(Set<Character> alphabet) {
        this();
        this.alphabet = alphabet;
    }

    public void addStates(int n) {
        for (int i = 0; i < n; i++) {
            states.add(i);
        }
    }

    public void addStates(Integer[] s) {
        states.addAll(Arrays.asList(s));
    }

    public void addTransitionsForState(int state, Map<Character, Integer> tran) throws Exception {
        if (states.contains(state)) {
            if (!alphabet.containsAll(tran.keySet())) {
                throw new Exception("Forbidden characters in transition");
            }
            transitions.put(state, tran);
        } else {
            throw new Exception("Forbidden state in transition");
        }
    }

    public int goTransition(int state, char inp) {
        if (transitions.containsKey(state) && transitions.get(state).containsKey(inp)) {
            return transitions.get(state).get(inp);
        } else {
            return INDEFINITE;
        }
    }

    public Set<Integer> usedStates() {
        return transitions.keySet();
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public Set<Integer> getStates() {
        return states;
    }

    public Collection<Integer> getOutcomeStates(int state) {
        return transitions.get(state).values();
    }

    public Map<Integer, Map<Character, Integer>> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        String ls = System.lineSeparator();
        char tab = '\t';

        StringBuilder sb = new StringBuilder("Automata {" + ls);
        sb.append(tab).append("alphabet: ").append(Arrays.toString(alphabet.toArray())).append(ls);
        sb.append(tab).append("states: ").append(Arrays.toString(states.toArray())).append(ls);
        sb.append(tab).append("transitions: [");
        for (Map.Entry<Integer, Map<Character, Integer>> entry: transitions.entrySet()) {
            sb.append(ls).append(tab).append(tab).append(entry.getKey()).append(": [");
            for (Map.Entry<Character, Integer> tr : entry.getValue().entrySet()) {
                sb.append(tr.getKey()).append("->").append(tr.getValue()).append(", ");
            }
            if (!entry.getValue().isEmpty()) sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append("]");
        }
        if (!transitions.isEmpty()) sb.append(ls).append(tab);

        sb.append("]").append(ls);
        sb.append("}");
        return sb.toString();
    }
}
