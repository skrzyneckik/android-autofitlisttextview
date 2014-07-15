package net.kskrzynecki.autofitlisttextview.widget;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Krzysztof 'Szpecku' Skrzynecki
 */
public class AutofitListTextView extends TextView {

    // Attributes
    private TextPaint mPaint;
    private Set<String> labels;

    public AutofitListTextView(Context context) {
        super(context);
        init();
    }

    public AutofitListTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutofitListTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        labels = new HashSet<String>();
    }

    public void setTextSizeBasedOnUsedLabels(String[] textArray) {
        for (String text: textArray) {
            labels.add(text);
        }
    }

    public void setTextSizeBasedOnUsedLabels(int... textResources) {
        for (int textRes : textResources) {
            labels.add((String) getResources().getText(textRes));
        }
    }

    /**
     * Re size the font so the specified text fits in the text box assuming the text box is the
     * specified width.
     */
    public void refitText() {

        float textSize = 21.f;
        int targetWidth = getWidth() - getPaddingLeft() - getPaddingRight();


        if (labels != null) {
            for (String text : labels) {

                if (targetWidth > 0) {

                    mPaint.set(getPaint());
                    mPaint.setTextSize(textSize);

                    while (mPaint.measureText(text) > targetWidth) {
                        textSize--;
                        mPaint.setTextSize(textSize);
                    }
                }
            }
        }
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start,
                                 final int lengthBefore, final int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        refitText();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            refitText();
        }
    }
}


