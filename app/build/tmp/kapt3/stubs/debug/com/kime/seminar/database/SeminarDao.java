package com.kime.seminar.database;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u0013\u00c0\u0006\u0003"}, d2 = {"Lcom/kime/seminar/database/SeminarDao;", "", "insertSeminar", "", "seminar", "Lcom/kime/seminar/database/Seminar;", "(Lcom/kime/seminar/database/Seminar;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSeminars", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSeminarById", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSeminarByTitle", "title", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "decrementQuota", "", "app_debug"})
@androidx.room.Dao()
public abstract interface SeminarDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSeminar(@org.jetbrains.annotations.NotNull()
    com.kime.seminar.database.Seminar seminar, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM seminars")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllSeminars(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kime.seminar.database.Seminar>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM seminars WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSeminarById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.kime.seminar.database.Seminar> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM seminars WHERE title = :title LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSeminarByTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.kime.seminar.database.Seminar> $completion);
    
    @androidx.room.Query(value = "UPDATE seminars SET quota = quota - 1 WHERE id = :id AND quota > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object decrementQuota(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}