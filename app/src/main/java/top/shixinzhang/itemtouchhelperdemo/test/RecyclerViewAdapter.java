package top.shixinzhang.itemtouchhelperdemo.test;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import top.shixinzhang.itemtouchhelperdemo.R;
import top.shixinzhang.itemtouchhelperdemo.test.recyclerviewHelper.IItemTouchHelperAdapter;
import top.shixinzhang.itemtouchhelperdemo.test.recyclerviewHelper.IItemTouchHelperViewHolder;
import top.shixinzhang.itemtouchhelperdemo.test.recyclerviewHelper.OnStartDragListener;


public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> implements IItemTouchHelperAdapter {

    private List<ItemEntity> mList;
    private OnClickSwitchListener mOnClickSwitchListener;
    private final OnStartDragListener mDragListener;

    public RecyclerViewAdapter(List<ItemEntity> list, OnStartDragListener mDragListener) {
        mList = list;
        this.mDragListener = mDragListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ItemEntity itemEntity = mList.get(position);



        if (itemEntity.isMoreActionShowed()) {
            holder.moreActionIv.setVisibility(View.VISIBLE);
            holder.moreActionIv2.setVisibility(View.VISIBLE);
            holder.moreActionIv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemEntity.setMoreActionShowed(false);
                    notifyDataSetChanged();
                }
            });

            holder.text.setVisibility(View.GONE);
            holder.switchCompat.setVisibility(View.GONE);
            holder.menu.setVisibility(View.GONE);
        } else {
            holder.moreActionIv.setVisibility(View.GONE);
            holder.moreActionIv2.setVisibility(View.GONE);

            holder.text.setVisibility(View.VISIBLE);
            holder.switchCompat.setVisibility(View.VISIBLE);
            holder.menu.setVisibility(View.VISIBLE);

            holder.text.setText(itemEntity.getText());
            holder.switchCompat.setChecked(itemEntity.isChecked());
            if (mOnClickSwitchListener != null) {
                holder.switchCompat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean b = itemEntity.isChecked();
                        itemEntity.setChecked(!b);
                        mOnClickSwitchListener.onClick(position, !b);
                    }
                });
            }
            holder.menu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction()
                            == MotionEvent.ACTION_DOWN) {
                        mDragListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMultipleOperationShowed(int position) {
        ItemEntity itemEntity = mList.get(position);
        if (itemEntity != null) {
            itemEntity.setMoreActionShowed(true);
        }
        notifyItemChanged(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements IItemTouchHelperViewHolder {
        private TextView text;
        private ImageView menu;
        private SwitchCompat switchCompat;

        private ImageView moreActionIv, moreActionIv2;

        ItemViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_list_text_textView);
            menu = itemView.findViewById(R.id.item_list_menu_imageView);
            switchCompat = itemView.findViewById(R.id.item_list_switchCompat);

            moreActionIv = itemView.findViewById(R.id.item_list_more_action_1);
            moreActionIv2 = itemView.findViewById(R.id.item_list_more_action_2);
        }

        @Override
        public void onItemSelected() {
            itemView.setTranslationZ(10);
        }

        @Override
        public void onItemClear() {
            itemView.setTranslationZ(0);
        }
    }

    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        mOnClickSwitchListener = onClickSwitchListener;
    }

    public interface OnClickSwitchListener {
        void onClick(int position, boolean isChecked);
    }
}
