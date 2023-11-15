import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
//TODO: Список фигур и начальное положение всех фигур
    private Figure[][] fields = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);

    public char getColorGaming() {
        return colorGaming;
    }

    public void setColorGaming( char colorGaming ) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'),new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'),new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
        };
    }

    public String getCell( int row, int col ){
        Figure figure = this.fields[row][col];
        if (figure == null){
            return "    ";
        }
        return " "+figure.getColor()+figure.getName()+" ";
    } // используется для отрисовки

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    private void pawnCheck( int row, int col) {
        if( this.fields[row][col] != null && this.fields[row][col].getName().equals("P") ){
            switch (fields[row][col].getColor()) {
                case 'w': if (row == 7) { this.fields[row][col] = ((Pawn)this.fields[row][col]).becomeAnyone('w'); } break;
                case 'b': if (row == 0) { this.fields[row][col] = ((Pawn)this.fields[row][col]).becomeAnyone('b'); } break;
            }
        }
    }
    private boolean shahFlagWhite = false;
    private boolean shahFlagBlack = false;
    public class ShahCoords {
        int row;
        int col;
        Figure figure;
        ShahCoords(int row, int col, Figure figure){
            this.row = row;
            this.col = col;
            this.figure = figure;
        }
    }
    private ShahCoords SC;
    private boolean shahCheck( Figure[][] fields, char color, int level ) {
        int c = 0; // shah counter
//        #1 search the right King
        int i=0,j=0,flag=0;
        for ( ; i<8; i++){
            for (j=0; j<8; j++){
                if ( fields[i][j] != null && fields[i][j].getName().equals("K") && color == fields[i][j].getColor() ) {flag = 1; break;}
            }
            if (flag == 1) break;
        }

//        #2 shah check for current King
//        # Pawn
        switch (color){
            case 'w':
                if (i<=6){
                    if ( (j>=1 && j<=6) ){
                        if ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) {SC = new ShahCoords(i+1, j-1, fields[i+1][j-1]); c++; if (c==2) return true;}
                        if ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) {SC = new ShahCoords(i+1, j+1, fields[i+1][j+1]); c++; if (c==2) return true;}
                    }
                    if ( j==0 && ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) ) {SC = new ShahCoords(i+1, j+1, fields[i+1][j+1]); c++; if (c==2) return true;}
                    if ( j==7 && ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) ) {SC = new ShahCoords(i+1, j-1, fields[i+1][j-1]); c++; if (c==2) return true;}
                }
                break;
            case 'b':
                if (i>=1){
                    if ( (j>=1 && j<=6) ){
                        if ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) {SC = new ShahCoords(i-1, j-1, fields[i-1][j-1]); c++; if (c==2) return true;}
                        if ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) {SC = new ShahCoords(i-1, j+1, fields[i-1][j+1]); c++; if (c==2) return true;}
                    }
                    if ( j==0 && ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) ) {SC = new ShahCoords(i-1, j+1, fields[i-1][j+1]); c++; if (c==2) return true;}
                    if ( j==7 && ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) ) {SC = new ShahCoords(i-1, j-1, fields[i-1][j-1]); c++; if (c==2) return true;}
                } break;

        }
//        # 6 8 4 2
        int k=0;
        if (j>=1)
            for (k = j-1; k>=0; k--) // влево \ 4
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) {
                        SC = new ShahCoords(i, k, fields[i][k]); c++; if (c==2) return true;
                    }
        if (j<=6)
            for (k = j+1; k<=7; k++) // вправо \ 6
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) {
                        SC = new ShahCoords(i, k, fields[i][k]); c++; if (c==2) return true;
                    }
        if (i>=1)
            for (k = i-1; k>=0; k--) // вниз \ 2
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) {
                        SC = new ShahCoords(k, j, fields[k][j]); c++; if (c==2) return true;
                    }
        if (i<=6)
            for (k = i+1; k<=7; k++) // вверх \ 8
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) {
                        SC = new ShahCoords(k, j, fields[k][j]); c++; if (c==2) return true;
                    }
//        # 9 7 1 3
        int l=0;
        if (i>=1 && j>=1){
            k = i-1;
            l = j-1;
            while (k>=0 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) {
                        SC = new ShahCoords(k, l, fields[k][l]); c++; if (c==2) return true;
                    }
                k--;
                l--;
            }
        } // 1
        if (i<=6 && j<=6){
            k = i+1;
            l = j+1;
            while (k<=7 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) {
                        SC = new ShahCoords(k, l, fields[k][l]); c++; if (c==2) return true;
                    }
                k++;
                l++;
            }
        } // 9
        if (i<=6 && j>=1){
            k = i+1;
            l = j-1;
            while (k<=7 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) {
                        SC = new ShahCoords(k, l, fields[k][l]); c++; if (c==2) return true;
                    }
                k++;
                l--;
            }
        } // 7
        if (i>=1 && j<=6){
            k = i-1;
            l = j+1;
            while (k>=0 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) {
                        SC = new ShahCoords(k, l, fields[k][l]); c++; if (c==2) return true;
                    }
                k--;
                l++;
            }
        } // 3

//        # Knight
//        # 1
        if ( i+1 <= 7 && j+2 <= 7)
            if ( (fields[i+1][j+2] instanceof Knight) && fields[i+1][j+2].getColor() != color) {
                SC = new ShahCoords(i+1, j+2, fields[i+1][j+2]); c++; if (c==2) return true;
            }
        if ( i+2 <= 7 && j+1 <= 7)
            if ( (fields[i+2][j+1] instanceof Knight) && fields[i+2][j+1].getColor() != color) {
                SC = new ShahCoords(i+2, j+1, fields[i+2][j+1]); c++; if (c==2) return true;
            }

//        # 2
        if ( i+1 <= 7 && j-2 >= 0)
            if ( (fields[i+1][j-2] instanceof Knight) && fields[i+1][j-2].getColor() != color) {
                SC = new ShahCoords(i+1, j-2, fields[i+1][j-2]); c++; if (c==2) return true;
            }
        if ( i+2 <= 7 && j-1 >= 0)
            if ( (fields[i+2][j-1] instanceof Knight) && fields[i+2][j-1].getColor() != color) {
                SC = new ShahCoords(i+2, j-1, fields[i+2][j-1]); c++; if (c==2) return true;
            }

        // # 3
        if ( i-1 >= 0 && j-2 >= 0)
            if ( (fields[i-1][j-2] instanceof Knight) && fields[i-1][j-2].getColor() != color) {
                SC = new ShahCoords(i-1, j-2, fields[i-1][j-2]); c++; if (c==2) return true;
            }
        if ( i-2 >= 0 && j-1 >= 0)
            if ( (fields[i-2][j-1] instanceof Knight) && fields[i-2][j-1].getColor() != color) {
                SC = new ShahCoords(i-2, j-1, fields[i-2][j-1]); c++; if (c==2) return true;
            }
//        # 4
        if ( i-1 >= 0 && j+2 <= 7)
            if ( (fields[i-1][j+2] instanceof Knight) && fields[i-1][j+2].getColor() != color) {
                SC = new ShahCoords(i-1, j+2, fields[i-1][j+2]); c++; if (c==2) return true;
            }
        if ( i-2 >= 0 && j+1 <= 7)
            if ( (fields[i-2][j+1] instanceof Knight) && fields[i-2][j+1].getColor() != color) {
                SC = new ShahCoords(i-2, j+1, fields[i-2][j+1]); c++; if (c==2) return true;
            }

        if (c>0 && level == 1) return true;
//        if (c>0 && level == 2) return false;
//        this.SC = null;
        return false;
    } //false = ne Shah

    private boolean checkTileUnderAttack(int row, int col, Figure[][] fields, char color){
        int i=0,j=0,flag=0;
        for ( ; i<8; i++){
            for ( j=0; j<8; j++){
                if ( fields[i][j] != null && fields[i][j].getName().equals("K") && color == fields[i][j].getColor() ) {flag = 1; break;}
            }
            if (flag == 1) break;
        }

        if ( fields[row][col] == null || (fields[row][col] != null && fields[row][col].getColor() != color) ) {
            fields[row][col] = fields[i][j];
            fields[i][j] = null;
            if (!shahCheck(fields, color, 1)) {
                System.out.printf("%d %d tile NOT under Shah\n", row, col );
                return false; // читай как "клетка не под атакой"
            }
        }
        return true;
    } //row col - интересующая клетка
    private boolean defeat( Figure[][] fields, char color ) {
        Figure[][] xfields = fields;

//      search the right King
        int i=0,j=0,flag=0;
        for ( ; i<8; i++){
            for ( j=0; j<8; j++){
//                xfields[i][j] = fields[i][i].getColor()+fields[i][i].getName();
                if ( fields[i][j] != null && fields[i][j].getName().equals("K") && color == fields[i][j].getColor() ) {flag = 1; break;}
            }
            if (flag == 1) break;
        }

//        # 1 Атаковано ли поле с королём? Если нет, то не мат.
        if ( !shahCheck(this.fields, color, 1) ) return false;

//        # 2 Атакованы ли соседние с королём и свободные от его фигур поля? Если нет, то не мат.
        if ( i-1>=0 ) {
            if (j - 1 >= 0)
                if ( (fields[i - 1][j - 1] == null || (fields[i - 1][j - 1] != null && fields[i - 1][j - 1].getColor() != color)) && !checkTileUnderAttack(i - 1, j - 1, fields, color) ) return false;
            if ( (fields[i - 1][j] == null || (fields[i - 1][j] != null && fields[i - 1][j].getColor() != color)) && !checkTileUnderAttack(i - 1, j, fields, color) ) return false;
            if (j + 1 <= 7)
                if ( (fields[i - 1][j + 1] == null || (fields[i - 1][j + 1] != null && fields[i - 1][j + 1].getColor() != color)) && !checkTileUnderAttack(i - 1, j + 1, fields, color) ) return false;
        }

        if (j - 1 >= 0)
            if ( (fields[i][j - 1] == null || (fields[i][j - 1] != null && fields[i][j - 1].getColor() != color)) && !checkTileUnderAttack(i, j - 1, fields, color) ) return false;
        if ( (fields[i][j] == null || (fields[i][j] != null && fields[i][j].getColor() != color)) && !checkTileUnderAttack(i, j, fields, color) ) return false;
        if (j + 1 <= 7)
            if ( (fields[i][j + 1] == null || (fields[i][j + 1] != null && fields[i][j + 1].getColor() != color)) && !checkTileUnderAttack(i, j + 1, fields, color) ) return false;

        if ( i+1<=7 ) {
            if (j - 1 >= 0)
                if ( (fields[i + 1][j - 1] == null || (fields[i + 1][j - 1] != null && fields[i + 1][j - 1].getColor() != color)) && !checkTileUnderAttack(i + 1, j - 1, fields, color) ) return false;
            if ( (fields[i + 1][j] == null || (fields[i + 1][j] != null && fields[i + 1][j].getColor() != color)) && !checkTileUnderAttack(i + 1, j, fields, color) ) return false;
            if (j + 1 <= 7)
                if ( (fields[i + 1][j + 1] == null || (fields[i + 1][j + 1] != null && fields[i + 1][j + 1].getColor() != color)) && !checkTileUnderAttack(i + 1, j + 1, fields, color) ) return false;
        }

//        # 3 Сколько фигур атакуют короля? Если две, то мат.
        if ( shahCheck(this.fields, color, 2) ) return true;

//        # 4 Можно ли съесть атакующую фигуру? Если да, то не мат.
//        поставить на тайл вражеского короля, проверить - под шахом ли он в этой позиции: если да, то эту фигуру можно съесть
        int i_reverse=0,j_reverse=0,flag_reverse=0; //коорды короля противоположного цвета
        for ( ; i_reverse<8; i_reverse++){
            for ( j_reverse=0; j_reverse<8; j_reverse++){
                if ( fields[i_reverse][j_reverse] != null && fields[i_reverse][j_reverse].getName().equals("K") && fields[i][j].getColor() != color ) {flag_reverse = 1; break;}
            }
            if (flag_reverse == 1) break;
        }

        Figure tmp = fields[SC.row][SC.col];
        fields[SC.row][SC.col] = fields[i_reverse][j_reverse];
        fields[i_reverse][j_reverse] = null;
        if ( shahCheck(fields, fields[SC.row][SC.col].getColor(), 1 ) ) {
            fields[i_reverse][j_reverse] = fields[SC.row][SC.col];
            fields[SC.row][SC.col] = tmp;
            return false;
        } //можем съесть = не мат

//        # 5 Атакует конь? Если да, то мат.
        if ( SC.figure.getName().equals("N") && SC.figure.getColor() != color ) return true;

//        # 6 Атакующая фигура на соседнем поле? Если да, то мат.
        if ( Math.abs(SC.row - i)<=1 && Math.abs(SC.col - j)<=1 && SC.figure.getColor() != color ) return true;

//        # 7 Можно ли перекрыть линию атаки? Если да, то не мат. Иначе - мат.
//       1) проверять направления 6 8 4 2 && 1 9 7 3 в которых стоит атакующая фигура.
//       если в промежутке между королем и Атакующей фигурой, подставляя пешку, удастся избавиться от шаха, то
//        начать перебирать какие фигуры могу добраться до одной из клеток
        int k, l;
        if ( i < SC.row && j < SC.col ){
            k = i+1;
            l = j+1;
            while (k < SC.row && l < SC.col){
                if ( blockShahCheck(k, l, fields, color) ) return false;
                k++;
                l++;
            }
        } // 9

        if ( i < SC.row && j == SC.col ) // 8
            for (k = i + 1; k < SC.row; k++)
                if ( blockShahCheck(k, j, fields, color) ) return false;

        if ( i < SC.row && j > SC.col ){
            k = i+1;
            l = j-1;
            while (k < SC.row && l > SC.col){
                if ( blockShahCheck(k, l, fields, color) ) return false;
                k++;
                l--;
            }
        } // 7

        if ( i == SC.row && j > SC.col ) // 4
            for (l = j - 1; l > SC.col; l--)
                if ( blockShahCheck(i, l, fields, color) ) return false;

        if ( i > SC.row && j > SC.col ){
            k = i-1;
            l = j-1;
            while (k > SC.row && l > SC.col){
                if ( blockShahCheck(k, l, fields, color) ) return false;
                k--;
                l--;
            }
        } // 1

        if ( i > SC.row && j == SC.col ) // 2
            for (k = i - 1; k > SC.row; k--)
                if ( blockShahCheck(k, j, fields, color) ) return false;

        if ( i > SC.row && j < SC.col ){
            k = i-1;
            l = j+1;
            while (k > SC.row && l < SC.col){
                if ( blockShahCheck(k, l, fields, color) ) return false;
                k--;
                l++;
            }
        } // 3

        if ( i == SC.row && j < SC.col ) // 6
            for (l = j + 1; l < SC.col; l++)
                if ( blockShahCheck(i, l, fields, color) ) return false;

        return true;
    } //true = mat

    private boolean blockShahCheck(int row, int col, Figure[][] fields, char color){
        Figure figure;
        for (int i = 0; i<8; i++)
            for (int j = 0; j<8; j++) {
                figure = fields[i][j];
                if ( figure != null && figure.getColor() == color && figure.canMove(i, j, row, col, fields) ) return true;
            }
        return false;
    }
    private boolean castling( int row1, int col1, int row2, int col2 ){

        Figure[][] fields = new Figure[8][];
        for(int i=0;i<8;i++) {
            fields[i] = new Figure[8];
            for(int j=0; j<8; j++)
                fields[i][j] = this.fields[i][j];
        }

        fields[row1][col1] = fields[row2][col2];
        fields[row2][col2] = null;
        Figure figure = fields[row1][col1];
        if( figure.getName().equals("K") && ((King)fields[row2][col2]).isFirstStep && Math.abs(col1-col2) == 2 )
            if ( col1 < col2 ) {
                if ( !fields[row1][col1+3].getName().equals("R") || !((Rook)fields[row1][col1 + 3]).isFirstStep || fields[row1][col1+1] != null ) {
                    System.out.println("path is not empty");
                    fields[row1][col1] = figure;
                    fields[row2][col2] = null;
                    return false;
                }
                fields[row1][col1+1] = figure;
                fields[row1][col1+2] = null;
                switch(colorGaming){
                    case 'w':
                        if ( shahFlagWhite || shahCheck(fields, colorGaming, 1) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1+1] = null;
                            return false;
                        } break;
                    case 'b':
                        if ( shahFlagBlack || shahCheck(fields, colorGaming, 1) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1+1] = null;
                            return false;
                        } break;
                }
                this.fields[row1][col1+2] = figure;
                this.fields[row1][col1+1] = this.fields[row1][col1+3];
                this.fields[row1][col1+3] = null;
                ((King)this.fields[row1][col1+2]).isFirstStep = false;
            } else {
                if ( !fields[row1][col1-4].getName().equals("R") || !((Rook)fields[row1][col1-4]).isFirstStep ||
                        fields[row1][col1-1] != null || fields[row1][col1-3] != null ) {
                    System.out.println("path is not empty");
                    fields[row1][col1] = figure;
                    fields[row2][col2] = null;
                    return false;
                }
                fields[row1][col1-1] = figure;
                fields[row1][col1-2] = null;
                switch(colorGaming){
                    case 'w':
                        if ( shahFlagWhite || shahCheck(fields, colorGaming, 1) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1-1] = null;
                            return false;
                        } break;
                    case 'b':
                        if ( shahFlagBlack || shahCheck(fields, colorGaming, 1) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1-1] = null;
                            return false;
                        } break;
                }
                this.fields[row1][col1-2] = figure;
                this.fields[row1][col1-1] = this.fields[row1][col1-4];
                this.fields[row1][col1-4] = null;
                ((King)this.fields[row1][col1-2]).isFirstStep = false;
            }
        return true;
    }

    public boolean move_figure( int row1, int col1, int row2, int col2 ){

        Figure figure =  this.fields[row1][col1];

        if (figure == null) return false;
        if (figure.getColor() != colorGaming) return false;

        shahFlagWhite = shahCheck(fields, 'w', 1);
        shahFlagBlack = shahCheck(fields, 'b', 1);
        switch(colorGaming){
            case 'w': if (!shahFlagWhite) { SC = null; }
            case 'b': if (!shahFlagBlack) { SC = null; }
        }
        if ( defeat(fields, colorGaming) ) { System.out.println("gg!"); System.exit(0); }

        if (figure.canMove(row1, col1, row2, col2, fields) && this.fields[row2][col2]==null){

            System.out.println("move");
            fields[row2][col2] = figure;
            fields[row1][col1] = null;

            if ( shahCheck(fields, colorGaming, 1) ) {
                System.out.println("still under Shah");
                fields[row1][col1] = figure;
                fields[row2][col2] = null;
                return false;
            }

            if ( !castling(row1, col1, row2, col2) ) return false;
            pawnCheck(row2, col2);

            return true;
        } else if (figure.canAttack(row1, col1, row2, col2, fields) && this.fields[row2][col2] != null &&
                this.fields[row2][col2].getColor() != this.fields[row1][col1].getColor() &&
                !this.fields[row2][col2].getName().equals("K") ){

            System.out.println("attack");
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;

            if ( shahCheck(fields, colorGaming, 1) ) {
                System.out.println("still under Shah");
                fields[row1][col1] = figure;
                fields[row2][col2] = null;
                return false;
            }

            switch (this.fields[row2][col2].getColor()){
                case 'w': this.takeWhite.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
                case 'b': this.takeBlack.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
            }

            pawnCheck(row2, col2);

            return true;
        }
        return false;

    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for(int row = 7; row >= 0; row--){
            System.out.print(row);
            for(int col = 0; col <= 7; col++){
                System.out.print("|"+getCell(row, col));
             }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col = 0; col <= 7; col++){
            System.out.print("    "+col);
        }


    }


}
