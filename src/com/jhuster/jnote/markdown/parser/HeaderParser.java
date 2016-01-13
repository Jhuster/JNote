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

public class HeaderParser extends MDParser {
    
    public static final String HEADER = "# ";
    public static final String HEADER2 = "## ";
    public static final String HEADER3 = "### ";

    @Override
    public MDWord parseLineFmt(String content) {
        if (content.startsWith(HEADER)) {
            return new MDWord("",HEADER.length(),Markdown.MD_FMT_HEADER1); 
        }
        else if (content.startsWith(HEADER2)) {
            return new MDWord("",HEADER2.length(),Markdown.MD_FMT_HEADER2);
        }
        else if (content.startsWith(HEADER3)) {
            return new MDWord("",HEADER3.length(),Markdown.MD_FMT_HEADER3);
        }        
        return MDWord.NULL;       
    }

    @Override
    public MDWord parseWordFmt(String content) {
        return MDWord.NULL;
    }    
}
