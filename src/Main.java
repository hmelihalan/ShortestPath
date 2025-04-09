import java.util.*;

public class Main {
    static char[][] board; // Represent the board as a 2D char array

    public static void main(String[] args) {
        Graph graph = new Graph();  //Class that initiates Bfs
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine(); //Input from user

        int row = Character.getNumericValue(text.charAt(0));
        int col = Character.getNumericValue(text.charAt(1));

        int knightRow = 0;
        int knightCol = 0;

        if(row<=9&&row>=2) System.out.println("Row number must be between 2 and 9"); //Handling Row and Column's edge case
        if(col<=9&&col>=2) System.out.println("Column number must be between 2 and 9");


        board = new char[row][col]; //Creating the board

        int goldCount = 0;
        int knightCount = 0;


        for (int i = 0; i < row; i++) { //Filling the board with '.'
            Arrays.fill(board[i], '.');

            if(text.charAt(i) == 'G') goldCount++;
            if(text.charAt(i) == 'K') knightCount++;
        }

        if(goldCount > 1) System.out.println("There's more than one gold on the board");
        if(knightCount > 1) System.out.println("There's more than one knight on the board");

        for(int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (c == 'T' || c == 'K' || c == 'G') { // Placing trees , knight , and gold
                int currentRow = (i - 2) / col;// Calculating row index
                int currentCol = (i - 2) % col;// Calculating column index
                board[currentRow][currentCol] = c;
                if(c =='K') {
                    knightRow= currentRow; // Keeping the knight's starting index
                    knightCol = currentCol;
                }
            }
        }

        graph.bfs(knightRow,knightCol,row,col,board);


    }
}
class Node{ //Linked list node
    int x,y,steps;
    Node parent;

    public Node(int x, int y,int steps , Node start) {
        this.x = x;
        this.y = y;
        this.steps = steps;
        this.parent = start;
    }
}

class Graph {
    int[][] directions = {  // every move that knight can make
            {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}
    };

    public void bfs(int knightRow, int knightCol, int row, int col, char[][] board) {
        Queue<Node> queue = new LinkedList<>(); //I used queue of linked lists to keep track after queuing each availible step
        boolean[][] visited = new boolean[row][col]; //A bool variable for keeping track for the each visited move

        visited[knightRow][knightCol] = true;//Visiting the knight's starting coordinate
        queue.add(new Node(knightRow,knightCol,0,null)); //Adding knight's first position to queue to start

        while (!queue.isEmpty()) {  //Entering while loop until queue is empty
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;  //Keeping coordinates with variables
            int steps = current.steps;

            if (board[x][y] == 'G') {  //Print the steps and the path if gold found

                printPath(current);
                return;
            }

            for (int[] direction : directions) { //Trying every move that the Knight can make
                int X = x + direction[0];
                int Y = y + direction[1];

                //If the move encounters an illegal move skip to the next move
                if (X < 0 || Y < 0 || X >= row || Y >= col || board[X][Y] == 'T' || visited[X][Y])
                    continue;

                //If it doesn't encounter a move that knight can't make add the new position to queue
                queue.add(new Node(X,Y,steps+1,current));
                //Mark the position visited
                visited[X][Y] = true;
            }
        }

        //If bfs couldn't find the gold
        System.out.println("No possible path to the gold.");
    }
    private void printPath(Node lastNode) { //Printing the path to the gold
        LinkedList<String> path = new LinkedList<>();

        while (lastNode != null) { //Iterate through the en of the list
            int nodeX =lastNode.x + 1; //Adding 1 to the indexes for actual coordinates
            int nodeY =lastNode.y + 1;
            path.addFirst("(" + nodeX + "," + nodeY + ")"); //Add converted indexes to the list
            lastNode = lastNode.parent;
        }


        for (int i = 0; i < path.size(); i++) {  //Print the linked list that we converted
            String p = path.get(i);
            if (i == path.size() - 1) {
                System.out.print(p);
                continue;
            }
            System.out.print(p + " -> ");


        }
    }
}