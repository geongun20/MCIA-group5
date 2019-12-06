package com.example.smokare;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class MyXAxisValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public MyXAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }

    /** this is only needed if numbers are returned, else return 0 */
//    @Override
//    public int getDecimalDigits() { return 0; }


}


//public class MyXAxisValueFormatter implements IAxisValueFormatter {
//
//    private static int MINUTES_INTERVAL = 5;
//    private String[] mValues;
//    private int mInterval;
//    private SensorInterval.Interval mSlot;
//
//    public MyXAxisValueFormatter(List<BinSensorData> range, int interval, SensorInterval.Interval slot) {
//        mValues = new String[range.size()];
//        mInterval = interval;
//        mSlot = slot;
//
//        Calendar calendar = Calendar.getInstance();
//        for (int i = 0; i < range.size(); i++) {
//            calendar.setTimeInMillis(range.get(i).getTime());
//
//            int unroundedMinutes = calendar.get(Calendar.MINUTE);
//            int mod = unroundedMinutes % MINUTES_INTERVAL;
//            calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (MINUTES_INTERVAL - mod));
//
//
//            String s = "";
//
//            if (slot.equals(SensorInterval.Interval.HOUR) || slot.equals(SensorInterval.Interval.DAY))
//                s = Util.getTimeFromTimestamp(calendar.getTimeInMillis());
//            else if (slot.equals(SensorInterval.Interval.WEEK))
//                s = Util.getDayFromTimestamp(calendar.getTimeInMillis());
//            else if (slot.equals(SensorInterval.Interval.MONTH))
//                s = Util.getMonthFromTimestamp(calendar.getTimeInMillis());
//            else if (slot.equals(SensorInterval.Interval.YEAR))
//                s = Util.getYearFromTimestamp(calendar.getTimeInMillis());
//
//
//            Util.setLog("Time : " + s);
//            mValues[i] = s;
//        }
//    }
//
//    @Override
//    public String getFormattedValue(float value, AxisBase axis) {
//        Util.setLog("Value : " + value);
//        if (value % mInterval == 0 && value >= 0) {
//            return mValues[(int) value % mValues.length];
//        } else
//            return "";
//
//    }
//
//    @Override
//    public int getDecimalDigits() {
//        return 0;
//    }
//}