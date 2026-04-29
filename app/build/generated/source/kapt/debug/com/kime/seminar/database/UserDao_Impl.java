package com.kime.seminar.database;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<User> __insertAdapterOfUser;

  private final EntityDeleteOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUser = new EntityInsertAdapter<User>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`name`,`email`,`gender`,`hobbies`,`city`,`profilePictureUrl`,`password`,`relationshipStatus`,`occupation`,`age`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getEmail());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getGender());
        }
        if (entity.getHobbies() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getHobbies());
        }
        if (entity.getCity() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCity());
        }
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getProfilePictureUrl());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getPassword());
        }
        if (entity.getRelationshipStatus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getRelationshipStatus());
        }
        if (entity.getOccupation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getOccupation());
        }
        statement.bindLong(11, entity.getAge());
      }
    };
    this.__updateAdapterOfUser = new EntityDeleteOrUpdateAdapter<User>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`name` = ?,`email` = ?,`gender` = ?,`hobbies` = ?,`city` = ?,`profilePictureUrl` = ?,`password` = ?,`relationshipStatus` = ?,`occupation` = ?,`age` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getEmail());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getGender());
        }
        if (entity.getHobbies() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getHobbies());
        }
        if (entity.getCity() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCity());
        }
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getProfilePictureUrl());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getPassword());
        }
        if (entity.getRelationshipStatus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getRelationshipStatus());
        }
        if (entity.getOccupation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getOccupation());
        }
        statement.bindLong(11, entity.getAge());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Long> arg1) {
    if (user == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfUser.insertAndReturnId(_connection, user);
    }, arg1);
  }

  @Override
  public Object updateUser(final User user, final Continuation<? super Unit> arg1) {
    if (user == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfUser.handle(_connection, user);
      return Unit.INSTANCE;
    }, arg1);
  }

  @Override
  public Object getUserByEmail(final String email, final Continuation<? super User> arg1) {
    final String _sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (email == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, email);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfGender = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gender");
        final int _columnIndexOfHobbies = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hobbies");
        final int _columnIndexOfCity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "city");
        final int _columnIndexOfProfilePictureUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profilePictureUrl");
        final int _columnIndexOfPassword = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "password");
        final int _columnIndexOfRelationshipStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "relationshipStatus");
        final int _columnIndexOfOccupation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "occupation");
        final int _columnIndexOfAge = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "age");
        final User _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          final String _tmpGender;
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmpGender = null;
          } else {
            _tmpGender = _stmt.getText(_columnIndexOfGender);
          }
          final String _tmpHobbies;
          if (_stmt.isNull(_columnIndexOfHobbies)) {
            _tmpHobbies = null;
          } else {
            _tmpHobbies = _stmt.getText(_columnIndexOfHobbies);
          }
          final String _tmpCity;
          if (_stmt.isNull(_columnIndexOfCity)) {
            _tmpCity = null;
          } else {
            _tmpCity = _stmt.getText(_columnIndexOfCity);
          }
          final String _tmpProfilePictureUrl;
          if (_stmt.isNull(_columnIndexOfProfilePictureUrl)) {
            _tmpProfilePictureUrl = null;
          } else {
            _tmpProfilePictureUrl = _stmt.getText(_columnIndexOfProfilePictureUrl);
          }
          final String _tmpPassword;
          if (_stmt.isNull(_columnIndexOfPassword)) {
            _tmpPassword = null;
          } else {
            _tmpPassword = _stmt.getText(_columnIndexOfPassword);
          }
          final String _tmpRelationshipStatus;
          if (_stmt.isNull(_columnIndexOfRelationshipStatus)) {
            _tmpRelationshipStatus = null;
          } else {
            _tmpRelationshipStatus = _stmt.getText(_columnIndexOfRelationshipStatus);
          }
          final String _tmpOccupation;
          if (_stmt.isNull(_columnIndexOfOccupation)) {
            _tmpOccupation = null;
          } else {
            _tmpOccupation = _stmt.getText(_columnIndexOfOccupation);
          }
          final int _tmpAge;
          _tmpAge = (int) (_stmt.getLong(_columnIndexOfAge));
          _result = new User(_tmpId,_tmpName,_tmpEmail,_tmpGender,_tmpHobbies,_tmpCity,_tmpProfilePictureUrl,_tmpPassword,_tmpRelationshipStatus,_tmpOccupation,_tmpAge);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, arg1);
  }

  @Override
  public Object getUserById(final long id, final Continuation<? super User> arg1) {
    final String _sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfGender = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gender");
        final int _columnIndexOfHobbies = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hobbies");
        final int _columnIndexOfCity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "city");
        final int _columnIndexOfProfilePictureUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profilePictureUrl");
        final int _columnIndexOfPassword = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "password");
        final int _columnIndexOfRelationshipStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "relationshipStatus");
        final int _columnIndexOfOccupation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "occupation");
        final int _columnIndexOfAge = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "age");
        final User _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          final String _tmpGender;
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmpGender = null;
          } else {
            _tmpGender = _stmt.getText(_columnIndexOfGender);
          }
          final String _tmpHobbies;
          if (_stmt.isNull(_columnIndexOfHobbies)) {
            _tmpHobbies = null;
          } else {
            _tmpHobbies = _stmt.getText(_columnIndexOfHobbies);
          }
          final String _tmpCity;
          if (_stmt.isNull(_columnIndexOfCity)) {
            _tmpCity = null;
          } else {
            _tmpCity = _stmt.getText(_columnIndexOfCity);
          }
          final String _tmpProfilePictureUrl;
          if (_stmt.isNull(_columnIndexOfProfilePictureUrl)) {
            _tmpProfilePictureUrl = null;
          } else {
            _tmpProfilePictureUrl = _stmt.getText(_columnIndexOfProfilePictureUrl);
          }
          final String _tmpPassword;
          if (_stmt.isNull(_columnIndexOfPassword)) {
            _tmpPassword = null;
          } else {
            _tmpPassword = _stmt.getText(_columnIndexOfPassword);
          }
          final String _tmpRelationshipStatus;
          if (_stmt.isNull(_columnIndexOfRelationshipStatus)) {
            _tmpRelationshipStatus = null;
          } else {
            _tmpRelationshipStatus = _stmt.getText(_columnIndexOfRelationshipStatus);
          }
          final String _tmpOccupation;
          if (_stmt.isNull(_columnIndexOfOccupation)) {
            _tmpOccupation = null;
          } else {
            _tmpOccupation = _stmt.getText(_columnIndexOfOccupation);
          }
          final int _tmpAge;
          _tmpAge = (int) (_stmt.getLong(_columnIndexOfAge));
          _result = new User(_tmpId,_tmpName,_tmpEmail,_tmpGender,_tmpHobbies,_tmpCity,_tmpProfilePictureUrl,_tmpPassword,_tmpRelationshipStatus,_tmpOccupation,_tmpAge);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, arg1);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
