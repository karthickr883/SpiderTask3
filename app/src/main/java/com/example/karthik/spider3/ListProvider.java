package com.example.karthik.spider3;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by karthik on 13-06-2018.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    Context context = null;
    int appWidgetId;
    ArrayList<String> widget = new ArrayList<>();

    ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        populateListItem();
    }

    private void populateListItem() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor data = databaseHelper.getData();
        while (data.moveToNext()) {
            widget.add(data.getString(1));
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widget.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_row);
        Log.d("SUCCESS",widget.get(position));
        remoteViews.setTextViewText(R.id.textView2,widget.get(position));
        remoteViews.setTextViewText(R.id.number,String.valueOf(position+1)+".");
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}