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

public class CenterParser extends MDParser {

    @Override
    public MDWord parseLineFmt(String content) {
        return MDWord.NULL;
    }

    @Override
    public MDWord parseWordFmt(String content) {
        if (content.charAt(0) == '{' && content.charAt(content.length() - 1) == '}') {
            int length = content.length();
            return new MDWord(content.substring(1, length - 1), length, Markdown.MD_FMT_CENTER);
        }
        return MDWord.NULL;
    }
}
