package com.weico.core.emotion;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.weico.core.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 加载表情数据
 *
 * @author hufeng
 */
public class ExpressionDataLoader {

    /**
     * 图片表情名称
     */
    public List<String> expressionNameList1; // 表情名字

    /**
     * 符号表情名称
     */
    public List<String> lstOfTxtEmotions1;


    public ExpressionDataLoader(Context context) {
        expressionNameList1 = new ArrayList<String>();
        lstOfTxtEmotions1 = new ArrayList<String>();
        loadExpression(context);
    }

    /**
     * 加载表情
     */
    private void loadExpression(Context context) {
        Resources resources = context.getResources();

        String[] expressionName = resources.getStringArray(R.array.expression_name);
        for (int i = 0; i < expressionName.length; i++) {
            String name = expressionName[i];
            expressionNameList1.add(name.trim());
        }

        AssetManager mAsset = resources.getAssets();
        try {
            // 符号表情
            InputStream in = mAsset.open("txt_emotions.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                lstOfTxtEmotions1.add(tempString.trim());
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空表情数据
     */
    public void clearData() {
        expressionNameList1.clear();
        lstOfTxtEmotions1.clear();
    }
}
