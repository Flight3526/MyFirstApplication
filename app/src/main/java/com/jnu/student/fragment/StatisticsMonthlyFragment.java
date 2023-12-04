package com.jnu.student.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.jnu.student.R;
import com.jnu.student.data.EventBank;
import com.jnu.student.data.EventItem;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticsMonthlyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;

    int[][] data;          // 0:收入 1:支出 2:结余
    float[] average;
    LineChart[] lineCharts;
    int dayOfMonth;
    int monthOfYear;
    int[] monthDays;
    public StatisticsMonthlyFragment() {}

    public static StatisticsMonthlyFragment newInstance(String param1, String param2) {
        StatisticsMonthlyFragment fragment = new StatisticsMonthlyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            monthDays = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        } else {
            monthDays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        }
        updateValue();                 // 更新收入支出结余值
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics_monthly, container, false);

        lineCharts = new LineChart[3];
        lineCharts[0] = rootView.findViewById(R.id.chart_monthly_income);
        lineCharts[1] = rootView.findViewById(R.id.chart_monthly_pay);
        lineCharts[2] = rootView.findViewById(R.id.chart_monthly_surplus);
        updateChart();                 // 更新统计图

        return rootView;
    }

    private void updateValue(){
        List<EventItem> eventList = null;
        Calendar calendar = Calendar.getInstance();
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;     // 令第一天为0
        monthOfYear = calendar.get(Calendar.MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String formattedCurrentTime = sdf.format(calendar.getTime());
        data = new int[3][dayOfMonth + 1];
        average = new float[3];

        try {eventList = EventBank.loadEventItems(getActivity());}
        catch (FileNotFoundException e) {e.printStackTrace();}
        if(null == eventList || eventList.isEmpty()) return;

        for(int i = 0; i < eventList.size(); ++i){
            EventItem e = eventList.get(i);
            if(e.getTime().substring(0, 7).equals(formattedCurrentTime)){     // 比较年和月
                int eDayOfMonth = Integer.parseInt(e.getTime().substring(8, 10)) - 1;
                if(eDayOfMonth <= dayOfMonth)
                    if(e.getEventValue() >= 0) data[0][eDayOfMonth] += e.getEventValue();
                    else data[1][eDayOfMonth] -= e.getEventValue();
            }
            else break;
        }

        // 计算结余、均值
        int start = -1;                                         // start为计算均值的起始时间
        for(int value = 0; value < dayOfMonth + 1; ++value) {
            data[2][value] = data[0][value] - data[1][value];
            if(-1 == start && (0 != data[0][value] || 0 != data[1][value]))
                start = value;
        }
        float n = 0 == dayOfMonth + 1 - start ? 1 : dayOfMonth + 1 - start;     // n为计算均值时的除数
        for(int kind = 0; kind < 3; ++kind) {
            int total = 0;
            for (int value : data[kind]) total += value;
            average[kind] = Math.round(total / n * 100) / 100.0f;   // 精确到小数点后第二位
        }
    }

    private void updateChart(){
        ArrayList<Entry>[] entries = new ArrayList[3];                    // 创建数据集合
        for(int kind = 0; kind < 3; ++kind) entries[kind] = new ArrayList<>();
        for(int kind = 0; kind < 3; ++kind)
            for(int value = 0; value < dayOfMonth + 1; ++value)
                entries[kind].add(new Entry(value, data[kind][value]));

        for(int kind = 0; kind < 3; ++kind){
            LineDataSet dataSet = new LineDataSet(entries[kind], "");  // 创建数据集
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSet.setColor(Color.WHITE);                             // 设置折线颜色
            dataSet.setCircleColor(Color.WHITE);                       // 设置数据点的颜色
            dataSet.setCircleRadius(4f);                               // 设置数据点的半径
            dataSet.setHighLightColor(Color.RED);                      // 设置点击某个点时，横竖两条线的颜色
            dataSet.setDrawValues(true);                               // 在点上显示数值 默认true
            dataSet.setValueTextSize(14f);                             // 数值字体大小
            dataSet.setValueTextColor(Color.WHITE);                    // 数值字体颜色
            dataSet.setDrawFilled(true);                               // 设置开启折线填充
            Drawable fillDrawable = new ColorDrawable(Color.WHITE);    // 设置折线填充颜色
            fillDrawable.setAlpha(40);                                 // 设置填充的透明度
            dataSet.setFillDrawable(fillDrawable);                     // 设置折线填充
            LineData lineData = new LineData(dataSet);                 // 创建LineData对象并设置数据集

            Description description = new Description();               // 设置图表描述
            if(0 == kind) description.setText("日均收入: " + average[kind]);
            else if(1 == kind) description.setText("日均支出: " + average[kind]);
            else description.setText("日均结余: " + average[kind]);
            description.setPosition(100f, 50f);
            description.setTextSize(15f);
            description.setTextColor(Color.WHITE);
            description.setTextAlign(Paint.Align.LEFT);                //设置字体对齐方式为左对齐，默认右对齐

            LimitLine limitLine = new LimitLine(average[kind], "");             // 设置限制线
            limitLine.setLineWidth(2f);
            limitLine.setLineColor(Color.RED);
            limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            limitLine.setTextSize(12f);
            limitLine.setTextColor(Color.WHITE);
            limitLine.enableDashedLine(10f, 10f, 0f);     // 绘制虚线

            lineCharts[kind].setData(lineData);                                       // 将 LineData 设置给 LineChart
            lineCharts[kind].setDescription(description);
            lineCharts[kind].getAxisLeft().removeAllLimitLines();                     // 删除旧限制线
            lineCharts[kind].getAxisLeft().addLimitLine(limitLine);                   // 添加限制线
            lineCharts[kind].setNoDataText("暂无数据");                                 // 没有数据时显示
            lineCharts[kind].setDragEnabled(true);                                    // 启用拖动功能
            lineCharts[kind].getAxisRight().setEnabled(false);                        // 隐藏右轴
            lineCharts[kind].getLegend().setEnabled(false);                           // 隐藏图例
            lineCharts[kind].setVisibleXRangeMaximum(8.2f);                           // 设置可见的X轴范围的最大值
            lineCharts[kind].setVisibleXRangeMinimum(0f);                             // 设置可见的X轴范围的最小值
            lineCharts[kind].moveViewToX(dayOfMonth - 8.2f);                   // 将最初视图移到最右端
            GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,                              // 渐进色的方向
                new int[]{Color.rgb(28,120,243), Color.rgb(28,120,243), Color.rgb(25,206,252)}
            );
            gd.setCornerRadius(22f);                                                  // 设置圆角半径
            lineCharts[kind].setBackground(gd);

            XAxis xAxis = lineCharts[kind].getXAxis();
            xAxis.setDrawAxisLine(true);                              // 绘制X轴，默认为true
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);            // 设置轴的位置
            xAxis.setDrawGridLines(false);                            // 不绘制网格线
            xAxis.setDrawLabels(true);                                // 绘制标签
            xAxis.setTextSize(11f);                                   // 设置标签文字大小
            xAxis.setTextColor(Color.WHITE);                          // 设置标签文字颜色
            xAxis.setLabelCount(10);                                  // 设置显示的标签数
            xAxis.setGranularity(1);                                  // 设置轴数值最小间隔
            xAxis.setAxisMinimum(0f);                                 // 设置轴数值最小值
            xAxis.setAxisMaximum(monthDays[monthOfYear] - 1 + 0.2f);  // +0.2f使最后一个标签可以显示
            xAxis.setValueFormatter(new ValueFormatter() {            // 转换轴标签
                public String getFormattedValue(float value) {
                return (monthOfYear + 1) + "/" + (int)(value + 1);
                }
            });
            YAxis yAxis = lineCharts[kind].getAxisLeft();
            yAxis.setTextColor(Color.WHITE);
            float yMax = lineCharts[kind].getData().getYMax();
            if(0 != yMax){
                if(2 != kind) yAxis.setAxisMaximum(yMax * 1.3f);                // 增加高度，防止与文字重叠
                else{
                    float yMin = lineCharts[kind].getData().getYMin();
                    yAxis.setAxisMaximum(Math.max(-yMin * 0.8f, yMax * 1.3f));
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        updateValue();
        updateChart();
    }
}