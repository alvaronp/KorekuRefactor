package es.unex.giiis.koreku.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class TodoGame implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_BUYDATE = "buydate";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

}
