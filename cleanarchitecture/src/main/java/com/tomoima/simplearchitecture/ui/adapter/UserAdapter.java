package com.tomoima.simplearchitecture.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tomoima.simplearchitecture.R;
import com.tomoima.simplearchitecture.domain.model.User;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tomoaki on 7/26/15.
 */
public class UserAdapter extends ArrayAdapter<User> {
    LayoutInflater mInflater;
    Context mContext;
    public UserAdapter (Context context, List<User> dataList) {
        super(context, -1, dataList);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void refresh(Collection<User> dataList){
        this.clear();
        this.addAll(dataList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_user, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        bindView(convertView, position,getItem(position));
        return convertView;
    }

    public void bindView(View view, int position, User data){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Glide.with(mContext).load(data.avatar_url).into(viewHolder.mPhoto);
        viewHolder.nameTv.setText(data.login);
    }

    public static class ViewHolder{
        @InjectView(R.id.photo_riv)
        RoundedImageView mPhoto;
        @InjectView(R.id.name_tv)
        TextView nameTv;

        ViewHolder(View v){
            ButterKnife.inject(this, v);
        }
    }
}
