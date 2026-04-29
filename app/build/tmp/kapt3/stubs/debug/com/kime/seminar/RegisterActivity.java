package com.kime.seminar;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\f\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\tH\u0002J!\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\t0\u0011H\u0002\u00a2\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\tH\u0002J\b\u0010\u0015\u001a\u00020\tH\u0002J\b\u0010\u0016\u001a\u00020\tH\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00120\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0012H\u0002J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0012H\u0002J\u0018\u0010!\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u0012H\u0002J\u0010\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0012H\u0002J\b\u0010%\u001a\u00020\tH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/kime/seminar/RegisterActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "binding", "Lcom/kime/seminar/databinding/ActivityRegisterBinding;", "sessionManager", "Lcom/kime/seminar/SessionManager;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setupAccountTypeSpinner", "setupRealTimeValidation", "createWatcher", "Landroid/text/TextWatcher;", "after", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)Landroid/text/TextWatcher;", "setupClickListeners", "resetForm", "showConfirmationDialog", "validateAll", "", "getSelectedHobbies", "", "validateName", "name", "validateEmail", "email", "validatePassword", "password", "validateConfirmPassword", "confirm", "validateCity", "city", "performRegister", "app_debug"})
public final class RegisterActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.kime.seminar.databinding.ActivityRegisterBinding binding;
    private com.kime.seminar.SessionManager sessionManager;
    
    public RegisterActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupAccountTypeSpinner() {
    }
    
    private final void setupRealTimeValidation() {
    }
    
    private final android.text.TextWatcher createWatcher(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> after) {
        return null;
    }
    
    private final void setupClickListeners() {
    }
    
    private final void resetForm() {
    }
    
    private final void showConfirmationDialog() {
    }
    
    private final boolean validateAll() {
        return false;
    }
    
    private final java.util.List<java.lang.String> getSelectedHobbies() {
        return null;
    }
    
    private final boolean validateName(java.lang.String name) {
        return false;
    }
    
    private final boolean validateEmail(java.lang.String email) {
        return false;
    }
    
    private final boolean validatePassword(java.lang.String password) {
        return false;
    }
    
    private final boolean validateConfirmPassword(java.lang.String password, java.lang.String confirm) {
        return false;
    }
    
    private final boolean validateCity(java.lang.String city) {
        return false;
    }
    
    private final void performRegister() {
    }
}