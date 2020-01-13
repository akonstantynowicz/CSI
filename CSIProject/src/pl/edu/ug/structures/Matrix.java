package pl.edu.ug.structures;

public class Matrix {

  public int M;

  public int N;

  public double[][] matrix;

  public double[] vector;

  public void print() {
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        System.out.format("%10f", matrix[i][j]);
      }
      System.out.format("| %10f", vector[i]);
      System.out.println();
    }
  }

  public Matrix(int M, int N) {
    this.M = M;
    this.N = N;
    matrix = new double[M][N];
    vector = new double[M];
  }

  public void fillWithZeros(){
    for(int i=0;i<M;i++){
      for(int j=0;j<N;j++){
        matrix[i][j]=0;
      }
    }
  }
}
