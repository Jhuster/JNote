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
package com.jhuster.jnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhuster.jnote.db.NoteDB;
import com.jhuster.jnote.db.NoteDB.Note;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

    private Context mContext;

    protected class ViewHolder {
        TextView mNoteDate;
        TextView mNoteTitle;
    }

    public NoteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return NoteDB.getInstance().size();
    }

    @Override
    public Object getItem(int position) {
        return NoteDB.getInstance().get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Note) getItem(position)).key;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (LinearLayout) inflater.inflate(R.layout.layout_note_item, null);
            ViewHolder holder = new ViewHolder();
            holder.mNoteDate = (TextView) convertView.findViewById(R.id.NoteDateText);
            holder.mNoteTitle = (TextView) convertView.findViewById(R.id.NoteTitleText);
            convertView.setTag(holder);
        }

        Note note = (Note) getItem(position);
        if (note != null) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mNoteDate.setText(getDateStr(note.date));
            holder.mNoteTitle.setText(note.title);
        }

        return convertView;
    }

    public static String getDateStr(long milliseconds) {
        return new SimpleDateFormat("yyyy年MM月dd日 EEEE HH点mm分", Locale.CHINA).format(milliseconds);
    }
}
