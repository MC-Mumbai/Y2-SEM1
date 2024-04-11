package edu.curtin.imageviewer;
import java.util.*;

/**
 * Represents an album of images.
 */
public class Album 
{
    private List<ImageRecord> images;

    public Album() {
        this.images = new ArrayList<>();
    }

    public void addImage(ImageRecord image) {
        this.images.add(image);
    }

    public ImageRecord getImage(int index) {
        return this.images.get(index);
    }

    public int getSize() {
        return this.images.size();
    }
}