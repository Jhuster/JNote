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

import java.util.Calendar;

import com.jhuster.jnote.db.NoteDB;
import com.jhuster.jnote.db.NoteDB.Note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    public static final String CONFIG_FIRST_START = "isFirstStart";

    private static final int REQUEST_CODE_ADD = 0;
    private static final int REQUEST_CODE_EDIT = 1;

    private ListView mNoteListView;
    private NoteAdapter mNoteAdapter;
    private int mSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        NoteDB.getInstance().close();
        super.onDestroy();
    }

    @Override
    protected void initVariables() {
        NoteDB.getInstance().open(this);
        onCheckFirstStart();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mNoteListView = (ListView) findViewById(R.id.NoteListView);
        mNoteAdapter = new NoteAdapter(this);
        mNoteListView.setAdapter(mNoteAdapter);
        registerForContextMenu(mNoteListView);
        OnItemLongClickListener longListener = new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition = position;
                mNoteListView.showContextMenu();
                return true;

            }
        };
        mNoteListView.setOnItemLongClickListener(longListener);
        mNoteListView.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.data_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.DataDelete:
                if (mSelectedPosition != -1) {
                    NoteDB.getInstance().delete(mSelectedPosition);
                    mNoteAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.DataClear:
                NoteDB.getInstance().clear();
                mNoteAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("NoteId", id);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDIT) {
            mNoteAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCheckFirstStart() {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!mSharedPreferences.getBoolean(CONFIG_FIRST_START, true)) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("# Markdown功能介绍\n\n");
        builder.append("本App支持一些简单的Markdown语法,您可以手动输入,也可以通过快捷工具栏来添加Markdown符号\n\n");
        builder.append("## **用法与规则**\n\n");
        builder.append("### **标题**\n");
        builder.append("使用\"#\"加空格在段首来创建标题\n\n");
        builder.append("例如:\n");
        builder.append("# 一级标题\n");
        builder.append("## 二级标题\n");
        builder.append("### 三级标题\n\n");
        builder.append("### **加粗功能**\n");
        builder.append("使用一组\"**\"来加粗一段文字\n\n");
        builder.append("例如:\n");
        builder.append("这是**加粗的文字**\n\n");
        builder.append("### **居中**\n");
        builder.append("使用一对大括号\"{}\"来居中一段文字(注:这是JNote特别添加的特性,非Markdown语法)\n\n");
        builder.append("例如:\n");
        builder.append("### {这是一个居中的标题}\n\n");
        builder.append("### **引用**\n");
        builder.append("使用\">\"在段首来创建引用\n\n");
        builder.append("例如:\n");
        builder.append("> 这是一段引用\n");
        builder.append("> 这是一段引用\n\n");
        builder.append("### **无序列表**\n");
        builder.append("使用\"-\"加空格在段首来创建无序列表\n\n");
        builder.append("例如:\n");
        builder.append("> 这是一个无序列表\n");
        builder.append("> 这是一个无序列表\n");
        builder.append("> 这是一个无序列表\n\n");
        builder.append("### **有序列表**\n");
        builder.append("使用数字圆点加空格在段首来创建有序列表\n\n");
        builder.append("例如:\n");
        builder.append("1. 这是一个有序列表\n");
        builder.append("2. 这是一个有序列表\n");
        builder.append("3. 这是一个有序列表\n\n");
        Note note = new Note();
        note.title = "Markdown功能介绍";
        note.content = builder.toString();
        note.date = Calendar.getInstance().getTimeInMillis();
        NoteDB.getInstance().insert(note);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(CONFIG_FIRST_START, false);
        edit.commit();
    }
}
