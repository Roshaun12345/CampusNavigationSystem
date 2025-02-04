The Campus Navigation System is designed to help navigate a campus by analyzing a graph of buildings (nodes) connected by paths (edges). The system reads input from a file containing information about buildings and paths, then allows users to perform graph-based operations like Depth First Search (DFS), Breadth First Search (BFS), and Minimum Spanning Tree (MST) using Kruskal’s algorithm.

Features
DFS (Depth First Search): Traverse the campus starting from a building using depth-first search and display the buildings visited.
BFS (Breadth First Search): Traverse the campus starting from a building using breadth-first search and display the buildings visited.
MST (Minimum Spanning Tree): Find and display the minimum spanning tree of the campus map using Kruskal's algorithm.
Input Validation: The system validates if all buildings and paths are correct based on the provided data in the file.

To run this program, ensure that you have a text file containing building and edge information in the required format (described below).

Input File Format
The input file must follow the structure below:

Nodes (Buildings):A list of building names, each on a separate line, followed by the word EDGES to indicate the start of edge data.
Example;

Building A

Building B

Building C

EDGES


Edges (Paths between buildings):Each line should contain two building names and the time it takes to travel between them, separated by commas.
Example;

Building A, Building B, 5

Building B, Building C, 10

Building A, Building C, 15


