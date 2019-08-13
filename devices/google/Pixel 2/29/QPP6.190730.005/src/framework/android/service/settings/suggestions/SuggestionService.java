/*
 * Decompiled with CFR 0.145.
 */
package android.service.settings.suggestions;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.settings.suggestions.ISuggestionService;
import android.service.settings.suggestions.Suggestion;
import java.util.List;

@SystemApi
public abstract class SuggestionService
extends Service {
    private static final boolean DEBUG = false;
    private static final String TAG = "SuggestionService";

    @Override
    public IBinder onBind(Intent intent) {
        return new ISuggestionService.Stub(){

            @Override
            public void dismissSuggestion(Suggestion suggestion) {
                SuggestionService.this.onSuggestionDismissed(suggestion);
            }

            @Override
            public List<Suggestion> getSuggestions() {
                return SuggestionService.this.onGetSuggestions();
            }

            @Override
            public void launchSuggestion(Suggestion suggestion) {
                SuggestionService.this.onSuggestionLaunched(suggestion);
            }
        };
    }

    public abstract List<Suggestion> onGetSuggestions();

    public abstract void onSuggestionDismissed(Suggestion var1);

    public abstract void onSuggestionLaunched(Suggestion var1);

}

