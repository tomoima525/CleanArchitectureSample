package com.tomoima.cleanarchitecturesample.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tomoima.cleanarchitecturesample.R;
import com.tomoima.cleanarchitecturesample.models.Repos;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tomoaki on 7/25/15.
 */
public class RepoAdapter extends ArrayAdapter<Repos> {
    LayoutInflater mInflater;
    Context mContext;
    public RepoAdapter (Context context, List<Repos> dataList) {
        super(context, -1, dataList);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_repos, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        bindView(convertView, position,getItem(position));
        return convertView;
    }

    public void bindView(View view, int position, Repos data){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.nameTv.setText(data.name);
        viewHolder.descTv.setText(data.description);
        viewHolder.urlTv.setText(data.html_url);
    }

    public static class ViewHolder{
        @InjectView(R.id.repos_description_tv)
        TextView descTv;
        @InjectView(R.id.repos_name_tv)
        TextView nameTv;
        @InjectView(R.id.repos_url)
        TextView urlTv;

        ViewHolder(View v){
            ButterKnife.inject(this,v);
        }
    }
}
