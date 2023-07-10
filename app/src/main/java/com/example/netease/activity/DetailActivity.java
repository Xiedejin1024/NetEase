package com.example.netease.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netease.R;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.news.bean.DetailImage;
import com.example.netease.news.bean.DocDetail;
import com.example.netease.utils.Httputil;
import com.example.netease.utils.Jsonutil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final int INIT_WEB_SUCESS = 0;
    public static final String DOCID = "docid";
    private WebView mWebDetail;
    private MyHandler mMyhandler;
    private TextView mTvReplyNumber;
    private EditText mEtReply;
    private TextView mTvSendreply;
    private LinearLayout mLlShapeEdit;
    private String mDocid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMyhandler = new MyHandler(this);
        initView();
        mDocid = getIntent().getStringExtra(DOCID);
        getDetaildatas(mDocid);

    }

    private void initView() {
        mWebDetail = findViewById(R.id.webview_detail);
        mLlShapeEdit = findViewById(R.id.ll_shape_edited);
        mTvSendreply = findViewById(R.id.tv_send_reply);
        mTvReplyNumber = findViewById(R.id.tv_reply_number);
        mEtReply = findViewById(R.id.tv_edited_reply);
        mEtReply.setOnFocusChangeListener((View view, boolean isFocus) -> {
            if (isFocus) {
                mLlShapeEdit.setVisibility(View.GONE);
                mTvSendreply.setVisibility(View.VISIBLE);
            } else {
                mLlShapeEdit.setVisibility(View.VISIBLE);
                mTvSendreply.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.image_detail_reply).setOnClickListener((View view) -> {
            Intent intent = new Intent();
            intent.setClass(this, FreedBackActivity.class);
            intent.putExtra(FreedBackActivity.DOCID, mDocid);
            startActivity(intent);
        });
    }

    public void getDetaildatas(String docid) {
        if (!TextUtils.isEmpty(docid)) {
            Httputil httputil = Httputil.getInstance();
            String detailURL = Contance.getDetailURL(docid);
            httputil.doGet(detailURL, new HttpResponse<String>(String.class) {
                @Override
                public void onError() {

                }

                @Override
                public void onSuccess(String string) {
                    try {
                        JSONObject json = new JSONObject(string);
                        JSONObject docid_json = json.getJSONObject(docid);
                        DocDetail docDetail = Jsonutil.Jsonparse(docid_json.toString(), DocDetail.class);
                        List<DetailImage> img = docDetail.getImg();
                        String docDetailBody = docDetail.getBody();
                        for (int i = 0; i < img.size(); i++) {
                            String imgHTML = "<IMG src='" + docDetail.getImg().get(i).getSrc() + "'/>";
                            docDetailBody = docDetailBody.replaceFirst("<!--IMG#" + i + "-->", imgHTML);
                        }

                        String titleHTML = "<p><span style='font-size:20px;'><strong>" + docDetail.getTitle() + "</strong></span></p>";
                        titleHTML = titleHTML + "<p><span style='color:#666666;'>" + docDetail.getSource() + "&nbsp&nbsp" + docDetail.getPtime() + "</span></p>";
                        docDetailBody = titleHTML + docDetailBody;

                        docDetailBody = "<html><head><style>img{width:100%}</style>" +
                                "<script type=\'text/javascript\'>function showImge(){window.demo.show()}</script></head><body>" + docDetailBody + "</body></html>";
                        Message message = mMyhandler.obtainMessage(INIT_WEB_SUCESS);
                        message.obj = docDetailBody;
                        message.arg1 = docDetail.getReplyCount();
                        mMyhandler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    static class MyHandler extends Handler {

        //采用弱引用，避免内存泄露
        private WeakReference<DetailActivity> activity;


        private MyHandler(DetailActivity activity) {
            this.activity = new WeakReference<>(activity);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            DetailActivity detailActivity = activity.get();
            if (detailActivity == null) {
                return;
            }

            switch (msg.what) {
                case INIT_WEB_SUCESS:
                    String docDetailBody = (String) msg.obj;
                    detailActivity.loadWebViewData(docDetailBody, msg.arg1);

                    break;
            }
        }
    }

    public void loadWebViewData(String docDetailBody, int replyCount) {
        mWebDetail.loadDataWithBaseURL(null, docDetailBody, "text/html", "utf-8", null);
        mTvReplyNumber.setText(String.valueOf(replyCount));
    }

}
