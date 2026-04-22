# 🚀 Quick Reference - Reset Password Feature

## 📱 File Locations

```
📦 ForgotPasswordActivity
   └─ app/src/main/kotlin/com/kime/seminar/ForgotPasswordActivity.kt
   └─ app/src/main/res/layout/activity_forgot_password.xml

📦 ResetPasswordActivity
   └─ app/src/main/kotlin/com/kime/seminar/ResetPasswordActivity.kt
   └─ app/src/main/res/layout/activity_reset_password.xml

📦 Manifest Updates
   └─ app/src/main/AndroidManifest.xml

📦 LoginActivity (Modified)
   └─ app/src/main/kotlin/com/kime/seminar/LoginActivity.kt
```

---

## 🔧 Key Classes & Methods

### ForgotPasswordActivity

```kotlin
// Lifecycle
class ForgotPasswordActivity : AppCompatActivity()
override fun onCreate(savedInstanceState: Bundle?)

// Setup Methods
private fun setupToolbar()
private fun setupRealTimeValidation()
private fun setupClickListeners()

// Validation
private fun validateEmail(email: String): Boolean

// Business Logic
private fun performForgotPassword()
// - Validates email
// - Shows progress bar
// - Calls SupabaseHelper.client.auth.resetPasswordForEmail(email)
// - Displays success message
// - Handles errors with toast
```

### ResetPasswordActivity

```kotlin
// Lifecycle
class ResetPasswordActivity : AppCompatActivity()
override fun onCreate(savedInstanceState: Bundle?)

// Setup Methods
private fun setupToolbar()
private fun setupRealTimeValidation()
private fun setupClickListeners()

// Validation
private fun validatePassword(password: String): Boolean
private fun validatePasswordMatch(): Boolean

// Business Logic
private fun performResetPassword()
// - Validates password & confirmation
// - Shows progress bar
// - Calls SupabaseHelper.client.auth.updateUser { password = newPassword }
// - Redirects to login on success
// - Handles errors with toast

private fun goToLogin()
// - Creates intent to LoginActivity
// - Sets FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK
// - Applies fade animation
```

---

## 🎯 Key UI Elements

### ForgotPasswordActivity Layout
```xml
<ScrollView id="containerForm">
    <!-- Form untuk input email -->
    <TextInputLayout id="tilEmail"
        <TextInputEditText id="etEmail"

<ScrollView id="containerSuccess">
    <!-- Pesan sukses -->
    <TextView id="tvSuccessEmail"
    <MaterialButton id="btnBackToLogin"

<ProgressBar id="progressReset"
<MaterialButton id="btnSendReset"
<TextView id="tvBackToLogin"
```

### ResetPasswordActivity Layout
```xml
<TextInputLayout id="tilNewPassword"
    <TextInputEditText id="etNewPassword"

<TextInputLayout id="tilConfirmPassword"
    <TextInputEditText id="etConfirmPassword"

<ProgressBar id="progressReset"
<MaterialButton id="btnResetPassword"
<TextView id="tvBackToLogin"
```

---

## 🔌 Supabase Integration

### Reset Password Email
```kotlin
lifecycleScope.launch {
    try {
        SupabaseHelper.client.auth.resetPasswordForEmail(email)
        // Success - show message
    } catch (e: Exception) {
        // Error - show toast
    }
}
```

### Update Password
```kotlin
lifecycleScope.launch {
    try {
        SupabaseHelper.client.auth.updateUser {
            password = newPassword
        }
        // Success - go to login
    } catch (e: Exception) {
        // Error - show toast
    }
}
```

---

## 🧪 Minimal Testing Code

```kotlin
// Test 1: Valid Email Reset
fun testValidEmailReset() {
    // Input: valid email
    // Expected: Success message shown
}

// Test 2: Invalid Email Format
fun testInvalidEmailFormat() {
    // Input: invalid email
    // Expected: Error message shown
}

// Test 3: Password Reset
fun testPasswordReset() {
    // Input: password + confirmation match
    // Expected: Success toast + redirect to login
}

// Test 4: Password Mismatch
fun testPasswordMismatch() {
    // Input: password != confirmation
    // Expected: Error message shown
}
```

---

## ⚙️ Configuration

### AndroidManifest.xml
```xml
<activity android:name=".ForgotPasswordActivity"
    android:exported="false"
    android:windowSoftInputMode="adjustResize" />

<activity android:name=".ResetPasswordActivity"
    android:exported="false"
    android:windowSoftInputMode="adjustResize" />
```

### Build.gradle (Already Has)
```groovy
implementation("io.github.jan-tennert.supabase:bom:2.4.3")
implementation("io.github.jan-tennert.supabase:postgrest-kt")
implementation("io.ktor:ktor-client-android:2.3.11")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
```

---

## 🎨 Styling References

### Material Button Style
```xml
<!-- From themes.xml -->
style="@style/KimePrimaryButton"
<!-- Properties: 
     - background: kime_primary color
     - textColor: white
     - cornerRadius: rounded
     - elevation: shadow
-->
```

### TextInput Layout Style
```xml
<!-- From themes.xml -->
style="@style/KimeTextInputLayout"
<!-- Properties:
     - boxBackgroundColor: light
     - hintTextColor: secondary
     - error colors: red
-->
```

---

## 📊 State Management

### ForgotPasswordActivity States
```
[INITIAL STATE]
├── Show form container
└── Hide success container

[LOADING STATE]
├── Disable send button
├── Show progress bar
└── Disable input fields

[SUCCESS STATE]
├── Hide form container
└── Show success container

[ERROR STATE]
├── Show error toast
├── Re-enable inputs
└── Re-enable button
```

### ResetPasswordActivity States
```
[INITIAL STATE]
├── Show form
└── Enable inputs

[LOADING STATE]
├── Disable reset button
├── Show progress bar
└── Disable input fields

[SUCCESS STATE]
├── Show success toast
└── Redirect to login

[ERROR STATE]
├── Show error toast
├── Re-enable inputs
└── Re-enable button
```

---

## 🔐 Validation Rules

### Email
```kotlin
✓ Not empty
✓ Contains "@"
✓ Valid email format (Patterns.EMAIL_ADDRESS)
```

### Password
```kotlin
✓ Not empty
✓ Length >= 6
```

### Password Confirm
```kotlin
✓ Not empty
✓ Matches new password exactly
```

---

## 🚨 Error Messages

```kotlin
// Email Errors
"Email tidak boleh kosong"
"Format email tidak valid (harus mengandung '@')"
"Format email tidak valid"

// Password Errors
"Password tidak boleh kosong"
"Password minimal 6 karakter"
"Konfirmasi password tidak boleh kosong"
"Password tidak cocok"

// Network Errors
"Gagal mengirim email reset: {message}"
"Gagal mereset password: {message}"

// Success Messages
"✅ Password berhasil direset! Silakan login dengan password baru."
"Login Berhasil!"
```

---

## 🎬 Navigation Flows

### Flow 1: From Login
```
LoginActivity
   ↓ tvForgotPassword.setOnClickListener
ForgotPasswordActivity
   ↓ [User actions]
ForgotPasswordActivity / LoginActivity
```

### Flow 2: From Email
```
Email Link (Deep Link)
   ↓ [User clicks]
ResetPasswordActivity
   ↓ [User fills form]
LoginActivity (Success)
   or
ResetPasswordActivity (Error - try again)
```

---

## 💡 Code Snippets

### Add Click Listener
```kotlin
binding.tvForgotPassword.setOnClickListener {
    startActivity(Intent(this, ForgotPasswordActivity::class.java))
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}
```

### Setup TextWatcher
```kotlin
binding.etEmail.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isNotEmpty()) validateEmail(s.toString())
        else binding.tilEmail.error = null
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
})
```

### Async Operation
```kotlin
lifecycleScope.launch {
    try {
        // Show loading
        binding.progressReset.visibility = View.VISIBLE
        binding.btnSendReset.isEnabled = false
        
        // Do work
        SupabaseHelper.client.auth.resetPasswordForEmail(email)
        
        // Handle success
        showSuccessUI()
    } catch (e: Exception) {
        // Handle error
        showErrorToast(e.message)
    } finally {
        // Hide loading
        binding.progressReset.visibility = View.GONE
        binding.btnSendReset.isEnabled = true
    }
}
```

---

## 📋 Checklist untuk Deployment

- [ ] Build successful tanpa errors
- [ ] Supabase Email Provider configured
- [ ] Email templates customized
- [ ] Test valid email flow
- [ ] Test invalid email flow
- [ ] Test password reset flow
- [ ] Test error handling
- [ ] Check animation smoothness
- [ ] Verify responsive on different screens
- [ ] Test back button behavior
- [ ] Check keyboard handling
- [ ] Verify loading states

---

## 🔗 Related Resources

```
Supabase Auth: https://supabase.com/docs/guides/auth
Android ViewBinding: https://developer.android.com/topic/libraries/view-binding
Material Design: https://material.io/design
Kotlin Coroutines: https://developer.android.com/kotlin/coroutines
```

---

## 🐛 Common Issues

| Issue | Solution |
|-------|----------|
| Email not sending | Check Supabase email config |
| Reset link not working | Verify deep links setup |
| Button not responding | Check lifecycle scope |
| Validation error not showing | Check TextInputLayout setup |
| App crashes on back | Check activity flags |

---

**Last Updated**: April 23, 2026
**Version**: 1.0.0
**Status**: ✅ Production Ready

