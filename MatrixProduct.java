import java.io.FileReader;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MatrixProduct{
    
    public static int[][] matrixProduct_DAC(int[][] A, int[][] B){
        int n = A.length;

        if((n == A[0].length) && (B.length == B[0].length) && (n == B.length) && isPowerOfTwo(n)){
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
            C11 = add_matrix(matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB, n/2), 0, 0, matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB, n/2), 0, 0, n/2);
            C12 = add_matrix(matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB + n/2, n/2), 0, 0, matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2), 0, 0, n/2);
            C21 = add_matrix(matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB, n/2), 0, 0, matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB, n/2), 0, 0, n/2);
            C22 = add_matrix(matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB + n/2, n/2), 0, 0, matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2), 0, 0, n/2);
        
            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
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

        if((n == A[0].length) && (B.length == B[0].length) && (n == B.length) && isPowerOfTwo(n)){
            return matrixStrassen(A, 0, 0, B, 0, 0, n);
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
            S7 = subtract_matrix(A, startrowA, startcolA + n/2, A, startrowA + n/2, startcolA + n/2, n/2);
            S8 = add_matrix(B, startrowB + n/2, startcolB, B, startrowB + n/2, startcolB + n/2, n/2);
            S9 = subtract_matrix(A, startrowA, startcolA, A, startrowA + n/2, startcolA, n/2);
            S10 = add_matrix(B, startrowB, startcolB, B, startrowB, startcolB + n/2, n/2);

            P1 = matrixStrassen(A, startrowA, startcolA, S1, 0, 0, n/2);
            P2 = matrixStrassen(S2, 0, 0, B, startrowB + n/2, startcolB + n/2, n/2);
            P3 = matrixStrassen(S3, 0, 0, B, startrowB, startcolB, n/2);
            P4 = matrixStrassen(A, startrowA+n/2, startcolA+n/2, S4, 0, 0, n/2);
            P5 = matrixStrassen(S5, 0, 0, S6, 0, 0, n/2);
            P6 = matrixStrassen(S7, 0, 0, S8, 0, 0, n/2);
            P7 = matrixStrassen(S9, 0, 0, S10, 0, 0, n/2);

            C11 = add_matrix(subtract_matrix(add_matrix( P5, 0, 0, P4, 0, 0, n/2), 0, 0, P2, 0, 0, n/2), 0, 0, P6, 0, 0, n/2);
            C12 = add_matrix(P1, 0, 0, P2, 0, 0, n/2);
            C21 = add_matrix(P3, 0, 0, P4, 0, 0, n/2);
            C22 = subtract_matrix(subtract_matrix(add_matrix(P5, 0, 0, P1, 0, 0, n/2), 0, 0, P3, 0, 0, n/2), 0, 0, P7, 0, 0, n/2);

            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
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
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++){
                C[i][j] = A[startrowA + i][startcolA + j] + B[startrowB + i][startcolB + j];
            }
        }
        return C;
    }

    private static int[][] subtract_matrix(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n) {
        int[][] C = new int[n][n];
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++){
                C[i][j] = A[startrowA + i][startcolA + j] - B[startrowB + i][startcolB + j];
            }
        }
        return C;
    }

    static boolean isPowerOfTwo(int n) { 
        if(n==0){ 
            return false; 
        }
        return (int)(Math.ceil((Math.log(n) / Math.log(2)))) == (int)(Math.floor(((Math.log(n) / Math.log(2))))); 
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
            C = matrixProduct_DAC(A, B);
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
