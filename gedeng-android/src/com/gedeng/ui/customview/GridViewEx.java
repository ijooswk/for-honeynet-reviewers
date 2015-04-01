/**
 * @author william
 * 
 *  自定义  GridView, 可以嵌套在  ScrollView 中
 * 
 */

package com.gedeng.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewEx extends GridView { 

    public GridViewEx(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 

    public GridViewEx(Context context) { 
        super(context); 
    } 

    public GridViewEx(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 
    
    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
}
