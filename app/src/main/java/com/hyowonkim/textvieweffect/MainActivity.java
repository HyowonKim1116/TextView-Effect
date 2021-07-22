package com.hyowonkim.textvieweffect;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kimkevin.hangulparser.HangulParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        setTyping(textView1, "한글 타이핑 효과");
        setCursor(textView2, "커서 깜빡임 효과");
    }

    // 한글 타이핑 효과 함수
    public void setTyping(TextView textView, String string) {
        Handler handler = new Handler();
        List<String> strList = new ArrayList<>();

        for (int i=0; i<string.length(); i++) {
            String str = string.substring(0,i);
            String cha = Character.toString(string.charAt(i));
            try {
                List<String> chaList = HangulParser.disassemble(cha);
                strList.add(str + chaList.get(0));
                try {
                    List<String> list = new ArrayList<>();
                    list.add(chaList.get(0));
                    list.add(chaList.get(1));
                    strList.add(str + HangulParser.assemble(list));
                    list.add(chaList.get(2));
                    strList.add(str + HangulParser.assemble(list));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                strList.add(cha + str);
            }
        }

        for (int j=0; j<strList.size(); j++) {
            String s = strList.get(j);
            handler.postDelayed(() -> textView.setText(s), 100*(j+1));
        }
    }

    // 커서 깜빡임 효과 함수
    public void setCursor(TextView textView, String string) {
        Handler handler = new Handler();

        textView.setText(string.concat(" "));
        for (int i=1; i<1000; i++) {
            handler.postDelayed(() -> textView.setText(string.concat("|")), 500*(2*i-1));
            handler.postDelayed(() -> textView.setText(string.concat(" ")), 500*(2*i));
        }
    }
}