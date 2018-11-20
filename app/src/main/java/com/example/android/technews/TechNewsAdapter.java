package com.example.android.technews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TechNewsAdapter extends ArrayAdapter<TechNews> {
    public TechNewsAdapter(Context context, List<TechNews> techNews) {
        super(context, 0, techNews);
    }

    private static final String TITLE_SEPARATOR = Pattern.quote("|");

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        TechNews techArticle = getItem(position);


        String onlyDate;
        TextView articlePostDate = listItemView.findViewById(R.id.posted_date);

        String currentTime = techArticle.getPostedDate();
        //Extract only the date and discard the hour
        String timePart[] = currentTime.split("T");
        onlyDate = timePart[0];
        //Format Date to change the order to dd-MM-yyyy
        String strDate = "18-11-2018";
        SimpleDateFormat dateSource = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateSource.parse(onlyDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy");
            strDate = sdfDestination.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        articlePostDate.setText(strDate);

        TextView articleTitle = listItemView.findViewById(R.id.article_title);
        articleTitle.setText(techArticle.getTitle());


        TextView authorTextView = listItemView.findViewById(R.id.author);

        String author = techArticle.getAuthor();

        if (author != null) {

            Log.e(TechNewsAdapter.class.getName(), "WRITER NAME IS" + author);
            //TextView onlyTitleTextView = (TextView) listItemView.findViewById (R.id.title);
            authorTextView.setText("by " + author);
        } else {

            authorTextView.setVisibility(View.GONE);
        }


        TextView articleSectionName = listItemView.findViewById(R.id.section_name);
        articleSectionName.setText(techArticle.getSectionName());

        TextView typeOfNews = listItemView.findViewById(R.id.type_of_article);
        typeOfNews.setText(techArticle.getTypeOfArticle() + " published on ");


        return listItemView;
    }


}
