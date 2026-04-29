package com.kime.seminar.database;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteConnectionUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
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
public final class SeminarDao_Impl implements SeminarDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Seminar> __insertAdapterOfSeminar;

  public SeminarDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfSeminar = new EntityInsertAdapter<Seminar>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `seminars` (`id`,`title`,`date`,`time`,`location`,`quota`,`summary`,`speaker`,`materials`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Seminar entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDate());
        }
        if (entity.getTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getTime());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getLocation());
        }
        statement.bindLong(6, entity.getQuota());
        if (entity.getSummary() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getSummary());
        }
        if (entity.getSpeaker() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getSpeaker());
        }
        if (entity.getMaterials() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getMaterials());
        }
      }
    };
  }

  @Override
  public Object insertSeminar(final Seminar seminar, final Continuation<? super Long> $completion) {
    if (seminar == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfSeminar.insertAndReturnId(_connection, seminar);
    }, $completion);
  }

  @Override
  public Object getAllSeminars(final Continuation<? super List<Seminar>> $completion) {
    final String _sql = "SELECT * FROM seminars";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "time");
        final int _columnIndexOfLocation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "location");
        final int _columnIndexOfQuota = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quota");
        final int _columnIndexOfSummary = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "summary");
        final int _columnIndexOfSpeaker = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "speaker");
        final int _columnIndexOfMaterials = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "materials");
        final List<Seminar> _result = new ArrayList<Seminar>();
        while (_stmt.step()) {
          final Seminar _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
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
          final int _tmpQuota;
          _tmpQuota = (int) (_stmt.getLong(_columnIndexOfQuota));
          final String _tmpSummary;
          if (_stmt.isNull(_columnIndexOfSummary)) {
            _tmpSummary = null;
          } else {
            _tmpSummary = _stmt.getText(_columnIndexOfSummary);
          }
          final String _tmpSpeaker;
          if (_stmt.isNull(_columnIndexOfSpeaker)) {
            _tmpSpeaker = null;
          } else {
            _tmpSpeaker = _stmt.getText(_columnIndexOfSpeaker);
          }
          final String _tmpMaterials;
          if (_stmt.isNull(_columnIndexOfMaterials)) {
            _tmpMaterials = null;
          } else {
            _tmpMaterials = _stmt.getText(_columnIndexOfMaterials);
          }
          _item = new Seminar(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpQuota,_tmpSummary,_tmpSpeaker,_tmpMaterials);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getSeminarById(final long id, final Continuation<? super Seminar> $completion) {
    final String _sql = "SELECT * FROM seminars WHERE id = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "time");
        final int _columnIndexOfLocation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "location");
        final int _columnIndexOfQuota = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quota");
        final int _columnIndexOfSummary = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "summary");
        final int _columnIndexOfSpeaker = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "speaker");
        final int _columnIndexOfMaterials = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "materials");
        final Seminar _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
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
          final int _tmpQuota;
          _tmpQuota = (int) (_stmt.getLong(_columnIndexOfQuota));
          final String _tmpSummary;
          if (_stmt.isNull(_columnIndexOfSummary)) {
            _tmpSummary = null;
          } else {
            _tmpSummary = _stmt.getText(_columnIndexOfSummary);
          }
          final String _tmpSpeaker;
          if (_stmt.isNull(_columnIndexOfSpeaker)) {
            _tmpSpeaker = null;
          } else {
            _tmpSpeaker = _stmt.getText(_columnIndexOfSpeaker);
          }
          final String _tmpMaterials;
          if (_stmt.isNull(_columnIndexOfMaterials)) {
            _tmpMaterials = null;
          } else {
            _tmpMaterials = _stmt.getText(_columnIndexOfMaterials);
          }
          _result = new Seminar(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpQuota,_tmpSummary,_tmpSpeaker,_tmpMaterials);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getSeminarByTitle(final String title,
      final Continuation<? super Seminar> $completion) {
    final String _sql = "SELECT * FROM seminars WHERE title = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, title);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "time");
        final int _columnIndexOfLocation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "location");
        final int _columnIndexOfQuota = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quota");
        final int _columnIndexOfSummary = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "summary");
        final int _columnIndexOfSpeaker = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "speaker");
        final int _columnIndexOfMaterials = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "materials");
        final Seminar _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
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
          final int _tmpQuota;
          _tmpQuota = (int) (_stmt.getLong(_columnIndexOfQuota));
          final String _tmpSummary;
          if (_stmt.isNull(_columnIndexOfSummary)) {
            _tmpSummary = null;
          } else {
            _tmpSummary = _stmt.getText(_columnIndexOfSummary);
          }
          final String _tmpSpeaker;
          if (_stmt.isNull(_columnIndexOfSpeaker)) {
            _tmpSpeaker = null;
          } else {
            _tmpSpeaker = _stmt.getText(_columnIndexOfSpeaker);
          }
          final String _tmpMaterials;
          if (_stmt.isNull(_columnIndexOfMaterials)) {
            _tmpMaterials = null;
          } else {
            _tmpMaterials = _stmt.getText(_columnIndexOfMaterials);
          }
          _result = new Seminar(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpQuota,_tmpSummary,_tmpSpeaker,_tmpMaterials);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object decrementQuota(final long id, final Continuation<? super Integer> $completion) {
    final String _sql = "UPDATE seminars SET quota = quota - 1 WHERE id = ? AND quota > 0";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return SQLiteConnectionUtil.getTotalChangedRows(_connection);
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
