package com.kime.seminar.database;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile SeminarDao _seminarDao;

  private volatile RegistrationDao _registrationDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(1, "7767455a7bae9175470125be808a3559", "a473e11e7825cb664299994bd1f5e292") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `gender` TEXT NOT NULL, `hobbies` TEXT NOT NULL, `city` TEXT NOT NULL, `profilePictureUrl` TEXT, `password` TEXT NOT NULL, `relationshipStatus` TEXT NOT NULL, `occupation` TEXT NOT NULL, `age` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `seminars` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `date` TEXT NOT NULL, `time` TEXT NOT NULL, `location` TEXT NOT NULL, `quota` INTEGER NOT NULL, `summary` TEXT NOT NULL, `speaker` TEXT NOT NULL, `materials` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `registrations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `seminarId` INTEGER NOT NULL, `registrationDate` TEXT NOT NULL, `status` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7767455a7bae9175470125be808a3559')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `users`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `seminars`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `registrations`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(11);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("hobbies", new TableInfo.Column("hobbies", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("city", new TableInfo.Column("city", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profilePictureUrl", new TableInfo.Column("profilePictureUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("relationshipStatus", new TableInfo.Column("relationshipStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("occupation", new TableInfo.Column("occupation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(connection, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenDelegate.ValidationResult(false, "users(com.kime.seminar.database.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final Map<String, TableInfo.Column> _columnsSeminars = new HashMap<String, TableInfo.Column>(9);
        _columnsSeminars.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("time", new TableInfo.Column("time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("quota", new TableInfo.Column("quota", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("summary", new TableInfo.Column("summary", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("speaker", new TableInfo.Column("speaker", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeminars.put("materials", new TableInfo.Column("materials", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysSeminars = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesSeminars = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSeminars = new TableInfo("seminars", _columnsSeminars, _foreignKeysSeminars, _indicesSeminars);
        final TableInfo _existingSeminars = TableInfo.read(connection, "seminars");
        if (!_infoSeminars.equals(_existingSeminars)) {
          return new RoomOpenDelegate.ValidationResult(false, "seminars(com.kime.seminar.database.Seminar).\n"
                  + " Expected:\n" + _infoSeminars + "\n"
                  + " Found:\n" + _existingSeminars);
        }
        final Map<String, TableInfo.Column> _columnsRegistrations = new HashMap<String, TableInfo.Column>(5);
        _columnsRegistrations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistrations.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistrations.put("seminarId", new TableInfo.Column("seminarId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistrations.put("registrationDate", new TableInfo.Column("registrationDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistrations.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysRegistrations = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesRegistrations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRegistrations = new TableInfo("registrations", _columnsRegistrations, _foreignKeysRegistrations, _indicesRegistrations);
        final TableInfo _existingRegistrations = TableInfo.read(connection, "registrations");
        if (!_infoRegistrations.equals(_existingRegistrations)) {
          return new RoomOpenDelegate.ValidationResult(false, "registrations(com.kime.seminar.database.Registration).\n"
                  + " Expected:\n" + _infoRegistrations + "\n"
                  + " Found:\n" + _existingRegistrations);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users", "seminars", "registrations");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "users", "seminars", "registrations");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SeminarDao.class, SeminarDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RegistrationDao.class, RegistrationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public SeminarDao seminarDao() {
    if (_seminarDao != null) {
      return _seminarDao;
    } else {
      synchronized(this) {
        if(_seminarDao == null) {
          _seminarDao = new SeminarDao_Impl(this);
        }
        return _seminarDao;
      }
    }
  }

  @Override
  public RegistrationDao registrationDao() {
    if (_registrationDao != null) {
      return _registrationDao;
    } else {
      synchronized(this) {
        if(_registrationDao == null) {
          _registrationDao = new RegistrationDao_Impl(this);
        }
        return _registrationDao;
      }
    }
  }
}
