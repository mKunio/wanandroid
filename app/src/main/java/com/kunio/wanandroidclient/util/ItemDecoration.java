package com.kunio.wanandroidclient.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhc on 2017/11/8 0008.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    private boolean mTop;
    private boolean mLeft;
    private boolean mRight;
    private boolean mBottom;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mRight) {
            outRect.right = mSpace;
        }
        if (mLeft) {
            outRect.left = mSpace;
        }
        if (mTop) {
            outRect.top = mSpace;
        }
        if (mBottom) {
            outRect.bottom = mSpace;
        }
    }

    public ItemDecoration(int space, boolean left, boolean top, boolean right, boolean bottom) {
        this.mSpace = space;
        mBottom = bottom;
        mLeft = left;
        mTop = top;
        mRight = right;
    }

    public ItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }
}
