#include <stdio.h>
#include <stdlib.h>

// Function to allocate memory for a matrix
int **allocateMatrix(int rows, int cols)
{
    int **matrix = (int **)malloc(rows * sizeof(int *));
    for (int i = 0; i < rows; i++)
    {
        matrix[i] = (int *)malloc(cols * sizeof(int));
    }
    return matrix;
}

// Function to deallocate memory for a matrix
void deallocateMatrix(int **matrix, int rows)
{
    for (int i = 0; i < rows; i++)
    {
        free(matrix[i]);
    }
    free(matrix);
}

// Function to multiply two matrices using divide and conquer
int **matrixMultiply(int **A, int **B, int size)
{
    int **result = allocateMatrix(size, size);

    if (size == 1)
    {
        result[0][0] = A[0][0] * B[0][0];
    }
    else
    {
        int **A11 = allocateMatrix(size / 2, size / 2);
        int **A12 = allocateMatrix(size / 2, size / 2);
        int **A21 = allocateMatrix(size / 2, size / 2);
        int **A22 = allocateMatrix(size / 2, size / 2);
        int **B11 = allocateMatrix(size / 2, size / 2);
        int **B12 = allocateMatrix(size / 2, size / 2);
        int **B21 = allocateMatrix(size / 2, size / 2);
        int **B22 = allocateMatrix(size / 2, size / 2);

        // Split matrices A and B into quarters
        for (int i = 0; i < size / 2; i++)
        {
            for (int j = 0; j < size / 2; j++)
            {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + size / 2];
                A21[i][j] = A[i + size / 2][j];
                A22[i][j] = A[i + size / 2][j + size / 2];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + size / 2];
                B21[i][j] = B[i + size / 2][j];
                B22[i][j] = B[i + size / 2][j + size / 2];
            }
        }

        // Recursively compute the product of matrices
        int **C11 = matrixMultiply(A11, B11, size / 2);
        int **C12 = matrixMultiply(A12, B21, size / 2);
        int **C21 = matrixMultiply(A21, B12, size / 2);
        int **C22 = matrixMultiply(A22, B22, size / 2);

        // Combine submatrices to get the result
        for (int i = 0; i < size / 2; i++)
        {
            for (int j = 0; j < size / 2; j++)
            {
                result[i][j] = C11[i][j] + C12[i][j];
                result[i][j + size / 2] = C11[i][j + size / 2] + C12[i][j + size / 2];
                result[i + size / 2][j] = C21[i][j] + C22[i][j];
                result[i + size / 2][j + size / 2] = C21[i][j + size / 2] + C22[i][j + size / 2];
            }
        }

        // Free allocated memory
        deallocateMatrix(A11, size / 2);
        deallocateMatrix(A12, size / 2);
        deallocateMatrix(A21, size / 2);
        deallocateMatrix(A22, size / 2);
        deallocateMatrix(B11, size / 2);
        deallocateMatrix(B12, size / 2);
        deallocateMatrix(B21, size / 2);
        deallocateMatrix(B22, size / 2);
        deallocateMatrix(C11, size / 2);
        deallocateMatrix(C12, size / 2);
        deallocateMatrix(C21, size / 2);
        deallocateMatrix(C22, size / 2);
    }

    return result;
}

// Function to print a matrix
void printMatrix(int **matrix, int rows, int cols)
{
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            printf("%d\t", matrix[i][j]);
        }
        printf("\n");
    }
}

int main()
{
    int size;
    printf("Enter the size of the matrices: ");
    scanf("%d", &size);

    int **A = allocateMatrix(size, size);
    int **B = allocateMatrix(size, size);

    // Input elements for matrix A
    printf("Enter elements for matrix A:\n");
    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            scanf("%d", &A[i][j]);
        }
    }

    // Input elements for matrix B
    printf("Enter elements for matrix B:\n");
    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            scanf("%d", &B[i][j]);
        }
    }

    printf("Matrix A:\n");
    printMatrix(A, size, size);

    printf("\nMatrix B:\n");
    printMatrix(B, size, size);

    int **result = matrixMultiply(A, B, size);

    printf("\nResultant Matrix:\n");
    printMatrix(result, size, size);

    // Deallocate memory
    deallocateMatrix(A, size);
    deallocateMatrix(B, size);
    deallocateMatrix(result, size);

    return 0;
}