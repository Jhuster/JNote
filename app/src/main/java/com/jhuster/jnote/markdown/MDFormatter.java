/*
 *  Copyright (C) 2015, Jhuster, All Rights Reserved
 *
 *  Author:  Jhuster(lujun.hust@gmail.com)
 *  
 *  https://github.com/Jhuster/JNote
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 */
package com.jhuster.jnote.markdown;

import java.util.List;

import com.jhuster.jnote.markdown.Markdown.MDLine;
import com.jhuster.jnote.markdown.Markdown.MDWord;

import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

public class MDFormatter {

    private final SpannableStringBuilder mBuilder = new SpannableStringBuilder();

    public MDFormatter(List<MDLine> lines) {
        for (MDLine line : lines) {
            format(line);
        }
        mBuilder.setSpan(new TypefaceSpan("monospace"), 0, mBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public SpannableStringBuilder getFormattedContent() {
        return mBuilder;
    }

    protected void format(MDLine line) {
        int start = mBuilder.length();
        for (MDWord word : line.mMDWords) {
            int index = mBuilder.length();
            mBuilder.append(word.mRawContent);
            mBuilder.setSpan(getSpan(word.mFormat), index, mBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mBuilder.append("\n");
        if (line.mFormat == Markdown.MD_FMT_ORDER_LIST ||
                line.mFormat == Markdown.MD_FMT_UNORDER_LIST ||
                line.mFormat == Markdown.MD_FMT_QUOTE) {
            mBuilder.setSpan(new LeadingMarginSpan.Standard(40), start, mBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (line.mFormat != Markdown.MD_FMT_TEXT) {
            mBuilder.setSpan(getSpan(line.mFormat), start, mBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    protected static Object getSpan(int format) {
        switch (format) {
            case Markdown.MD_FMT_TEXT:
                return new RelativeSizeSpan(1.1f);
            case Markdown.MD_FMT_HEADER1:
                return new RelativeSizeSpan(1.5f);
            case Markdown.MD_FMT_HEADER2:
                return new RelativeSizeSpan(1.4f);
            case Markdown.MD_FMT_HEADER3:
                return new RelativeSizeSpan(1.3f);
            case Markdown.MD_FMT_QUOTE:
                return new QuoteSpan(Color.GRAY);
            case Markdown.MD_FMT_ITALIC:
                return new StyleSpan(android.graphics.Typeface.ITALIC);
            case Markdown.MD_FMT_BOLD:
                return new StyleSpan(android.graphics.Typeface.BOLD);
            case Markdown.MD_FMT_CENTER:
                return new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
            case Markdown.MD_FMT_UNORDER_LIST:
                return new BulletSpan(10, Color.BLACK);
            case Markdown.MD_FMT_ORDER_LIST:
                return new BulletSpan(10, Color.TRANSPARENT);
            default:
                break;
        }
        return null;
    }
}
