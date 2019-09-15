package com.tea.teatool.spanclick;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tea.teatool.R;

import java.util.ArrayList;
import java.util.List;

public class SpannableClickActivity extends AppCompatActivity {

    private List<String> titleContract = new ArrayList<>();
    private TextView spanTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_click);
        spanTv = findViewById(R.id.span_tv);
        initContract();

        spanTv.setText(getClickableSpan());
        spanTv.setMovementMethod(LinkMovementMethod.getInstance());
        spanTv.setHighlightColor(Color.TRANSPARENT);
    }

    private void initContract() {
        titleContract.add("《买卖合同》");
        titleContract.add("《赠与合同》");
        titleContract.add("《借款合同》");
        titleContract.add("《租赁合同》");
        titleContract.add("《技术合同》");
        titleContract.add("《委托合同》");
        titleContract.add("《承揽合同》");
        titleContract.add("《仓储合同》");
        titleContract.add("《融资租赁合同》");
        titleContract.add("《行纪合同》");
    }

    private SpannableString getClickableSpan() {

        String string = "我已认真阅读";
        int startlen = string.length();

        for (int i = 0; i < titleContract.size(); i++) {
            if (i == titleContract.size() - 2) {
                string += (titleContract.get(i) + "及");
            } else if (i == titleContract.size() - 1) {
                string += (titleContract.get(i) + "的全部内容，了解并接受约定。");
            } else {
                string += (titleContract.get(i) + "、");
            }
        }

        SpannableString spannableString = new SpannableString(string);
        for (int i = 0; i < titleContract.size(); i++) {
            final int finalI = i;
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Toast.makeText(SpannableClickActivity.this, titleContract.get(finalI), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.colorPrimary));
                    ds.setUnderlineText(false);
                }
            }, startlen, startlen + titleContract.get(i).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            startlen = startlen + titleContract.get(i).length() + 1;
        }

        return spannableString;
    }
}
