package com.kime.seminar.database;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b2\u0006\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\r\u00c0\u0006\u0003"}, d2 = {"Lcom/kime/seminar/database/RegistrationDao;", "", "insertRegistration", "", "registration", "Lcom/kime/seminar/database/Registration;", "(Lcom/kime/seminar/database/Registration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRegistrationsByUserId", "", "userId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRegistrationsWithSeminarDetails", "Lcom/kime/seminar/database/RegistrationWithSeminar;", "app_debug"})
@androidx.room.Dao()
public abstract interface RegistrationDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRegistration(@org.jetbrains.annotations.NotNull()
    com.kime.seminar.database.Registration registration, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM registrations WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRegistrationsByUserId(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kime.seminar.database.Registration>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT r.*, s.title, s.date, s.time, s.location, s.speaker\n        FROM registrations r\n        INNER JOIN seminars s ON r.seminarId = s.id\n        WHERE r.userId = :userId\n        ORDER BY r.registrationDate DESC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRegistrationsWithSeminarDetails(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kime.seminar.database.RegistrationWithSeminar>> $completion);
}