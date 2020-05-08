package ParkingApp;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
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

import ParkingUI.FancyWaypointRenderer;
import ParkingUI.MyWaypoint;
import javafx.scene.input.KeyEvent;

public class GUI {
	static JXMapViewer mapViewer = new JXMapViewer();

	public static void main(String[] args) {
		// Create a TileFactoryInfo for Virtual Earth
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);

		// Setup local file cache
		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

		// Setup JXMapViewer
		mapViewer.setTileFactory(tileFactory);

		GeoPosition athleticCenter = new GeoPosition(34.2265, -118.8762);
		GeoPosition montclef = new GeoPosition(34.2250, -118.8765);
		GeoPosition trinity = new GeoPosition(34.2238, -118.8828);
		GeoPosition center = new GeoPosition(34.221968, -118.879374);

		// Add Tooltip with Latitude and Longitude of Mouse
		final JToolTip tooltip = new JToolTip();
		tooltip.setTipText("Java");
		tooltip.setComponent(mapViewer);

		// Set the focus
		mapViewer.setZoom(2);
		mapViewer.setAddressLocation(center);

		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		// Create waypoints from the geo-positions
		Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.RED, athleticCenter),
				new MyWaypoint("", Color.GREEN, montclef), new MyWaypoint("", Color.YELLOW, trinity)));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new FancyWaypointRenderer());

		mapViewer.setOverlayPainter(waypointPainter);

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("Available Parking");
		frame.getContentPane().add(mapViewer);

		frame.setJMenuBar(menuBarSetUp());

		frame.setSize(1200, 800);
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

	protected static JMenuBar menuBarSetUp() {
		Queries query = new Queries();
		final JMenuBar toolBar = new JMenuBar();
		JMenu menu = new JMenu("Search Options");

		ButtonGroup group = new ButtonGroup();

		JRadioButtonMenuItem menuItemAny = new JRadioButtonMenuItem("Any");
		menuItemAny.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findAvailable();
					Set<MyWaypoint> waypointSelected = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						Color c;
						if(temp.getDesignation().indexOf("C") != -1)
						{
							c = Color.RED;
						}else if (temp.getDesignation().indexOf("S") != -1)
						{
							c = Color.CYAN;
						}else if (temp.getDesignation().indexOf("R") != -1)
						{
							c = Color.GREEN;
						}else if (temp.getDesignation().indexOf("E") != -1)
						{
							c = Color.BLUE;
						}else
						{
							c = Color.YELLOW;
						}
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointSelected.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", c, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointSelected);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemAny);
		menu.add(menuItemAny);

		JRadioButtonMenuItem menuItemCommuter = new JRadioButtonMenuItem("Commuter");
		menuItemCommuter.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("C");
					Set<MyWaypoint> waypointCommuter = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointCommuter.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.RED, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointCommuter);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemCommuter);
		menu.add(menuItemCommuter);

		JRadioButtonMenuItem menuItemStaff = new JRadioButtonMenuItem("Staff");
		menuItemStaff.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("S");
					Set<MyWaypoint> waypointStaff = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointStaff.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.CYAN, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointStaff);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemStaff);
		menu.add(menuItemStaff);

		JRadioButtonMenuItem menuItemResident = new JRadioButtonMenuItem("Resident");
		menuItemResident.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("R");
					Set<MyWaypoint> waypointResident = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointResident.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.GREEN, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointResident);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemResident);
		menu.add(menuItemResident);

		JRadioButtonMenuItem menuItemExempt = new JRadioButtonMenuItem("Exempt");
		menuItemExempt.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("E");
					Set<MyWaypoint> waypointExempt = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointExempt.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.BLUE, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointExempt);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemExempt);
		menu.add(menuItemExempt);

		JRadioButtonMenuItem menuItemGeneral = new JRadioButtonMenuItem("General");
		menuItemGeneral.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("G");
					Set<MyWaypoint> waypointGeneral = new HashSet<MyWaypoint>();
					for (ParkingSpot temp : spotsAvailable) {
						GeoPosition currentSpot = new GeoPosition(temp.getLatitude().doubleValue(),temp.getLongitude().doubleValue());
						waypointGeneral.addAll(new HashSet<MyWaypoint>(Arrays.asList(new MyWaypoint("", Color.YELLOW, currentSpot))));
					}
					
					WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
					waypointPainter.setWaypoints(waypointGeneral);
					waypointPainter.setRenderer(new FancyWaypointRenderer());

					mapViewer.setOverlayPainter(waypointPainter);

				}
			}
		});
		group.add(menuItemGeneral);
		menu.add(menuItemGeneral);

		toolBar.add(menu);

		return toolBar;
	}
}
