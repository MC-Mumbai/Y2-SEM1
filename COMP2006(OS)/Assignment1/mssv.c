#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <stdint.h>  // For uintptr_t


#define HEIGHT 9
#define WIDTH 9

// Global Sudoku grid and validation arrays
int Sol[HEIGHT][WIDTH];
int Row[HEIGHT] = {0};
int Col[WIDTH] = {0};
int Sub[9] = {0}; // Each 3x3 subgrid
int Counter = 0;
pthread_mutex_t mutex;


typedef struct {
    int start_index;
    int end_index;
} ValidationArgs;

typedef struct {
    int valid;                // Overall validity flag
    char details[128];        // Details about what specifically failed
} ValidationResult;


void* validate_rows_and_subgrids(void* arg) {
    ValidationArgs *args = (ValidationArgs*) arg;
    int valid = 1;  // Assume all are valid unless proven otherwise
    int valid_count = 0;

    // Validate Rows
    for (int i = args->start_index; i <= args->end_index; i++) {
        int seen[10] = {0};
        for (int j = 0; j < WIDTH; j++) {
            int num = Sol[i][j];
            if (num < 1 || num > 9 || seen[num]) {
                valid = 0;
                break;
            }
            seen[num] = 1;
        }
        pthread_mutex_lock(&mutex);
        Row[i] = seen[1] && seen[2] && seen[3] && seen[4] && seen[5] &&
                 seen[6] && seen[7] && seen[8] && seen[9];
        valid_count += Row[i];
        pthread_mutex_unlock(&mutex);
    }

    // Validate Sub-grids
    for (int idx = args->start_index; idx <= args->end_index; idx++) {
        int seen[10] = {0};
        int rowStart = (idx / 3) * 3;
        int colStart = (idx % 3) * 3;
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                int num = Sol[i][j];
                if (num < 1 || num > 9 || seen[num]) {
                    valid = 0;
                    break;
                }
                seen[num] = 1;
            }
        }
        pthread_mutex_lock(&mutex);
        Sub[idx] = seen[1] && seen[2] && seen[3] && seen[4] && seen[5] &&
                   seen[6] && seen[7] && seen[8] && seen[9];
        valid_count += Sub[idx];
        pthread_mutex_unlock(&mutex);
    }

    pthread_mutex_lock(&mutex);
    Counter += valid_count;
    pthread_mutex_unlock(&mutex);

    sleep(1); // Sleep to observe synchronization issues
    return (void*)(uintptr_t)valid;
}


void* validate_columns(void* arg) {
    int valid = 1;
    int valid_count = 0;

    for (int col = 0; col < WIDTH; col++) {
        int seen[10] = {0};
        for (int row = 0; row < HEIGHT; row++) {
            int num = Sol[row][col];
            if (num < 1 || num > 9 || seen[num]) {
                valid = 0;
                break;
            }
            seen[num] = 1;
        }
        pthread_mutex_lock(&mutex);
        Col[col] = seen[1] && seen[2] && seen[3] && seen[4] && seen[5] &&
                   seen[6] && seen[7] && seen[8] && seen[9];
        valid_count += Col[col];
        pthread_mutex_unlock(&mutex);
    }

    pthread_mutex_lock(&mutex);
    Counter += valid_count;
    pthread_mutex_unlock(&mutex);

    sleep(1); // Sleep to observe synchronization issues
    printf("Thread ID-4 is the last thread.\n");
    return (void*)(uintptr_t)valid;
}



void loadSudokuMatrix(const char* filename) {
    FILE *fp;
    int readCount;

    fp = fopen(filename, "r");
    if (fp == NULL) {
        fprintf(stderr, "UNABLE TO OPEN THE FILE \n");
        exit(EXIT_FAILURE);
    }

    for (int col = 0; col < HEIGHT; col++) {
        for (int row = 0; row < WIDTH; row++) {
            readCount = fscanf(fp, "%d", &Sol[col][row]);
            if (readCount != 1) {
                fprintf(stderr, "Error reading data at position [%d,%d]\n", col, row);
                fclose(fp);
                exit(EXIT_FAILURE);
            }
        }
    }
    fclose(fp);

    // Display the loaded Sudoku matrix
    printf("CURRENTLY SOLVING --\n");
    for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
            printf("%d ", Sol[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void print_validation_results() {
    printf("\nValidation Results:\n");
    int total_valid = 0;

    // Check rows
    printf("Invalid rows:");
    for (int i = 0; i < HEIGHT; i++) {
        if (Row[i] == 0) {
            printf(" %d", i + 1);
        } else {
            total_valid++;
        }
    }
    printf("\n");

    // Check columns
    printf("Invalid columns:");
    for (int i = 0; i < WIDTH; i++) {
        if (Col[i] == 0) {
            printf(" %d", i + 1);
        } else {
            total_valid++;
        }
    }
    printf("\n");

    // Check sub-grids
    printf("Invalid sub-grids:");
    for (int i = 0; i < 9; i++) {
        if (Sub[i] == 0) {
            printf(" %d", i + 1);
        } else {
            total_valid++;
        }
    }
    printf("\n");

    // Print summary
    printf("Total valid rows, columns, and sub-grids: %d\n", total_valid);
    if (total_valid == 27) {
        printf("The solution is valid.\n");
    } else {
        printf("The solution is invalid.\n");
    }
}




int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <filename>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    loadSudokuMatrix(argv[1]);
    pthread_mutex_init(&mutex, NULL);

    pthread_t threads[4];
    int thread_results[4];
    ValidationArgs args[4] = {{0, 2}, {3, 5}, {6, 8}, {0, 8}}; // Last args for columns

    // Create threads
    for (int i = 0; i < 4; i++) {
        pthread_create(&threads[i], NULL, (i < 3 ? validate_rows_and_subgrids : validate_columns), &args[i]);
    }

    // Join threads and collect results
    for (int i = 0; i < 4; i++) {
        void *result;
        pthread_join(threads[i], &result);
        thread_results[i] = (int)(uintptr_t)result;
        printf("Thread ID-%d: %s\n", i + 1, thread_results[i] ? "valid" : "invalid");
    }

    pthread_mutex_destroy(&mutex);

    // Final summary
    printf("There are %d valid sub-grids, and thus the solution is %s\n", Counter, (Counter == 27 ? "valid" : "invalid"));

    return 0;
}



