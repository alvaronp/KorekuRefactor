package es.unex.giiis.koreku.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class Games implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String _ID="game_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_BUYDATE = "buydate";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
    public static class Consolas implements BaseColumns {
        public static final String TABLE_NAME = "consolas";
        public static final String _ID="console_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COMPANY = "company";
        public static final String COLUMN_NAME_IMAGE = "image";

    }
    public static class Perfil implements BaseColumns {
        public static final String TABLE_NAME = "perfil";
        public static final String _ID="profile_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COMPANY = "company";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_IMAGE = "image";


    }
}
