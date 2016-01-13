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
package com.jhuster.jnote.markdown.parser;

import com.jhuster.jnote.markdown.Markdown;
import com.jhuster.jnote.markdown.Markdown.MDParser;
import com.jhuster.jnote.markdown.Markdown.MDWord;

public class BoldParser extends MDParser {
    
    private static final String KEY = "**";

    @Override
    public MDWord parseLineFmt(String content) {
        return MDWord.NULL;
    }

    @Override
    public MDWord parseWordFmt(String content) {
        if (!content.startsWith(KEY)) {
            return MDWord.NULL; 
        }
        int position = content.indexOf(KEY,2);
        if (position==-1) {
            return MDWord.NULL;
        }
        return new MDWord(content.substring(2,position),position+2,Markdown.MD_FMT_BOLD);
    }

}
