package com.kime.seminar;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0002J6\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u0013H\u0002J\b\u0010\u001f\u001a\u00020\u0013H\u0002J\b\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\u0013H\u0002J\b\u0010\"\u001a\u00020\u0013H\u0002J\u001c\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00130&H\u0002J\u0010\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0011H\u0002J\u0010\u0010*\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0011H\u0002J\u0010\u0010+\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0011H\u0002J\b\u0010,\u001a\u00020(H\u0002J\b\u0010-\u001a\u00020(H\u0002J\b\u0010.\u001a\u00020(H\u0002J\b\u0010/\u001a\u00020(H\u0002J\b\u00100\u001a\u00020\u0013H\u0002J\b\u00101\u001a\u00020\u0013H\u0002J(\u00102\u001a\u00020\u00132\u0006\u00103\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u00112\u0006\u00105\u001a\u00020\u0011H\u0002J8\u00106\u001a\u00020\u00132\u0006\u00103\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u00107\u001a\u00020\u00112\u0006\u00105\u001a\u00020\u0011H\u0002J\b\u00108\u001a\u00020\u0013H\u0002J\u0010\u00109\u001a\u00020(2\u0006\u0010:\u001a\u00020;H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lcom/kime/seminar/FormActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "binding", "Lcom/kime/seminar/databinding/ActivityFormBinding;", "calendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "sessionManager", "Lcom/kime/seminar/SessionManager;", "registrationRepository", "Lcom/kime/seminar/database/RegistrationRepository;", "seminarRepository", "Lcom/kime/seminar/database/SeminarRepository;", "seminars", "", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "loadSeminarsFromDb", "insertRegistrationData", "seminarId", "name", "email", "phone", "jenisKelamin", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setupToolbar", "setupSpinner", "setupDatePicker", "showDatePicker", "setupRealTimeValidation", "createWatcher", "Landroid/text/TextWatcher;", "validate", "Lkotlin/Function1;", "validateNama", "", "value", "validateEmail", "validateNoHp", "validateGender", "validateTanggal", "validateSeminar", "validateCheckbox", "setupClickListeners", "submitForm", "showConfirmationDialog", "nama", "noHp", "tanggal", "goToResult", "seminar", "resetForm", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "app_debug"})
public final class FormActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.kime.seminar.databinding.ActivityFormBinding binding;
    private final java.util.Calendar calendar = null;
    private com.kime.seminar.SessionManager sessionManager;
    private com.kime.seminar.database.RegistrationRepository registrationRepository;
    private com.kime.seminar.database.SeminarRepository seminarRepository;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> seminars = null;
    
    public FormActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadSeminarsFromDb() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertRegistrationData(@org.jetbrains.annotations.NotNull()
    java.lang.String seminarId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String jenisKelamin, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupSpinner() {
    }
    
    private final void setupDatePicker() {
    }
    
    private final void showDatePicker() {
    }
    
    private final void setupRealTimeValidation() {
    }
    
    private final android.text.TextWatcher createWatcher(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> validate) {
        return null;
    }
    
    private final boolean validateNama(java.lang.String value) {
        return false;
    }
    
    private final boolean validateEmail(java.lang.String value) {
        return false;
    }
    
    private final boolean validateNoHp(java.lang.String value) {
        return false;
    }
    
    private final boolean validateGender() {
        return false;
    }
    
    private final boolean validateTanggal() {
        return false;
    }
    
    private final boolean validateSeminar() {
        return false;
    }
    
    private final boolean validateCheckbox() {
        return false;
    }
    
    private final void setupClickListeners() {
    }
    
    private final void submitForm() {
    }
    
    private final void showConfirmationDialog(java.lang.String nama, java.lang.String email, java.lang.String noHp, java.lang.String tanggal) {
    }
    
    private final void goToResult(java.lang.String nama, java.lang.String email, java.lang.String noHp, java.lang.String jenisKelamin, java.lang.String seminar, java.lang.String tanggal) {
    }
    
    private final void resetForm() {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}