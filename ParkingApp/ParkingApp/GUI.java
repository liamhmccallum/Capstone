package ParkingApp;

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

import ParkingUI.FancyWaypointRenderer;
import ParkingUI.MyWaypoint;

public class GUI {

	Queries query = new Queries();

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
	
	//
	// @Override
	// public void start(final Stage primaryStage) {}
	//
	// mapComponent = new GoogleMapView(true);
	// mapComponent.addMapInitializedListener(this);

	// BorderPane root = new BorderPane();
	//
	// root.setTop(menuSetup());
	// root.setCenter(mapComponent);
	//
	// primaryStage.setTitle("JavaFX App");
	//
	// Scene menuItems = new Scene(root, 960, 600);
	// primaryStage.setScene(menuItems);
	// primaryStage.show();
	//
	// }
	//
	// public MenuBar menuSetup() {
	//
	// MenuBar menuBar = new MenuBar();
	//
	// Menu queryMenu = new Menu("Search Available");
	// Menu zoomMenu = new Menu("Zoom Functions");
	//
	// try {
	// RadioMenuItem choice1Item = new RadioMenuItem("Any");
	// EventHandler<ActionEvent> eventAny = new EventHandler<ActionEvent>() {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findAvailable();
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	//
	// }
	// }
	// };
	// choice1Item.setOnAction(eventAny);
	//
	// RadioMenuItem choice2Item = new RadioMenuItem("Commuter");
	// EventHandler<ActionEvent> eventCommuter = new EventHandler<ActionEvent>()
	// {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("C");
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	// }
	// }
	// };
	// choice2Item.setOnAction(eventCommuter);
	//
	// RadioMenuItem choice3Item = new RadioMenuItem("Staff");
	// EventHandler<ActionEvent> eventStaff = new EventHandler<ActionEvent>() {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("S");
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	// }
	// }
	// };
	// choice3Item.setOnAction(eventStaff);
	//
	// RadioMenuItem choice4Item = new RadioMenuItem("Resident");
	// EventHandler<ActionEvent> eventResident = new EventHandler<ActionEvent>()
	// {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("R");
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	// }
	// }
	// };
	// choice4Item.setOnAction(eventResident);
	//
	// RadioMenuItem choice5Item = new RadioMenuItem("Exempt");
	// EventHandler<ActionEvent> eventExempt = new EventHandler<ActionEvent>() {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("E");
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	// }
	// }
	// };
	// choice5Item.setOnAction(eventExempt);
	//
	// RadioMenuItem choice6Item = new RadioMenuItem("General");
	// EventHandler<ActionEvent> eventGeneral = new EventHandler<ActionEvent>()
	// {
	// public void handle(ActionEvent e) {
	// List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("G");
	// for (ParkingSpot temp : spotsAvailable) {
	// System.out.println(temp);
	// }
	// }
	// };
	// choice6Item.setOnAction(eventGeneral);
	//
	// MenuItem zoomIn = new MenuItem("Zoom In");
	// EventHandler<ActionEvent> eventZoomIn = new EventHandler<ActionEvent>() {
	// public void handle(ActionEvent e) {
	// map.zoomProperty().set(map.getZoom() + 1);
	// }
	// };
	// zoomIn.setOnAction(eventZoomIn);
	// zoomIn.setDisable(true);
	//
	// MenuItem zoomOut = new MenuItem("Zoom Out");
	// EventHandler<ActionEvent> eventZoomOut = new EventHandler<ActionEvent>()
	// {
	// public void handle(ActionEvent e) {
	// map.zoomProperty().set(map.getZoom() - 1);
	// }
	// };
	//
	// zoomOut.setOnAction(eventZoomOut);
	// zoomOut.setDisable(true);
	//
	// ToggleGroup toggleGroup = new ToggleGroup();
	// toggleGroup.getToggles().add(choice1Item);
	// toggleGroup.getToggles().add(choice2Item);
	// toggleGroup.getToggles().add(choice3Item);
	// toggleGroup.getToggles().add(choice4Item);
	// toggleGroup.getToggles().add(choice5Item);
	// toggleGroup.getToggles().add(choice6Item);
	//
	// queryMenu.getItems().add(choice1Item);
	// queryMenu.getItems().add(choice2Item);
	// queryMenu.getItems().add(choice3Item);
	// queryMenu.getItems().add(choice4Item);
	// queryMenu.getItems().add(choice5Item);
	// queryMenu.getItems().add(choice6Item);
	//
	// zoomMenu.getItems().add(zoomIn);
	// zoomMenu.getItems().add(zoomOut);
	//
	// menuBar.getMenus().add(queryMenu);
	// menuBar.getMenus().add(zoomMenu);
	// } catch (NumberFormatException e) {
	// e.printStackTrace();
	// }
	//
	// return menuBar;
	// }
	//
	// public void mapInitialized() {
	//
	// // System.out.println("MainApp.mapInitialised....");
	//
	// // Once the map has been loaded by the Webview, initialize the map
	// // details.
	// LatLong center = new LatLong(34.222084, -118.879372);
	// mapComponent.addMapReadyListener(() -> {
	// // This call will fail unless the map is completely ready.
	// checkCenter(center);
	// });
	//
	// MapOptions options = new MapOptions();
	// options.center(center).zoom(9).overviewMapControl(false).panControl(false).rotateControl(false)
	// .scaleControl(false).streetViewControl(false).zoomControl(false).mapType(MapTypeIdEnum.TERRAIN)
	// .clickableIcons(false).disableDefaultUI(true).disableDoubleClickZoom(true).keyboardShortcuts(false)
	// .styleString(
	// "[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]");
	//
	// //
	// [{\"featureType\":\"landscape\",\"stylers\":[{\"saturation\":-100},{\"lightness\":65},{\"visibility\":\"on\"}]},{\"featureType\":\"poi\",\"stylers\":[{\"saturation\":-100},{\"lightness\":51},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.highway\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]
	// map = mapComponent.createMap(options, false);
	//
	// }
	//
	// private void checkCenter(LatLong center) {
	// System.out.println("Testing fromLatLngToPoint using: " + center);
	// Point2D p = map.fromLatLngToPoint(center);
	// System.out.println("Testing fromLatLngToPoint result: " + p);
	// System.out.println("Testing fromLatLngToPoint expected: " +
	// mapComponent.getWidth() / 2 + ", "
	// + mapComponent.getHeight() / 2);
	// }
}
