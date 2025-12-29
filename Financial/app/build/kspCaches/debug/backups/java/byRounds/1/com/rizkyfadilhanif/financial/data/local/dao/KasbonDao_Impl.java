package com.rizkyfadilhanif.financial.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rizkyfadilhanif.financial.data.local.entity.KasbonEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class KasbonDao_Impl implements KasbonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<KasbonEntity> __insertionAdapterOfKasbonEntity;

  private final EntityDeletionOrUpdateAdapter<KasbonEntity> __deletionAdapterOfKasbonEntity;

  private final EntityDeletionOrUpdateAdapter<KasbonEntity> __updateAdapterOfKasbonEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteKasbonById;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsPaid;

  public KasbonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfKasbonEntity = new EntityInsertionAdapter<KasbonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `kasbon` (`id`,`employeeId`,`amount`,`description`,`isPaid`,`date`,`paidDate`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final KasbonEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindString(4, entity.getDescription());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getDate());
        if (entity.getPaidDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPaidDate());
        }
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfKasbonEntity = new EntityDeletionOrUpdateAdapter<KasbonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `kasbon` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final KasbonEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfKasbonEntity = new EntityDeletionOrUpdateAdapter<KasbonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `kasbon` SET `id` = ?,`employeeId` = ?,`amount` = ?,`description` = ?,`isPaid` = ?,`date` = ?,`paidDate` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final KasbonEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindString(4, entity.getDescription());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getDate());
        if (entity.getPaidDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPaidDate());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteKasbonById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM kasbon WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsPaid = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE kasbon SET isPaid = 1, paidDate = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertKasbon(final KasbonEntity kasbon,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfKasbonEntity.insertAndReturnId(kasbon);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteKasbon(final KasbonEntity kasbon,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfKasbonEntity.handle(kasbon);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateKasbon(final KasbonEntity kasbon,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfKasbonEntity.handle(kasbon);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteKasbonById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteKasbonById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteKasbonById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsPaid(final long id, final long paidDate,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsPaid.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, paidDate);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsPaid.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<KasbonEntity>> getAllKasbon() {
    final String _sql = "SELECT * FROM kasbon ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"kasbon"}, new Callable<List<KasbonEntity>>() {
      @Override
      @NonNull
      public List<KasbonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPaidDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paidDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<KasbonEntity> _result = new ArrayList<KasbonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final KasbonEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getLong(_cursorIndexOfEmployeeId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpPaidDate;
            if (_cursor.isNull(_cursorIndexOfPaidDate)) {
              _tmpPaidDate = null;
            } else {
              _tmpPaidDate = _cursor.getLong(_cursorIndexOfPaidDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new KasbonEntity(_tmpId,_tmpEmployeeId,_tmpAmount,_tmpDescription,_tmpIsPaid,_tmpDate,_tmpPaidDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<KasbonEntity>> getKasbonByEmployee(final long employeeId) {
    final String _sql = "SELECT * FROM kasbon WHERE employeeId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, employeeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"kasbon"}, new Callable<List<KasbonEntity>>() {
      @Override
      @NonNull
      public List<KasbonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPaidDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paidDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<KasbonEntity> _result = new ArrayList<KasbonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final KasbonEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getLong(_cursorIndexOfEmployeeId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpPaidDate;
            if (_cursor.isNull(_cursorIndexOfPaidDate)) {
              _tmpPaidDate = null;
            } else {
              _tmpPaidDate = _cursor.getLong(_cursorIndexOfPaidDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new KasbonEntity(_tmpId,_tmpEmployeeId,_tmpAmount,_tmpDescription,_tmpIsPaid,_tmpDate,_tmpPaidDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<KasbonEntity>> getKasbonByStatus(final boolean isPaid) {
    final String _sql = "SELECT * FROM kasbon WHERE isPaid = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = isPaid ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"kasbon"}, new Callable<List<KasbonEntity>>() {
      @Override
      @NonNull
      public List<KasbonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPaidDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paidDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<KasbonEntity> _result = new ArrayList<KasbonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final KasbonEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getLong(_cursorIndexOfEmployeeId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsPaid;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp_1 != 0;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpPaidDate;
            if (_cursor.isNull(_cursorIndexOfPaidDate)) {
              _tmpPaidDate = null;
            } else {
              _tmpPaidDate = _cursor.getLong(_cursorIndexOfPaidDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new KasbonEntity(_tmpId,_tmpEmployeeId,_tmpAmount,_tmpDescription,_tmpIsPaid,_tmpDate,_tmpPaidDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getKasbonById(final long id, final Continuation<? super KasbonEntity> $completion) {
    final String _sql = "SELECT * FROM kasbon WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<KasbonEntity>() {
      @Override
      @Nullable
      public KasbonEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPaidDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paidDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final KasbonEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getLong(_cursorIndexOfEmployeeId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpPaidDate;
            if (_cursor.isNull(_cursorIndexOfPaidDate)) {
              _tmpPaidDate = null;
            } else {
              _tmpPaidDate = _cursor.getLong(_cursorIndexOfPaidDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new KasbonEntity(_tmpId,_tmpEmployeeId,_tmpAmount,_tmpDescription,_tmpIsPaid,_tmpDate,_tmpPaidDate,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Double> getTotalUnpaidKasbon() {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM kasbon WHERE isPaid = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"kasbon"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
