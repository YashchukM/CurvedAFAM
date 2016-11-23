import java.util.*;

public class Partition {
    private List<List<Set<Integer>>> levels;

    public Partition() {
        this.levels = new ArrayList<>();
    }

    public void addLevel(Set<Integer> level) {
        List<Set<Integer>> newLevel = new ArrayList<>();
        newLevel.add(level);
        levels.add(newLevel);
    }

    public int getSize() {
        return levels.size();
    }

    public void setLevel(int levelPosition, List<Set<Integer>> level) {
        levels.set(levelPosition, level);
    }

    public List<Set<Integer>> getLevel(int position) {
        return levels.get(position);
    }

    public Set<Integer> getLevelSet(int position) {
        Set<Integer> levelSet = new HashSet<>();
        for (Set<Integer> subSet: levels.get(position)) {
            levelSet.addAll(subSet);
        }
        return levelSet;
    }

    public void removeFromLevel(int position, Collection<Integer> toRemove) {
        for (Set<Integer> subSet: levels.get(position)) {
            subSet.removeAll(toRemove);
        }
    }

    @Override
    public String toString() {
        String ls = System.lineSeparator();
        char tab = '\t';

        StringBuilder sb = new StringBuilder("Partitiion [");
        if (!levels.isEmpty()) sb.append(ls);
        for (int i = 0; i < levels.size(); i++) {
            sb.append(tab).append(i).append(": ").append(Arrays.toString(levels.get(i).toArray())).append(ls);
        }
        sb.append(']').append(ls);

        return sb.toString();
    }
}
