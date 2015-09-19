package com.pyconindia.pycon;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pyconindia.pycon.http.Api.UrlType;
import com.pyconindia.pycon.http.ResponseHandler;
import com.pythonindia.pycon.R;

public class BaseActivity extends AppCompatActivity implements ResponseHandler {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
         Spannable s = Spannable.Factory.getInstance().newSpannable("");
         s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, s.length(), 0);
        switch (item.getItemId()) {
//            case R.id.credits:
//                s = Spannable.Factory.getInstance().newSpannable(this.getText(R.string.credits_title));
//                s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, s.length(), 0);
//                final TextView msg = new TextView(this);
//                final SpannableString s11 =
//                             new SpannableString(this.getText(R.string.credits));
//                s11.setSpan(new ClickableSpan() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/abhi1992"));
//                        startActivity(browserIntent);
//
//                    }
//                }, s11.toString().indexOf("Abhishek"), s11.toString().indexOf("Banerjee")+8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                s11.setSpan(new ClickableSpan() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kracekumar"));
//                        startActivity(browserIntent);
//
//                    }
//                }, s11.toString().indexOf("Kracekumar"), s11.toString().indexOf("Ramaraju")+8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                s11.setSpan(new ClickableSpan() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tanaykumarbera"));
//                        startActivity(browserIntent);
//
//                    }
//                }, s11.toString().indexOf("Tanay"), s11.toString().indexOf("Bera")+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//                msg.setTextSize(18);
//                msg.setPadding(30, 10, 30, 10);
//                msg.setText(s11);
//                msg.setMovementMethod(LinkMovementMethod.getInstance());
//
//                new AlertDialog.Builder(this)
//                 .setTitle(s)
//                 .setCancelable(true)
//                 .setIcon(R.drawable.ic_launcher)
//                 .setPositiveButton("OK", null)
//                 .setView(msg)
//                 .create().show();
//                return true;
            case R.id.help:
                s = Spannable.Factory.getInstance().newSpannable(this.getText(R.string.help_title));
                s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, s.length(), 0);

                new AlertDialog.Builder(this)
                 .setTitle(s)
                 .setCancelable(true)
                 .setIcon(R.drawable.ic_launcher)
                 .setPositiveButton("OK", null)
                 .setMessage(this.getText(R.string.help))
                 .create().show();
                return true;

            case R.id.about:
                s = Spannable.Factory.getInstance().newSpannable(this.getText(R.string.about_title));
                s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, s.length(), 0);

                new AlertDialog.Builder(this)
                 .setTitle(s)
                 .setCancelable(true)
                 .setIcon(R.drawable.ic_launcher)
                 .setPositiveButton("OK", null)
                 .setMessage(this.getText(R.string.about))
                 .create().show();
                return true;

            default:
                break;
        }
        return false;
     }

	@Override
	public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {

	}

	@Override
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType) {

	}

	@Override
	public void onFailure(int statusCode, String resonseString, Throwable e, UrlType urlType) {

	}

    @Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
        Log.d("abhishek", "response fail");
    }
}
