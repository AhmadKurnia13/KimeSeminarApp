package com.kime.seminar.database;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class RegistrationDao_Impl implements RegistrationDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Registration> __insertAdapterOfRegistration;

  public RegistrationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfRegistration = new EntityInsertAdapter<Registration>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `registrations` (`id`,`userId`,`seminarId`,`registrationDate`,`status`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Registration entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getSeminarId());
        if (entity.getRegistrationDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getRegistrationDate());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getStatus());
        }
      }
    };
  }

  @Override
  public Object insertRegistration(final Registration registration,
      final Continuation<? super Long> arg1) {
    if (registration == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfRegistration.insertAndReturnId(_connection, registration);
    }, arg1);
  }

  @Override
  public Object getRegistrationsByUserId(final long userId,
      final Continuation<? super List<Registration>> arg1) {
    final String _sql = "SELECT * FROM registrations WHERE userId = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSeminarId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "seminarId");
        final int _columnIndexOfRegistrationDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "registrationDate");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final List<Registration> _result = new ArrayList<Registration>();
        while (_stmt.step()) {
          final Registration _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpUserId;
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId);
          final long _tmpSeminarId;
          _tmpSeminarId = _stmt.getLong(_columnIndexOfSeminarId);
          final String _tmpRegistrationDate;
          if (_stmt.isNull(_columnIndexOfRegistrationDate)) {
            _tmpRegistrationDate = null;
          } else {
            _tmpRegistrationDate = _stmt.getText(_columnIndexOfRegistrationDate);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          _item = new Registration(_tmpId,_tmpUserId,_tmpSeminarId,_tmpRegistrationDate,_tmpStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, arg1);
  }

  @Override
  public Object getRegistrationsWithSeminarDetails(final long userId,
      final Continuation<? super List<RegistrationWithSeminar>> arg1) {
    final String _sql = "\n"
            + "        SELECT r.*, s.title, s.date, s.time, s.location, s.speaker\n"
            + "        FROM registrations r\n"
            + "        INNER JOIN seminars s ON r.seminarId = s.id\n"
            + "        WHERE r.userId = ?\n"
            + "        ORDER BY r.registrationDate DESC\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSeminarId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "seminarId");
        final int _columnIndexOfRegistrationDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "registrationDate");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "time");
        final int _columnIndexOfLocation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "location");
        final int _columnIndexOfSpeaker = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "speaker");
        final List<RegistrationWithSeminar> _result = new ArrayList<RegistrationWithSeminar>();
        while (_stmt.step()) {
          final RegistrationWithSeminar _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpUserId;
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId);
          final long _tmpSeminarId;
          _tmpSeminarId = _stmt.getLong(_columnIndexOfSeminarId);
          final String _tmpRegistrationDate;
          if (_stmt.isNull(_columnIndexOfRegistrationDate)) {
            _tmpRegistrationDate = null;
          } else {
            _tmpRegistrationDate = _stmt.getText(_columnIndexOfRegistrationDate);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDate;
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmpDate = null;
          } else {
            _tmpDate = _stmt.getText(_columnIndexOfDate);
          }
          final String _tmpTime;
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmpTime = null;
          } else {
            _tmpTime = _stmt.getText(_columnIndexOfTime);
          }
          final String _tmpLocation;
          if (_stmt.isNull(_columnIndexOfLocation)) {
            _tmpLocation = null;
          } else {
            _tmpLocation = _stmt.getText(_columnIndexOfLocation);
          }
          final String _tmpSpeaker;
          if (_stmt.isNull(_columnIndexOfSpeaker)) {
            _tmpSpeaker = null;
          } else {
            _tmpSpeaker = _stmt.getText(_columnIndexOfSpeaker);
          }
          _item = new RegistrationWithSeminar(_tmpId,_tmpUserId,_tmpSeminarId,_tmpRegistrationDate,_tmpStatus,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpSpeaker);
          _result.add(_item);
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
