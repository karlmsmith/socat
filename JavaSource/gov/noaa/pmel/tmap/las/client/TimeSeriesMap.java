/**
 * This software is provided by NOAA for full, free and open release.  It is
 * understood by the recipient/user that NOAA assumes no liability for any
 * errors contained in the code.  Although this software is released without
 * conditions or restrictions in its use, it is expected that appropriate
 * credit be given to its author and to the National Oceanic and Atmospheric
 * Administration should the software be included by the recipient as an
 * element in other product development. 
 */
package gov.noaa.pmel.tmap.las.client;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author rhs
 *
 */
public class TimeSeriesMap extends Composite {
	MapWidget map;
	Label label = new Label();
	Button reset = new Button("Reset Map");
	Grid layout_grid = new Grid(1,2);
	CategorySerializable cat;
	VerticalPanel layout = new VerticalPanel();
	String currentGridID;
	LatLng sw;
	LatLng ne;
	int default_zoom;
	LatLng default_center;
	/**
	 * 
	 */
	public TimeSeriesMap(String text) {
		map = new MapWidget(new LatLng(0.0, 0.0), 1);
		map.setSize("520px", "350px");
		map.addControl(new LargeMapControl());
		map.addControl(new MapTypeControl());
		label.setText(text);
		layout.add(label);
		layout.add(map);
		layout.add(reset);
		reset.addClickListener(resetListener);
		initWidget(layout);
	}
	public void update(CategorySerializable categorySerializable) {
		cat = categorySerializable;
		map.clearOverlays();
		double sw_lat = 9999.;
		double sw_lon = 9999.;
		double ne_lat = -9999.;
		double ne_lon = -9999.;
		HashMap<String, HashMap<String, GridSerializable>> grids = new HashMap<String, HashMap<String, GridSerializable>>();
		if ( cat.hasMultipleDatasets() ) {
			DatasetSerializable[] ds = cat.getDatasetSerializableArray();
			for (int i = 0; i < ds.length; i++) {
				HashMap<String, GridSerializable> dsGrids = new HashMap<String, GridSerializable>();
				DatasetSerializable d = ds[i];
				VariableSerializable[] vars = d.getVariablesSerializable();
				for (int j = 0; j < vars.length; j++) {
					VariableSerializable var = vars[j];
					GridSerializable grid = var.getGrid();
					dsGrids.put(grid.getID(), grid);
				}
				grids.put(d.getName(), dsGrids);
			}
		} else {
			DatasetSerializable ds = cat.getDatasetSerializable();
			HashMap<String, GridSerializable> dsGrids = new HashMap<String, GridSerializable>();
			VariableSerializable[] vars = ds.getVariablesSerializable();
			for (int j = 0; j < vars.length; j++) {
				VariableSerializable var = vars[j];
				GridSerializable grid = var.getGrid();
				dsGrids.put(grid.getID(), grid);
			}
			grids.put(ds.getName(), dsGrids);
		}
		for (Iterator gridIt = grids.keySet().iterator(); gridIt.hasNext();) {
			String key = (String) gridIt.next();
			HashMap<String, GridSerializable> dsGrids = grids.get(key);
			for (Iterator dsgIt = dsGrids.keySet().iterator(); dsgIt.hasNext();) {
				String gridid = (String) dsgIt.next();
				GridSerializable grid = dsGrids.get(gridid);

				AxisSerializable xAxis = grid.getXAxis();
				AxisSerializable yAxis = grid.getYAxis();
				double y = Double.valueOf(yAxis.getLo()).doubleValue();
				double x = Double.valueOf(xAxis.getLo()).doubleValue();

				if ( y < sw_lat ) sw_lat = y;
				if ( y > ne_lat ) ne_lat = y;
				if ( x < sw_lon ) sw_lon = x;
				if ( x > ne_lon ) ne_lon = x;
				MarkerOptions options = new MarkerOptions();
				options.setTitle(key);
				Marker marker = new Marker(new LatLng(y, x), options);
				final String gridID = grid.getID();
				marker.addMarkerClickHandler(new MarkerClickHandler() {
					String id = gridID;
					public void onDoubleClick(Marker sender) {
					}

					public void onClick(MarkerClickEvent event) {
						setCurrentGridID(gridID);
					}
				});
				map.addOverlay(marker);
			}
		}

		double center_x = sw_lon + ((ne_lon - sw_lon)/2.0);
		double center_y = sw_lat + ((ne_lat - sw_lat)/2.0);
		sw = new LatLng(sw_lat, sw_lon);
		ne = new LatLng(ne_lat, ne_lon);
		LatLngBounds bounds = new LatLngBounds(sw, ne);
		default_zoom = map.getBoundsZoomLevel(bounds);
        default_center = new LatLng(center_y, center_x);
		map.setCenter(default_center, default_zoom);

	}
	ClickListener resetListener = new ClickListener() {
		public void onClick(Widget button) {
			map.setCenter(default_center, default_zoom);
		}
	};
	public void setCurrentGridID(String id) {
		currentGridID = id;
	}
	public String getCurrentGridID() {
		return this.currentGridID;
	}
	
	public void addMapClickHandler(MapClickHandler handler) {
		map.addMapClickHandler(handler);
	}
}
