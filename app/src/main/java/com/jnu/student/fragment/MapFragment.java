package com.jnu.student.fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.jnu.student.R;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private MapView mMapView = null;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public MapFragment() {}
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = rootView.findViewById(R.id.map_view);
        TencentMap mTencentMap = mMapView.getMap();

        LatLng position = new LatLng(22.24992,113.533224);               // 设置坐标信息
        mTencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 19));    // 将初始位置移到指定位置

        Marker marker = mTencentMap.addMarker(new MarkerOptions(position).title("暨南大学"));   // 设置标记
        marker.showInfoWindow();                                                          // 设置默认显示一个infoWindow
        marker.setClickable(true);                                                        // 设置marker支持点击
        // 设置点击标记事件
        mTencentMap.setOnMarkerClickListener(marker1 -> {
            if(marker1.getId().equals(marker.getId())) {
                Toast.makeText(requireActivity(),"欢迎来到暨南大学",Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}

