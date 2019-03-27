package com.myapplication.tencentmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huajiawei.myapplication.R;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.Cluster;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterItem;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterManager;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.view.DefaultClusterRenderer;
import com.tencent.tencentmap.mapsdk.vector.utils.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity {

    private TencentMap mTencentMap;
    private MapView mMapView;

    private ClusterManager<PetalItem> mClusterManager;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_clustering);
        initView();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onResume();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //调用这个方法, 用于清除聚合数据, 避免退出后, 继续计算聚合导致空指针。
        if (mClusterManager != null) {
            mClusterManager.cancel();
        }
        mMapView.onDestroy();
    }

    protected void initView() {
        mMapView = (MapView)findViewById(R.id.map);
        mTencentMap = mMapView.getMap();
        mClusterManager = new ClusterManager<PetalItem>(this, mTencentMap);
        mClusterManager.setRenderer(new CustomIconClusterRenderer(this, mTencentMap, mClusterManager));
        mTencentMap.setOnCameraChangeListener(mClusterManager);
        addItem();
        mClusterManager.cluster();

        //设置点击回调
        mTencentMap.setOnMarkerClickListener(mClusterManager);
        mTencentMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<PetalItem>() {
            @Override
            public boolean onClusterClick(Cluster<PetalItem> cluster) {
                CustomIconClusterRenderer renderer = (CustomIconClusterRenderer) mClusterManager.getRenderer();
                Toast.makeText(MapActivity.this, "cluster clicked, cluster size:" + cluster.getSize()
                        + " marker is " + (renderer.getMarker(cluster) == null ? "null" : "not null"), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mClusterManager.setOnClusterInfoWindowClickListener(
                new ClusterManager.OnClusterInfoWindowClickListener<PetalItem>() {
                    @Override
                    public void onClusterInfoWindowClick(Cluster<PetalItem> cluster) {
                        Toast.makeText(MapActivity.this,
                                "cluster infowindow clicked, cluster size:" +
                                        cluster.getSize(), Toast.LENGTH_SHORT).show();
                    }
                });

        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<PetalItem>() {
                    @Override
                    public boolean onClusterItemClick(PetalItem petalItem) {
                        Toast.makeText(MapActivity.this,
                                "single marker clicked, position:" +
                                        petalItem.getPosition(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        mClusterManager.setOnClusterItemInfoWindowClickListener(
                new ClusterManager.OnClusterItemInfoWindowClickListener<PetalItem>() {
                    @Override
                    public void onClusterItemInfoWindowClick(PetalItem petalItem) {
                        Toast.makeText(MapActivity.this,
                                "single marker infowindow clicked, position:" +
                                        petalItem.getPosition(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void addItem() {
        List<LatLng> latLngs = new ArrayList<>();

        mClusterManager.addItem(new PetalItem(31.215864181518555,121.42137145996094, R.mipmap.petal_blue));
        latLngs.add(new LatLng(31.215864181518555,121.42137145996094));

        mClusterManager.addItem(new PetalItem(31.213470458984375,121.42405700683594, R.mipmap.petal_red));
        latLngs.add(new LatLng(31.213470458984375,121.42405700683594));

        mClusterManager.addItem(new PetalItem(31.211700439453125,121.4234619140625, R.mipmap.petal_green));
        latLngs.add(new LatLng(31.211700439453125,121.4234619140625));

        mClusterManager.addItem(new PetalItem(31.215290069580078,121.42034149169922, R.mipmap.petal_yellow));
        latLngs.add(new LatLng(31.215290069580078,121.42034149169922));

        mClusterManager.addItem(new PetalItem(31.215869903564453,121.42345428466797, R.mipmap.petal_orange));
        latLngs.add(new LatLng(31.215869903564453,121.42345428466797));

        mClusterManager.addItem(new PetalItem(31.217382431030273,121.4229507446289, R.mipmap.petal_purple));
        latLngs.add(new LatLng(31.217382431030273,121.4229507446289));

        mClusterManager.addItem(new PetalItem(31.2153263092041,121.42028045654297, R.mipmap.petal_blue));
        latLngs.add(new LatLng(31.2153263092041,121.42028045654297));

        mClusterManager.addItem(new PetalItem(31.219219207763672,121.4187240600586, R.mipmap.petal_red));
        latLngs.add(new LatLng(31.219219207763672,121.4187240600586));

        mClusterManager.addItem(new PetalItem(31.2174072265625,121.42224884033203, R.mipmap.petal_green));
        latLngs.add(new LatLng(31.219219207763672,121.4187240600586));

        mClusterManager.addItem(new PetalItem(31.218612670898438,121.41632843017578, R.mipmap.petal_yellow));
        latLngs.add(new LatLng(31.218612670898438,121.41632843017578));

        mClusterManager.addItem(new PetalItem(31.219221115112305,121.42646026611328, R.mipmap.petal_orange));
        latLngs.add(new LatLng(31.219221115112305,121.42646026611328));

        mClusterManager.addItem(new PetalItem(31.217193603515625,121.41686248779297, R.mipmap.petal_purple));
        latLngs.add(new LatLng(31.217193603515625,121.41686248779297));

        mClusterManager.addItem(new PetalItem(31.217649459838867,121.41787719726562, R.mipmap.petal_blue));
        latLngs.add(new LatLng(31.217649459838867,121.41787719726562));

        mClusterManager.addItem(new PetalItem(31.218660354614258,121.42047119140625, R.mipmap.petal_red));
        latLngs.add(new LatLng(31.218660354614258,121.42047119140625));

        mClusterManager.addItem(new PetalItem(31.215919494628906,121.41911315917969, R.mipmap.petal_green));
        latLngs.add(new LatLng(31.215919494628906,121.41911315917969));

        mClusterManager.addItem(new PetalItem(31.218727111816406,121.41691589355469, R.mipmap.petal_yellow));
        latLngs.add(new LatLng(31.218727111816406,121.41691589355469));

        mClusterManager.addItem(new PetalItem(31.219379425048828,121.4178695678711, R.mipmap.petal_orange));
        latLngs.add(new LatLng(31.219379425048828,121.4178695678711));

        mClusterManager.addItem(new PetalItem(31.21677017211914,121.41702270507812, R.mipmap.petal_purple));
        latLngs.add(new LatLng(31.21677017211914,121.41702270507812));

        mClusterManager.addItem(new PetalItem(31.218740463256836,121.41703033447266, R.mipmap.petal_blue));
        latLngs.add(new LatLng(31.218740463256836,121.41703033447266));

        mClusterManager.addItem(new PetalItem(31.21668815612793,121.4224853515625, R.mipmap.petal_red));
        latLngs.add(new LatLng(31.218660354614258,121.42047119140625));

        mClusterManager.addItem(new PetalItem(31.212051391601562,121.40787506103516, R.mipmap.petal_green));
        latLngs.add(new LatLng(31.212051391601562,121.40787506103516));

        mClusterManager.addItem(new PetalItem(31.219131469726562,121.41661071777344, R.mipmap.petal_yellow));
        latLngs.add(new LatLng(31.219131469726562,121.41661071777344));

        mClusterManager.addItem(new PetalItem(31.20825958251953,121.41893768310547, R.mipmap.petal_orange));
        latLngs.add(new LatLng(31.20825958251953,121.41893768310547));

        mClusterManager.addItem(new PetalItem(31.21293067932129,121.42123413085938, R.mipmap.petal_purple));
        latLngs.add(new LatLng(31.21293067932129,121.42123413085938));

        mClusterManager.addItem(new PetalItem(31.21812629699707,121.418701171875, R.mipmap.petal_blue));
        latLngs.add(new LatLng(31.21812629699707,121.418701171875));

        zoomToSpan(mTencentMap, latLngs, 0, 0, 0, 0);
    }

    public class PetalItem implements ClusterItem {

        private final LatLng mLatLng;

        private int mDrawableResourceId;

        public PetalItem(double latitude, double longitude, int resourceId) {
            // TODO Auto-generated constructor stub
            mLatLng = new LatLng(latitude, longitude);
            mDrawableResourceId = resourceId;
        }

        /* (non-Javadoc)
         * @see com.tencent.mapsdk.clustering.ClusterItem#getPosition()
         */
        @Override
        public LatLng getPosition() {
            // TODO Auto-generated method stub
            return mLatLng;
        }

        public int getDrawableResourceId() {
            return mDrawableResourceId;
        }

    }

    class CustomIconClusterRenderer extends DefaultClusterRenderer<PetalItem> {

        private IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private ImageView mItemImageView = new ImageView(getApplicationContext());
        private ImageView mClusterImageView = new ImageView(getApplicationContext());

        public CustomIconClusterRenderer(
                Context context, TencentMap map, ClusterManager clusterManager) {
            super(context, map, clusterManager);
            mItemImageView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            mIconGenerator.setContentView(mItemImageView);

            mClusterImageView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            mClusterIconGenerator.setContentView(mClusterImageView);

            setMinClusterSize(1);
        }

        @Override
        public void onBeforeClusterRendered(
                Cluster<PetalItem> cluster, MarkerOptions markerOptions) {
            int[] resources = new int[cluster.getItems().size()];
            int i = 0;
            for (PetalItem item : cluster.getItems()) {
                resources[i++] = item.getDrawableResourceId();
            }
            PetalDrawable drawable = new PetalDrawable(getApplicationContext(), resources);
            mClusterImageView.setImageDrawable(drawable);
            Bitmap icon = mClusterIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
            //不显示 infowindow
            markerOptions.infoWindowEnable(false);
        }

        @Override
        public void onBeforeClusterItemRendered(PetalItem item, MarkerOptions markerOptions) {
            mItemImageView.setImageResource(item.getDrawableResourceId());
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
    }

    public static void zoomToSpan(TencentMap tencentMap, List<LatLng> listPts,
                                  int leftPadding,
                                  int rightPadding,
                                  int topPadding,
                                  int bottomPadding) {
        if (listPts == null || tencentMap == null) {
            return;
        }
        int iPtSize = listPts.size();
        if (iPtSize <= 0) {
            return;
        }

        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();
        LatLng latlng = null;

        for (int i = 0; i < iPtSize; i++) {
            latlng = listPts.get(i);

            if (latlng == null) {
                continue;
            }

            boundBuilder.include(latlng);
        }

        final LatLngBounds bounds = boundBuilder.build();


        tencentMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, leftPadding, rightPadding, topPadding, bottomPadding));
    }
}
