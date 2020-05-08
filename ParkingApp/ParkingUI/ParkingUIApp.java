package ParkingUI;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import javax.swing.JToolTip;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;


public class ParkingUIApp {

	public static void main(String[] args) {
		// Create a TileFactoryInfo for Virtual Earth
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);

		// Setup local file cache
		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

		// Setup JXMapViewer
		JXMapViewer mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(tileFactory);

		GeoPosition athleticCenter = new GeoPosition(34.2265,-118.8762);
		GeoPosition montclef = new GeoPosition(34.2250,-118.8765);
		GeoPosition trinity = new GeoPosition(34.2238,-118.8828);
		
		// Add Tooltip with Latitude and Longitude of Mouse
        final JToolTip tooltip = new JToolTip();
        tooltip.setTipText("Java");
        tooltip.setComponent(mapViewer);

		// Set the focus
		mapViewer.setZoom(3);
		mapViewer.setAddressLocation(athleticCenter);

		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		// Create waypoints from the geo-positions
		Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(
				Arrays.asList(new MyWaypoint("", Color.RED, athleticCenter),
						new MyWaypoint("", Color.GREEN, montclef),
						new MyWaypoint("", Color.YELLOW, trinity)));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new FancyWaypointRenderer());

		mapViewer.setOverlayPainter(waypointPainter);

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("Available Parking");
		frame.getContentPane().add(mapViewer);
		frame.setSize(1200,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		mapViewer.addPropertyChangeListener("zoom", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateWindowTitle(frame, mapViewer);
			}
		});

		mapViewer.addPropertyChangeListener("center", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateWindowTitle(frame, mapViewer);
			}
		});

		updateWindowTitle(frame, mapViewer);
	}

	protected static void updateWindowTitle(JFrame frame, JXMapViewer mapViewer) {
		double lat = mapViewer.getCenterPosition().getLatitude();
		double lon = mapViewer.getCenterPosition().getLongitude();
		int zoom = mapViewer.getZoom();

		frame.setTitle(String.format("Centered at (%.2f / %.2f) with Zoom: %d", lat, lon, zoom));
	}
}