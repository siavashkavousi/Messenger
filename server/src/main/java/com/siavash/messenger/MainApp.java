package com.siavash.messenger;

import com.siavash.messenger.database.Insertions;
import com.siavash.messenger.database.Queries;

/**
 * Created by sia on 6/25/16.
 */
public class MainApp {
    public static Queries queries = Queries.newInstance();
    public static Insertions insertions = Insertions.newInstance();

    public static void main(String[] args) {
    }
}
