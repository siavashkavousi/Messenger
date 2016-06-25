package com.siavash.messenger;

/**
 * Created by sia on 6/25/16.
 */
public class Queries {

    private Queries() {
    }

    public static Queries newInstance(){
        return new Queries();
    }

    public User findUser(String userName) {
//        Insertions insertions = new Insertions();
//        insertions.insertContacts(new ObjectId("1"), new ObjectId("2"), null, null);

        return new User("d", "d", "d", "d");
    }
}
