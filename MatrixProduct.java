public class MatrixProduct{
    
    public static int[][] matrixProduct_DAC(int[][] A, int[][] B){
        

        if((n == A[0].length) && (B.length == B[0].length) && (n == B.length) && A.length % 2 == 0){
            matrixDAC(A, 0, 0, B, 0, 0, A.length);
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
            C11 = matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB, n/2) + matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB, n/2);
            C12 = matrixDAC(A, startrowA, startcolA, B, startrowB, startcolB + n/2, n/2) + matrixDAC(A, startrowA, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2);
            C21 = matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB, n/2) + matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB, n/2);
            C22 = matrixDAC(A, startrowA + n/2, startcolA, B, startrowB, startcolB + n/2, n/2) + matrixDAC(A, startrowA + n/2, startcolA + n/2, B, startrowB + n/2, startcolB + n/2, n/2);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                C[i][j] = C11[i][j] + C12[i][j] + C21[i][j] + C22[i][j];
            }
        }

        return C;
    }
}