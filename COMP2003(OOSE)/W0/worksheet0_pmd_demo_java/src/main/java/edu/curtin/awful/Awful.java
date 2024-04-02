package edu.curtin.awful;

import java.io.*;
import java.util.*;

/**
 * This is some deliberately awful code to help demonstrate PMD's code quality warnings.
 *
 * You can trigger the quality warnings by running:
 * ./gradlew check
 *
 * OR
 * ./gradlew pmdMain
 */
public class Awful
{
    public static final int constant_not_following_naming_convention = 1;
    static int fieldWithDefaultAccess;

    public static void main(String[] args)
    {
        try
        {
            // Opening a file without (necessarily) closing it.
            PrintWriter writer = new PrintWriter("file.txt");

            writer.println("Hello world");

            // But we are closing the file! No -- this line won't run if there's an exception.
            // (Use either 'try-finally' or 'try-with-resources', introduced in Lecture 4.)
            writer.close();
        }
        catch(Exception e)  // Catching generic exception types
        {
            e.printStackTrace(); // Being unhelpful to the user
        }
    }
}
