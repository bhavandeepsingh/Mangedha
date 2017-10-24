package com.mangedha.knit.layouts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bhavan on 10/24/17.
 */
public class TextInputLayout extends android.support.design.widget.TextInputLayout{


    public TextInputLayout(Context context) {
        super(context);
    }

    public TextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setErrorEnabled(boolean enabled) {
        super.setErrorEnabled(enabled);
        if (enabled) {
            return;
        }
        if (getChildCount() > 1) {
            View view = getChildAt(1);
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);
        if(error == null){
            setErrorEnabled(false);
        }
    }
}
