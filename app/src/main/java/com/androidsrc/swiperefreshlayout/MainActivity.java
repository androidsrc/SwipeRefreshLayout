package com.androidsrc.swiperefreshlayout;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnRefreshListener {

	private SwipeRefreshLayout swipeView;

	private ListView listView;
	private ArrayAdapter<String> adapter;

	private String[] LIST_ITEM_TEXT_CITIES = { "Los Angeles", "Chicago",
			"Indianapolis", "San Francisco", "Oklahoma City", "Washington" };

	private String[] LIST_ITEM_TEXT_MORE_CITIES = { "Phoenix", "San Antonio",
			"San Jose", "Nashville", "Las Vegas", "Virginia Beach" };

	private List<String> cityList;

	// variable to toggle city values on refresh
	boolean refreshToggle = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
		swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
				Color.RED, Color.CYAN);
		swipeView.setDistanceToTriggerSync(20);// in dips
		swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used

		cityList = Arrays.asList(LIST_ITEM_TEXT_CITIES);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.list_item, cityList);
		listView.setAdapter(adapter);
		listView.requestLayout();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (refreshToggle) {
				refreshToggle = false;
				cityList = Arrays.asList(LIST_ITEM_TEXT_MORE_CITIES);
				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.list_item, cityList);
			} else {
				refreshToggle = true;
				cityList = Arrays.asList(LIST_ITEM_TEXT_CITIES);
				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.list_item, cityList);
			}
			listView.setAdapter(adapter);

			swipeView.postDelayed(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getApplicationContext(),
							"city list refreshed", Toast.LENGTH_SHORT).show();
					swipeView.setRefreshing(false);
				}
			}, 1000);
		};
	};

	@Override
	public void onRefresh() {

		swipeView.postDelayed(new Runnable() {

			@Override
			public void run() {
				swipeView.setRefreshing(true);
				handler.sendEmptyMessage(0);
			}
		}, 1000);
	}

}
