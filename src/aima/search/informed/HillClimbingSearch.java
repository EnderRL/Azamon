package aima.search.informed;

import java.util.ArrayList;
import java.util.List;

import IA.Estado;
import IA.Heuristicos.FuncionHeuristica;
import aima.search.framework.Node;
import aima.search.framework.NodeExpander;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchUtils;
import aima.util.Pair;

/**
 * @author Ravi Mohan
 *  
 */
public class HillClimbingSearch extends NodeExpander implements Search {
 private Object goalState;
	private ArrayList<Double> costePaso;
 private Node lastNode;
	public List search(Problem p) throws Exception {
		costePaso = new ArrayList<Double>();
		clearInstrumentation();
		int numpasos = 0;
		Node current = new Node(p.getInitialState());
		Node neighbor = null;
		while (true) {
			if (numpasos%5 == 0) {
				costePaso.add(numpasos/1.);
				costePaso.add(((Estado)current.getState()).getPrecio());
			}
			List children = expandNode(current, p);
			neighbor = getHighestValuedNodeFrom(children, p);
			if ((neighbor == null)
					|| (getValue(neighbor, p) <= getValue(current, p))) {
                                goalState = current.getState();

				return SearchUtils.actionsFromNodes(current.getPathFromRoot());
			}
			current = neighbor;
			++numpasos;
		}

	}

        public Object getGoalState(){
            return(goalState);
        }

	public ArrayList<Double> getCostePaso() {return costePaso;}
        
	private Node getHighestValuedNodeFrom(List children, Problem p) {
		double highestValue = Double.NEGATIVE_INFINITY;
		Node nodeWithHighestValue = null;
		for (int i = 0; i < children.size(); i++) {
			Node child = (Node) children.get(i);
			double value = getValue(child, p);
			if (value > highestValue) {
				highestValue = value;
				nodeWithHighestValue = child;
			}
		}

		return nodeWithHighestValue;
	}

	private double getHeuristic(Node aNode, Problem p) {

		return p.getHeuristicFunction().getHeuristicValue(aNode.getState());
	}

	private double getValue(Node n, Problem p) {

		return -1 * getHeuristic(n, p); //assumption greater heuristic value =>
		// HIGHER on hill; 0 == goal state;
	}
        
        public List getPathStates(){
            return SearchUtils.statesFromNodes(lastNode.getPathFromRoot());
        }

}