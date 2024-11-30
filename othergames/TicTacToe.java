public class TicTacToe {
    int user;
    int[][] grid;
    boolean win;

    public TicTacToe() {
        this.user = -1;
        this.grid = new int[5][5];
        this.win = false;
    }
    
    public boolean turn(int x, int y) {
        if (this.grid[x][y] == 0) {
            this.grid[x][y] = this.user;
            this.win = check_win(x, y, 1);
            this.user *= -1;
            return true;
        }

        return false;
    }

    public boolean check_win(int x, int y, int inARow) {
        inARow = 0;
        if (x == y) {
            for (int i = 0; i < 5; i++) {
                if (this.grid[i][i] == this.user) {
                    inARow += 1;
                } else {
                    break;
                }
            }
        }

        if (inARow == 5) {
            return true;
        }
        
        inARow = 0;
        if (x == 4 - y) {
            for (int i = 0; i < 5; i++) {
                if (this.grid[4 - i][i] == this.user) {
                    inARow += 1;
                } else {
                    break;
                }
            }
        }

        if (inARow == 5) {
            return true;
        }

        inARow = 0;
        for (int i = 0; i < 5; i++) {
            if (this.grid[i][y] == this.user) {
                inARow += 1;
            } else {
                break;
            }
        }

        if (inARow == 5) {
            return true;
        }

        inARow = 0;
        for (int i = 0; i < 5; i++) {
            if (this.grid[x][i] == this.user) {
                inARow += 1;
            } else {
                break;
            }
        }

        if (inARow == 5) {
            return true;
        }

        return false;
    }
}
