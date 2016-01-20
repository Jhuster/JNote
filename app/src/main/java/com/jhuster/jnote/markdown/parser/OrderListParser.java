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

public class OrderListParser extends MDParser {
    
    private static final String KEY = "^[0-9].*";

    @Override
    public MDWord parseLineFmt(String content) {        
        if (!content.matches(KEY)) {
            return MDWord.NULL;
        }  
        return new MDWord("",0,Markdown.MD_FMT_ORDER_LIST);
    }

    @Override
    public MDWord parseWordFmt(String content) {               
        return MDWord.NULL;
    }

}
