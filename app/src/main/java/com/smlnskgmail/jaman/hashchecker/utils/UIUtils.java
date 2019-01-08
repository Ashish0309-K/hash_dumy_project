package com.smlnskgmail.jaman.hashchecker.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.smlnskgmail.jaman.hashchecker.R;

public class UIUtils {

    private static final int COMMON_SNACKBAR_MAGRIN = 12;

    public static void showSnackbar(@NonNull Context context, @NonNull View parent, @NonNull String message, int length) {
        showSnackbar(context, parent, message, null, null, length);
        if (Preferences.getVibrateAccess(context)) {
            AppUtils.vibrate(context);
        }
    }

    public static void showSnackbar(@NonNull Context context, @NonNull View parent, @NonNull String message,
                                    @Nullable String actionText, @Nullable View.OnClickListener action, int length) {
        Snackbar snackbar = Snackbar.make(parent, message, length);
        if (action != null) {
            snackbar.setAction(actionText, action);
        } else {
            final Snackbar closableSnackbar = snackbar;
            snackbar.setAction(context.getResources().getString(R.string.common_ok), v -> closableSnackbar.dismiss());
        }
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbar.getView().getLayoutParams();
        params.setMargins(COMMON_SNACKBAR_MAGRIN, COMMON_SNACKBAR_MAGRIN, COMMON_SNACKBAR_MAGRIN, COMMON_SNACKBAR_MAGRIN);
        snackbar.getView().setBackground(ContextCompat.getDrawable(context, R.drawable.bg_snackbar));

        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTypeface(ResourcesCompat.getFont(context, R.font.google_sans_regular));
        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action))
                .setTypeface(ResourcesCompat.getFont(context, R.font.google_sans_regular));
        snackbar.show();
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.content, fragment, Constants.TAGS.CURRENT_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void removeFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();
        fragmentManager.popBackStackImmediate();
    }

    public static void applyAdaptiveFont(@NonNull Context context, @NonNull TextView textView) {
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.google_sans_regular));
    }

    public static void applyAdaptiveFontWithBoldStyle(@NonNull Context context, @NonNull TextView textView) {
        applyAdaptiveFont(context, textView);
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.google_sans_regular), Typeface.BOLD);
    }

    public static void hideKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("ResourceType")
    @NonNull
    public static ProgressDialog getProgressDialog(@NonNull Context context, @IdRes int textMessageResId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(textMessageResId));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

}