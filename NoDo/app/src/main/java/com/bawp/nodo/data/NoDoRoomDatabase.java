package com.bawp.nodo.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.bawp.nodo.model.NoDo;

@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {

    public static volatile NoDoRoomDatabase INSTANCE;
    public abstract NoDoDao noDoDao();

    public static NoDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
             synchronized (NoDoRoomDatabase.class) {
                  if (INSTANCE == null) {
                      //create our db
                      INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                              NoDoRoomDatabase.class, "nodo_database")
                              .addCallback(roomDatabaseCallBack)
                              .build();
                  }
             }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallBack =
                new RoomDatabase.Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        new PopulateDbAsync(INSTANCE).execute();
                    }
                };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final NoDoDao noDoDao;

        public PopulateDbAsync(NoDoRoomDatabase db) {
            noDoDao = db.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //noDoDao.deleteAll(); //removes all items from our table
            //for testing
//            NoDo noDo = new NoDo("Buy a new Ferrari");
//            noDoDao.insert(noDo);
//
//            noDo = new NoDo("Buy a Big house");
//            noDoDao.insert(noDo);

            return null;
        }
    }
}
