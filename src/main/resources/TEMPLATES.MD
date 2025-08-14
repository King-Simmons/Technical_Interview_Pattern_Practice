    //BFS directional

    private static void bfs(int sr, int sc, int[][] grid, int target){
        int[][] dirs ={{0,1}, {1,0}, {-1,0}, {0,-1}};
        int m = grid.length;
        int n = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;
    
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];

            for(int[] d : dirs){
                int newR = r + d[0], newC = c + d[1];
                if(newR >= 0 && newC >= 0 && newR < m && newC < n && !visited[newR][newC] && grid[newR][newC] == target ){
                    visited[newR][newC] = true;
                    q.add(new int[]{newR, newC});
                }
            }
        }
    }

    //BFS multiSoure directional + levels
    private static void bfs(int[][] grid, int target, int start){
        int[][] dirs = {{0,1}, {1, 0}, {-1,0}, {0, -1}};
        int m = grid.length, n = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        int level = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                if(grid[r][c] == start){
                    q.add(new int[]{r,c});
                    visited[r][c] = true;
                }
            }
        }

        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                int[] curr = q.poll();
                int r = curr[0], c =curr[1];
                for(int[] d : dirs){
                    int newR = r + d[0], newC = c + d[1];
                    if(newR >= 0 && newC >= 0 && newR < m && newC < n && !visited[newR][newC] && grid[newR][newC] == target){
                        q.add(new int[]{newR, newC});
                        visited[newR][newC] = true;
                    }
                }
            }
            level++;
        }
    }

    //dfs
    private static void dfs(int r, int c, int m, int n, int[][] grid, boolean[][] visited, int target){
        if(r < 0 || c < 0 || r >= m || c >= n || visited[r][c] || grid[r][c] != target) return;
        visited[r][c] = true;

        int[][] dirs = {{1,0}, {0,1}, {-1, 0}, {0, -1}};
        for(int[] d : dirs){
            dfs(r + d[0], c + d[1], m, n, grid, visited, target);
        }
    }