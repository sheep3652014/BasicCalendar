package com.udn4hc.basiccalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String tag = "Main";
	private Button selectedDayMonthYearButton;
	private Button currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridAdapter adapter;
	private Calendar calendar;
	private int month, year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		//calendar = cale
	}

	public class GridAdapter extends BaseAdapter implements
			android.view.View.OnClickListener {

		private static final String tag = "GridAdapter";
		private final Context context;
		private final List<String> list;
		private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
				"Wed", "Thu", "Fri", "Sat", "Sun" };
		private final String[] months = { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
				31, 30, 31 };
		private final int month, year;
		int daysInMonth, prevMonthDays;
		private final int currentDayOfMonth;
		private Button gridcell;

		public GridAdapter(Context context, int textViewResourceId, int month,
				int year) {
			super();
			this.context = context;
			this.list = new ArrayList<String>();
			this.month = month;
			this.year = year;

			Log.d(tag, "Month: " + month + " " + "Year: " + year);
			Calendar calendar = Calendar.getInstance();
			currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			printMonth(month, year);

		}

		private void printMonth(int month, int year) {
			int trailingSpaces = 0;
			int leadSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			GregorianCalendar cal = new GregorianCalendar(year, month,
					currentDayOfMonth);
			daysInMonth = daysOfMonth[month];
			int currentMonth = month;
			if (currentMonth == 11) {
				prevMonth = 10;
				daysInPrevMonth = daysOfMonth[prevMonth];
				nextMonth = 0;
				prevYear = year;
				nextYear = year + 1;
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = year - 1;
				nextYear = year;
				daysInPrevMonth = daysOfMonth[prevMonth];
				nextMonth = 1;
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = year;
				prevYear = year;
				daysInPrevMonth = daysOfMonth[prevMonth];
			}

			trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;

			if (cal.isLeapYear(cal.get(Calendar.YEAR)) && month == 1) {
				++daysInMonth;
			}
			for (int i = 0; i < trailingSpaces; i++) {
				list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1)
						+ i)
						+ "-GREY" + "-" + months[prevMonth] + "-" + prevYear);
			}

			for (int i = 1; i <= daysInMonth; i++) {
				list.add(String.valueOf(i) + "-WHITE" + "-" + months[month]
						+ "-" + year);
			}

			for (int i = 0; i < list.size() % 7; i++) {
				Log.d(tag, "NEXT MONTH:= " + months[nextMonth]);
				list.add(String.valueOf(i + 1) + "-GREY" + "-"
						+ months[nextMonth] + "-" + nextYear);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.d(tag, "getView");
			View view = convertView;
			if (view == null) {
				Log.d(tag, "inflating xml");
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.id.gridcell, parent, false);
				Log.d(tag, "xml inflated successfully");
			}
			gridcell = (Button) view.findViewById(R.id.gridcell);
			gridcell.setOnClickListener(this);

			Log.d(tag, "Current Day: " + currentDayOfMonth);
			String[] day_color = list.get(position).split("-");
			gridcell.setText(day_color[0]);
			gridcell.setTag(day_color[0] + "-" + day_color[2] + "-"
					+ day_color[3]);

			if (day_color[1].equals("GREY")) {
				gridcell.setTextColor(Color.LTGRAY);
			}
			if (day_color[1].equals("WHITE")) {
				gridcell.setTextColor(Color.WHITE);
			}
			if (position == currentDayOfMonth) {
				gridcell.setTextColor(Color.BLUE);
			}

			return view;
		}

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			String date_month_year = (String) view.getTag();
			Toast.makeText(getApplicationContext(), date_month_year,
					Toast.LENGTH_SHORT).show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
