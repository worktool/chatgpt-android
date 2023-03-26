package org.yameida.asrassistant.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import java.util.List;


/**
 * 多类型列表基类
 * 使用：
 *  1.继承后在构造内调用addItemType方法添加类型
 *  2.convert方法中 通过 holder.getItemViewType() 判断类型
 * Created by gallon on 2017/5/19.
 */

public abstract class RvMultiAdapter<T extends MultiItemEntity> extends RvSimpleAdapter<T> {

    private SparseArray<Integer> layouts;

    public RvMultiAdapter(Context context, List data) {
        super(context, data, 0);
    }

    public void addItemType(int type, @LayoutRes int layoutResId) {
        if(this.layouts == null) {
            this.layouts = new SparseArray();
        }

        this.layouts.put(type, layoutResId);

    }

    @Override
    protected RvViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new RvViewHolder(mInflater.inflate(layouts.get(viewType), parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        MultiItemEntity data = mData.get(position);
        return data.getType();
    }

    @Override
    public void convert(RvViewHolder holder, T bean, int position) {

    }
}
