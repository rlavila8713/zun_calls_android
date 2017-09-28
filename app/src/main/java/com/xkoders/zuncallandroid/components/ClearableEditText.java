package com.xkoders.zuncallandroid.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.interfaces.TextWatcherListener;

/**
 * To change clear icon, set
 * <p/>
 * <pre>
 * android:drawableRight="@drawable/custom_icon"
 * </pre>
 */
public class ClearableEditText extends EditText implements OnTouchListener, OnFocusChangeListener, TextWatcherListener {

    private Drawable xD, xEr, xOk;
    private Listener listener;
    private int validData, lastValidData;
    private OnTouchListener l;
    private OnFocusChangeListener f;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setValidData(int validData) {
        this.validData = validData;
    }

    @Override
    protected void onSelectionChanged(int start, int end) {
        CharSequence text = getText();
        if (text != null) {
            if (start != text.length() || end != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }

        super.onSelectionChanged(start, end);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.l = l;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener f) {
        this.f = f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - xD.getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    validData = 0;
                    setText("");
                    if (listener != null) {
                        listener.didClearText();
                    }
                }
                return true;
            }
        }
        if (l != null) {
            return l.onTouch(v, event);
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(!TextUtils.isEmpty(getText()));
        } else {
            if (validData == 0) {
                setClearIconVisible(false);
            }
        }
        if (f != null) {
            f.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        if (isFocused()) {
            setClearIconVisible(!TextUtils.isEmpty(text));
        }
    }

    private void init() {
        xD = getCompoundDrawables()[2];
        if (xD == null) {
            //xD = getResources().getDrawable(android.R.drawable.presence_offline);
            xD = getResources().getDrawable(R.drawable.close_image2);
        }
        xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
        xEr = getResources().getDrawable(R.drawable.edit_text_error);
        xOk = getResources().getDrawable(R.drawable.edit_text_ok);
        xEr.setBounds(0, 0, xEr.getIntrinsicWidth(), xEr.getIntrinsicHeight());
        xOk.setBounds(0, 0, xOk.getIntrinsicWidth(), xOk.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
//        addTextChangedListener(new TextWatcherAdapter(this, this));
//        setOnKeyListener(new KeyListener());
    }

    protected void setClearIconVisible(boolean visible) {
        boolean wasVisible = (getCompoundDrawables()[2] != null);
        if (visible != wasVisible || validData != lastValidData) {
            Drawable x;
            if (visible) {
                switch (validData) {
                    case 1:
                        x = xEr;
                        break;
                    case 2:
                        x = xOk;
                        break;
                    default:
                        x = xD;
                        break;
                }
            } else {
                x = null;
            }
            lastValidData = validData;
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
        }
    }

    public interface Listener {
        void didClearText();
    }
}