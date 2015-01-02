package net.aayush.skooterapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.aayush.skooterapp.data.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends ArrayAdapter<Comment> {
    protected Context mContext;
    protected int mLayoutResourceId;
    protected List<Comment> data = new ArrayList<Comment>();

    public CommentsAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
        }

        Comment comment = data.get(position);

        TextView commentContent = (TextView) convertView.findViewById(R.id.postText);
        commentContent.setText(comment.getContent());

        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        timestamp.setText(comment.getTimestamp());

        Button upvoteBtn = (Button) convertView.findViewById(R.id.upvote);
        Button downvoteBtn = (Button) convertView.findViewById(R.id.downvote);

        upvoteBtn.setTag(comment);
        downvoteBtn.setTag(comment);

        if (comment.isIfUserVoted()) {
            upvoteBtn.setEnabled(false);
            downvoteBtn.setEnabled(false);
            if (comment.isUserVote()) {
                upvoteBtn.setAlpha(0.8f);
                downvoteBtn.setAlpha(0.3f);
            } else {
                downvoteBtn.setAlpha(0.8f);
                upvoteBtn.setAlpha(0.3f);
            }
        } else {
            upvoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout rl = (RelativeLayout) v.getParent();
                    Button upvoteBtn = (Button) rl.findViewById(R.id.upvote);
                    Comment comment = (Comment) upvoteBtn.getTag();

                    //Call the upvote method
                    comment.upvoteComment();
                    notifyDataSetChanged();
                }
            });

            downvoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout rl = (RelativeLayout) v.getParent();
                    Button downvoteBtn = (Button) rl.findViewById(R.id.downvote);

                    Comment comment = (Comment) downvoteBtn.getTag();

                    //Call the upvote method
                    comment.downvoteComment();
                    notifyDataSetChanged();
                }
            });
        }
        //upvoteBtn.setFocusable(false);
        //downvoteBtn.setFocusable(false);

        return convertView;
    }
}