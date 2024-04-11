package edu.curtin.addressbook;

import java.util.*;

/**
 * Contains all the address book entries.
 * 
 * @author Bhuvan
 */
public class AddressBook
{
    private Map<String, Entry> entries;

    public AddressBook() {
        entries = new HashMap<>();
    }

    public void addEntry(Entry entry) {
        entries.put(entry.getName(), entry);
    }

    public Entry getEntryByName(String name) {
        return entries.get(name);
    }

    public Entry getEntryByEmail(String email) {
        for (Entry entry : entries.values()) {
            if (entry.getEmails().contains(email)) {
                return entry;
            }
        }
        return null;
    }
}

