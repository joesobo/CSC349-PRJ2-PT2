import java.io.FileReader;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MatrixProduct{
    
    public static int[][] matrixProduct_DAC(int[][] A, int[][] B){
        int n = A.length;

        if((n == A[0].length) && (B.length == B[0].length) && (n == B.length) && n % 2 == 0){
            return matrixDAC(A, 0, 0, B, 0, 0, A.length);
        }else{
            throw new IllegalArgumentException("Error: matrix k values did not match");
        }
    }

    public static int[][] matrixDAC(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n){
        int[][] C = new int[n][n];
        int[][] C11, C12, C21, C22;


        if(n==1){
            C[0][0] = A[startrowA][startcolA] * B[startrowB][startcolB];
        }else{
            C11 = add_matrix(matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB, n/2), startrowA, startcolA, matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB, n/2), startrowB, startcolB, n);
            C12 = add_matrix(matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB + n/2, n/2), startrowA, startcolA, matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2), startrowB, startcolB, n);
            C21 = add_matrix(matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB, n/2), startrowA, startcolA, matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB, n/2), startrowB, startcolB, n);
            C22 = add_matrix(matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB + n/2, n/2), startrowA, startcolA, matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2), startrowB, startcolB, n);
        
            for(int i = 0; i < n/2-1; i++){
                for(int j = 0; j < n/2-1; j++){
                    C[i][j] = C11[i][j];
                    C[i][j + n/2] = C12[i][j];
                    C[i + n/2][j] = C21[i][j];
                    C[i + n/2][j + n/2] = C22[i][j];
                }
            }
        }

        return C;
    }

    public static int[][] matrixProduct_Strassen(int[][] A, int[][] B){
        int n = A.length;

        if((n == A[0].length) && (B.length == B[0].length) && (n == B.length) && n % 2 == 0){
            return matrixStrassen(A, 0, 0, B, 0, 0, A.length);
        }else{
            throw new IllegalArgumentException("Error: matrix k values did not match");
        }
    }

    public static int[][] matrixStrassen(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n){
        int[][] C = new int[n][n];
        int[][] S1, S2, S3, S4, S5, S6, S7, S8, S9, S10;
        int[][] P1, P2, P3, P4, P5, P6, P7;
        int[][] C11, C12, C21, C22;

        if(n==1){
            C[0][0] = A[startrowA][startcolA] * B[startrowB][startcolB];
        }else{
            S1 = subtract_matrix(B, startrowB, startcolB + n/2, B, startrowB + n/2, startcolB + n/2, n/2);
            S2 = add_matrix(A, startrowA, startcolA, A, startrowA, startcolA + n/2, n/2);
            S3 = add_matrix(A, startrowA + n/2, startcolA, A, startrowA + n/2, startcolA + n/2, n/2);
            S4 = subtract_matrix(B, startrowB+n/2, startcolB, B, startrowB, startcolB, n/2);
            S5 = add_matrix(A, startrowA, startcolA, A, startrowA + n/2, startcolA + n/2, n/2);
            S6 = add_matrix(B, startrowB, startcolB, B, startrowB + n/2, startcolB + n/2, n/2);
            S7 = subtract_matrix(A, startrowA, startcolA + n/2, A, startrowA + n/2, startcolB + n/2, n/2);
            S8 = add_matrix(B, startrowB + n/2, startcolB, B, startrowB + n/2, startcolB + n/2, n/2);
            S9 = subtract_matrix(A, startrowA, startcolA, A, startrowA + n/2, startcolA, n/2);
            S10 = add_matrix(B, startrowB, startcolB, B, startrowB, startcolB + n/2, n/2);

            P1 = matrixStrassen(A, startrowA, startcolA, S1, startrowB, startcolB, n/2);
            P2 = matrixStrassen(S2, startrowA, startcolA, B, startrowB + n/2, startcolB + n/2, n/2);
            P3 = matrixStrassen(S3, startrowA, startcolA, B, startrowB, startcolB, n/2);
            P4 = matrixStrassen(A, startrowA+n/2, startcolA+n/2, S4, startrowB, startcolB, n/2);
            P5 = matrixStrassen(S5, startrowA, startcolA, S6, startrowB, startcolB, n/2);
            P6 = matrixStrassen(S7, startrowA, startcolA, S8, startrowB, startcolB, n/2);
            P7 = matrixStrassen(S9, startrowA, startcolA, S10, startrowB, startcolB, n/2);

            C11 = add_matrix(subtract_matrix(add_matrix(P5, startrowA, startcolA, P4, startrowB, startcolB, n/2), startrowA, startcolA, P2, startrowB, startcolB, n/2), startrowA, startcolA, P6, startrowB, startcolB, n/2);
            C12 = add_matrix(P1, startrowA, startcolA, P2, startrowB, startcolB, n/2);
            C21 = add_matrix(P3, startrowA, startcolA, P4, startrowB, startcolB, n/2);
            C22 = subtract_matrix(subtract_matrix(add_matrix(P5, startrowA, startcolA, P1, startrowB, startcolB, n/2), startrowA, startcolA, P3, startrowB, startcolB, n/2), startrowA, startcolA, P7, startrowB, startcolB, n/2);

            for(int i = 0; i < n-1; i++){
                for(int j = 0; j < n-1; j++){
                    C[i][j] = C11[i][j];
                    C[i][j + n/2] = C12[i][j];
                    C[i + n/2][j] = C21[i][j];
                    C[i + n/2][j + n/2] = C22[i][j];
                }
            }
        }

        return C;
    }
    
    private static int[][] add_matrix(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n) {
        int[][] C = new int[n][n];
        for (int i= 0; i<n-1; i++) {
            for (int j = 0; j<n-1; j++){
                C[i][j] = A[startrowA + i][startcolA + j] + B[startrowB + i][startcolB + j];
            }
        }
        return C;
        }

    private static int[][] subtract_matrix(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n) {
        int[][] C = new int[n][n];
        for (int i= 0; i<n-1; i++) {
            for (int j = 0; j<n-1; j++){
                C[i][j] = A[startrowA + i][startcolA + j] - B[startrowB + i][startcolB + j];
            }
        }
        return C;
    }

    public static void main(String args[]){
        List<Integer> list = new ArrayList<Integer>();
        //int[] list;
        BufferedReader textReader;
        int ax;
        int ay;
        int bx;
        int by;

        Scanner inputReader = new Scanner(System.in);
        System.out.println("Enter a matrix file name: ");

        //open file input
        String input = inputReader.nextLine();
        File file = new File(input);

        //loop through file collecting data into list
        try{
            textReader = new BufferedReader(new FileReader(file));
            String text;
            while((text = textReader.readLine()) != null){
                String[] items = text.split("\\s+");
                for (int i = 0; i < items.length; i++) {
                    if(!items[i].equals("")){
                        list.add(Integer.parseInt(items[i]));
                    }
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        //creation of matrices
        ax = list.get(0);
        ay = list.get(1);
        bx = list.get(2 + ax * ay);
        by = list.get(3 + ax * ay);

        int[][] A = new int[ax][ay];
        int[][] B = new int[bx][by];

        //fill A
        int count = 2;
        for(int i = 0; i < ax; i++){
            for(int j = 0; j < ay; j++){
                A[i][j] = list.get(count);
                count++;
            }
        }

        //fill B
        count = 4 + ax*ay;
        for(int i = 0; i < bx; i++){
            for(int j = 0; j < by; j++){
                B[i][j] = list.get(count);
                count++;
            }
        }

        //matrixProduct
        int[][] C;

        try{
            C = matrixProduct_Strassen(A, B);
        }
        //catch argument and close program
        catch(IllegalArgumentException ex){
            System.out.println("Matrixes were incorrect size");
            inputReader.close();
            return;
        }

        //printout
        System.out.println("\nProduct matrix:");
        for(int i = 0; i < C.length; i++){
            for(int j = 0; j < C[0].length; j++){
                System.out.print(C[i][j] + " ");
            }
            System.out.println("");
        }

        inputReader.close();
    }
}
