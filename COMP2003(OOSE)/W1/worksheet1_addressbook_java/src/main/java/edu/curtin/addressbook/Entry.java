package edu.curtin.addressbook;

import java.util.*;

/**
 * Represents a single address book entry.
 * 
 * @author Bhuvan Lau
 */
public class Entry 
{
    private String name;
    private Set<String> emails;

    public Entry(String name) {
        this.name = name;
        this.emails = new HashSet<>();
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public String getName() {
        return name;
    }

    public Set<String> getEmails() {
        return emails;
    }
}
