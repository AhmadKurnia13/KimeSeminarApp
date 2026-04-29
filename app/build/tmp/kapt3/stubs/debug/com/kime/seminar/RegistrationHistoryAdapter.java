package com.kime.seminar;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00102\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002\u000f\u0010B\u0007\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\nH\u0016\u00a8\u0006\u0011"}, d2 = {"Lcom/kime/seminar/RegistrationHistoryAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/kime/seminar/database/RegistrationWithSeminar;", "Lcom/kime/seminar/RegistrationHistoryAdapter$RegistrationViewHolder;", "<init>", "()V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "", "holder", "position", "RegistrationViewHolder", "Companion", "app_debug"})
public final class RegistrationHistoryAdapter extends androidx.recyclerview.widget.ListAdapter<com.kime.seminar.database.RegistrationWithSeminar, com.kime.seminar.RegistrationHistoryAdapter.RegistrationViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.recyclerview.widget.DiffUtil.ItemCallback<com.kime.seminar.database.RegistrationWithSeminar> DiffCallback = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.kime.seminar.RegistrationHistoryAdapter.Companion Companion = null;
    
    public RegistrationHistoryAdapter() {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.kime.seminar.RegistrationHistoryAdapter.RegistrationViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.kime.seminar.RegistrationHistoryAdapter.RegistrationViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/kime/seminar/RegistrationHistoryAdapter$Companion;", "", "<init>", "()V", "DiffCallback", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/kime/seminar/RegistrationHistoryAdapter$RegistrationViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/kime/seminar/databinding/ItemRegistrationHistoryBinding;", "<init>", "(Lcom/kime/seminar/databinding/ItemRegistrationHistoryBinding;)V", "bind", "", "registration", "Lcom/kime/seminar/database/RegistrationWithSeminar;", "app_debug"})
    public static final class RegistrationViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.kime.seminar.databinding.ItemRegistrationHistoryBinding binding = null;
        
        public RegistrationViewHolder(@org.jetbrains.annotations.NotNull()
        com.kime.seminar.databinding.ItemRegistrationHistoryBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.kime.seminar.database.RegistrationWithSeminar registration) {
        }
    }
}