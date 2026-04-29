package com.kime.seminar.database;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&\u00a8\u0006\u000b"}, d2 = {"Lcom/kime/seminar/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "<init>", "()V", "userDao", "Lcom/kime/seminar/database/UserDao;", "seminarDao", "Lcom/kime/seminar/database/SeminarDao;", "registrationDao", "Lcom/kime/seminar/database/RegistrationDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.kime.seminar.database.User.class, com.kime.seminar.database.Seminar.class, com.kime.seminar.database.Registration.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.kime.seminar.database.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.kime.seminar.database.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kime.seminar.database.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kime.seminar.database.SeminarDao seminarDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kime.seminar.database.RegistrationDao registrationDao();
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/kime/seminar/database/AppDatabase$Companion;", "", "<init>", "()V", "INSTANCE", "Lcom/kime/seminar/database/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kime.seminar.database.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}