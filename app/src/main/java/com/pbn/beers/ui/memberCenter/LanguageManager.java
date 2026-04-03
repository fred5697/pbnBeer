package com.pbn.beers.ui.memberCenter;

import android.app.LocaleManager;
import android.content.Context;
import android.os.Build;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

/**
 * Utility for managing app language selection.
 * Supports Android 13+ via LocaleManager and fallback for older versions via AppCompatDelegate.
 */
public class LanguageManager {

    private static final String PREF_LANGUAGE = "selected_language";

    /**
     * Language codes supported by the app.
     */
    public enum Language {
        TRADITIONAL_CHINESE("zh-TW", "繁體中文"),
        SIMPLIFIED_CHINESE("zh-CN", "简体中文"),
        ENGLISH("en", "English"),
        SYSTEM("", "System Default");

        public final String code;
        public final String displayName;

        Language(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public static Language fromCode(String code) {
            if (code == null || code.isEmpty()) return SYSTEM;
            for (Language lang : values()) {
                if (lang.code.equals(code)) return lang;
            }
            return SYSTEM;
        }
    }

    /**
     * Set the app language.
     * Android 13+ uses LocaleManager; older versions use AppCompatDelegate.
     */
    public static void setLanguage(Context context, Language language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            LocaleManager localeManager = context.getSystemService(LocaleManager.class);
            if (localeManager != null) {
                if (language == Language.SYSTEM) {
                    localeManager.setApplicationLocales(LocaleList.getEmptyLocaleList());
                } else {
                    String[] parts = language.code.split("-");
                    Locale locale = parts.length > 1
                            ? new Locale(parts[0], parts[1])
                            : new Locale(parts[0]);
                    localeManager.setApplicationLocales(new LocaleList(locale));
                }
            }
        } else {
            if (language == Language.SYSTEM) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList());
            } else {
                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language.code));
            }
        }

        // Persist preference
        context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
                .edit()
                .putString(PREF_LANGUAGE, language.code)
                .apply();
    }

    /**
     * Get the currently saved language preference.
     */
    public static Language getSelectedLanguage(Context context) {
        String code = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
                .getString(PREF_LANGUAGE, "");
        return Language.fromCode(code);
    }
}

