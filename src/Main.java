import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static int[][] arr = new int[10][10];
    static int[] visit = new int[10];
    static int[] queue = new int[10];
    static int front = 0, rear = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of movies and similarities:");
        int node = sc.nextInt();
        int edges = sc.nextInt();

        sc.nextLine(); // Consume the newline

        // Map to store movie names and their corresponding indices
        HashMap<String, Integer> movieIndexMap = new HashMap<>();
        HashMap<Integer, String> indexMovieMap = new HashMap<>();

        // Input movie names
        System.out.println("Enter movie names:");
        for (int i = 0; i < node; i++) {
            String movie = sc.nextLine();
            movieIndexMap.put(movie, i);
            indexMovieMap.put(i, movie);
        }

        // Initialize adjacency matrix
        for (int i = 0; i < node; i++) {
            for (int j = 0; j < node; j++) {
                arr[i][j] = 0;
            }
        }

        // Input edges
        System.out.println("Enter pairs of similar movies:");
        for (int i = 0; i < edges; i++) {
            String movie1 = sc.nextLine();
            String movie2 = sc.nextLine();

            if (!movieIndexMap.containsKey(movie1) || !movieIndexMap.containsKey(movie2)) {
                System.out.println("Error: One or both movies are not in the movie list. Please try again.");
                i--; // Decrement to retry this iteration
                continue;
            }

            int a = movieIndexMap.get(movie1);
            int b = movieIndexMap.get(movie2);
            arr[a][b] = arr[b][a] = 1;
        }

        // Print adjacency matrix for verification (optional)
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < node; i++) {
            for (int j = 0; j < node; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

        // Initialize visit array
        for (int i = 0; i < node; i++) {
            visit[i] = 0;
        }

        // Get user's liked movie
        System.out.println("Enter the movie you like:");
        String likedMovie = sc.nextLine();

        if (!movieIndexMap.containsKey(likedMovie)) {
            System.out.println("Error: The movie you like is not in the movie list. Exiting.");
            sc.close();
            return;
        }

        int source = movieIndexMap.get(likedMovie);

        // Perform BFS and get recommendations
        enqueue(source);
        visit[source] = 1;  // Mark the source as visited
        System.out.println("Recommended movies:");
        while (front < rear) {
            int item = dequeue();
            if (item != source) {
                System.out.print(indexMovieMap.get(item) + " ");  // Displaying movie name
            }

            for (int k = 0; k < node; k++) {
                if (arr[item][k] == 1 && notVisited(k) == 1) {
                    enqueue(k);
                }
            }
        }
        sc.close();
    }

    static int notVisited(int n) {
        return visit[n] == 1 ? 0 : 1;
    }

    static void enqueue(int n) {
        queue[rear++] = n;
        visit[n] = 1;
    }

    static int dequeue() {
        return queue[front++];
    }
}
