CC = gcc
CFLAGS = -Wall -pedantic -ansi -std=c99 -pthread
OBJ = mssv.o
EXEC = mssv 

$(EXEC) : $(OBJ)	
		$(CC) $(OBJ) -o $(EXEC)

mssv.o : mssv.c	
		$(CC) -c mssv.c $(CFLAGS)

clean:
		rm -f $(EXEC) $(OBJ)