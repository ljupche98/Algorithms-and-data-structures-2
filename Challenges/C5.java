import java.util.*;

/**
0 1 2
0 2 8
1 3 3
1 4 4
2 3 4
2 4 2
3 5 1
4 5 5
**/

public class C5
{
	public static Network G;
	
	public static boolean BFS()
	{
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		pq.add(0); G.nodes[0].marked = true;
		
		while (pq.size() > 0) {
			int u = pq.poll();
			
		/// System.out.printf("PQ: %d\n", u);
			
			for (Edge e : G.nodes[u].outEdges) {
				if (!G.nodes[e.endID].marked) {
					if (Math.min(e.capacity - e.currFlow, G.nodes[u].incFlow) > 0) {
					/// System.out.printf("%d -> %d\n", u, e.endID);
						int flow = Math.min(e.capacity - e.currFlow, G.nodes[u].incFlow);
						G.nodes[e.endID].sign = 1;
						G.nodes[e.endID].marked = true;
						G.nodes[e.endID].incFlow = flow;
						G.nodes[e.endID].augmEdge = e;
						G.nodes[u].incFlow = flow;
					/// System.out.printf("%d -> %d +\n", u, e.endID);
						pq.add(e.endID);
					}
				}
			}	
			
			for (Edge e : G.nodes[u].inEdges) {
				if (!G.nodes[e.startID].marked) {
					if (Math.min(e.currFlow, G.nodes[u].incFlow) > 0) {
						int flow = Math.min(e.currFlow, G.nodes[u].incFlow);
						G.nodes[e.startID].sign = -1;
						G.nodes[e.startID].marked = true;
						G.nodes[e.startID].incFlow = flow;
						G.nodes[e.startID].augmEdge = e;
						G.nodes[u].incFlow = flow;
					/// System.out.printf("%d -> %d -\n", e.startID, u);
						pq.add(e.startID);
					}
				}
			}
		}
		
		return G.nodes[G.N - 1].marked;
	}
	
	public static String sign2str(int x)
	{
		if (x < 0) return "-";
		if (x > 0) return "+";
		return "";
	}
	
	public static int getParent(int x)
	{
		if (x == G.nodes[x].augmEdge.startID) return G.nodes[x].augmEdge.endID;
		return G.nodes[x].augmEdge.startID;
	}
	
	public static void main(String[] args)
	{
		if (args.length < 1) return;
		G = new Network(Integer.valueOf(args[0]));
		
		Scanner cin = new Scanner(System.in);
		while (cin.hasNextInt()) G.addConnection(cin.nextInt(), cin.nextInt(), cin.nextInt());
		
		while (BFS()) {
			int u = G.N - 1;
			System.out.print(G.nodes[u].incFlow + ": ");
			while (u > 0) {
				System.out.print(u + sign2str(G.nodes[u].sign) + " ");
				G.nodes[u].augmEdge.currFlow += G.nodes[u].sign * G.nodes[G.N - 1].incFlow;
				u = getParent(u);
			}
			System.out.println(0);
			
			G.resetMarks();
		}
	}
}

class Node {	
	int id;
	//marks for the algorithm
	//------------------------------------
	int sign = 0;
	boolean marked = false;
	Edge augmEdge = null; //the edge over which we brought the flow increase
	int incFlow = 1 << 25; //-1 means a potentially infinite flow
	//------------------------------------
	ArrayList<Edge> inEdges;
	ArrayList<Edge> outEdges;
	
	public Node(int i) {
		id = i;
		inEdges = new ArrayList<Edge>();
		outEdges = new ArrayList<Edge>();
	}
}

class Edge {
	int startID; 
	int endID;
	int capacity; 
	int currFlow;
	
	public Edge(int fromNode, int toNode, int capacity2) {
		startID = fromNode;
		endID = toNode;
		capacity = capacity2;
		currFlow = 0;
	}
}

class Network {
	int N;
	Node[] nodes;
	
	/**
	 * Create a new network with n nodes (0..n-1).
	 * @param n the size of the network.
	 */
	public Network(int n) {
		N = n;
		nodes = new Node[n];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i]= new Node(i);
		}
	}
	/**
	 * Add a connection to the network, with all the corresponding in and out edges.
	 * @param fromNode
	 * @param toNode
	 * @param capacity
	 */
	public void addConnection(int fromNode, int toNode, int capacity) {
		Edge e = new Edge(fromNode, toNode, capacity);
		nodes[fromNode].outEdges.add(e);
		nodes[toNode].inEdges.add(e);
	}

	/**
	 * Reset all the marks of the algorithm, before the start of a new iteration.
	 */
	public void resetMarks() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].sign = 0;
			nodes[i].marked = false;
			nodes[i].augmEdge = null;
			nodes[i].incFlow = 1 << 25;
		}
	}
}