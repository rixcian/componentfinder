package mendelu.xkremece;

import java.util.ArrayList;
import java.util.List;

public class Node extends Throwable{

    private String name;
    private List<Node> neighbours = new ArrayList<Node>();

    public Node(String name) {
        this.name = name;
    }

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public String getName() {
        return name;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    // Prida novy sousedni uzel
    public boolean newNeighbour(Node neighbourNode) {
        if (!containsNeighbour(neighbourNode)) {
            this.neighbours.add(neighbourNode);
            return true;
        } else {
            // Pridavany prvek uz je pridany
            return false;
        }
    }

    // Zjisti zda zadany uzel uz nesousedi s timto uzlem
    private boolean containsNeighbour(Node testedNode) {
        for (int i = 0; i < this.neighbours.size(); i++) {
            if (this.neighbours.get(i).getName().equals(testedNode.getName())) {
                return true;
            }
        }

        return false;
    }
}
