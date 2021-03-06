package de.fau.cs.mad.fablab.android.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import de.fau.cs.mad.fablab.android.R;

public class UiUtils {

    public static void showSpinner(View spinnerContainerView, ImageView spinnerImageView) {
        spinnerContainerView.setVisibility(View.VISIBLE);

        spinnerImageView.setBackgroundResource(R.drawable.spinner);
        AnimationDrawable animationDrawable = (AnimationDrawable) spinnerImageView.getBackground();
        animationDrawable.start();
    }


    public static void hideSpinner(View spinnerContainerView, ImageView spinnerImageView) {
        AnimationDrawable animationDrawable = (AnimationDrawable) spinnerImageView.getBackground();
        animationDrawable.stop();

        spinnerContainerView.setVisibility(View.GONE);
    }

    public static void hideKeyboard(Activity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    0);
        }
    }

    public String processNewsText(String text)
    {
        //replace relative paths
        String res = text;
        res = res.replace("href=\"/", "href=\"https://fablab.fau.de/");

        //remove image at beginning
        if(res.indexOf("<p>")==0 || res.indexOf("<p>")==1 || res.indexOf("<a")==0)
        {
            res = res.substring(res.indexOf("</a>")+4);
        }
        System.out.println(res);

        return res;
    }
}
