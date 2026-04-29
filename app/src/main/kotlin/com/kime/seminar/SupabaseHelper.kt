package com.kime.seminar

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.storage.Storage

object SupabaseHelper {
    // GANTI DENGAN URL DAN KEY MILIKMU
    private const val SUPABASE_URL = "https://llhhejtkkplcpjclfczq.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxsaGhlanRra3BsY3BqY2xmY3pxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY4NjU5NzgsImV4cCI6MjA5MjQ0MTk3OH0.-hLaf5U_tuIlrGUOUo1jJkmXFoiFq5PpvFCh95CnYo0"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
}