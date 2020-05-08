package ParkingApp;
//
//import java.util.List;
//import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.geometry.Point2D;
//import javafx.scene.Scene;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.RadioMenuItem;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import javafx.event.ActionEvent;

public class GUI extends Object {}

//	protected GoogleMapView mapComponent;
//	protected GoogleMap map;
//
//	Queries query = new Queries();
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void start(final Stage primaryStage) {}
//		
//		mapComponent = new GoogleMapView(true);
//		mapComponent.addMapInitializedListener(this);

//		BorderPane root = new BorderPane();
//
//		root.setTop(menuSetup());
//		root.setCenter(mapComponent);
//		
//		primaryStage.setTitle("JavaFX App");
//
//		Scene menuItems = new Scene(root, 960, 600);
//		primaryStage.setScene(menuItems);
//		primaryStage.show();
//
//	}
//
//	public MenuBar menuSetup() {
//
//		MenuBar menuBar = new MenuBar();
//
//		Menu queryMenu = new Menu("Search Available");
//		Menu zoomMenu = new Menu("Zoom Functions");
//
//		try {
//			RadioMenuItem choice1Item = new RadioMenuItem("Any");
//			EventHandler<ActionEvent> eventAny = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findAvailable();
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//
//					}
//				}
//			};
//			choice1Item.setOnAction(eventAny);
//
//			RadioMenuItem choice2Item = new RadioMenuItem("Commuter");
//			EventHandler<ActionEvent> eventCommuter = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("C");
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//					}
//				}
//			};
//			choice2Item.setOnAction(eventCommuter);
//
//			RadioMenuItem choice3Item = new RadioMenuItem("Staff");
//			EventHandler<ActionEvent> eventStaff = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("S");
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//					}
//				}
//			};
//			choice3Item.setOnAction(eventStaff);
//
//			RadioMenuItem choice4Item = new RadioMenuItem("Resident");
//			EventHandler<ActionEvent> eventResident = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("R");
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//					}
//				}
//			};
//			choice4Item.setOnAction(eventResident);
//
//			RadioMenuItem choice5Item = new RadioMenuItem("Exempt");
//			EventHandler<ActionEvent> eventExempt = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("E");
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//					}
//				}
//			};
//			choice5Item.setOnAction(eventExempt);
//
//			RadioMenuItem choice6Item = new RadioMenuItem("General");
//			EventHandler<ActionEvent> eventGeneral = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					List<ParkingSpot> spotsAvailable = query.findSpecificAvailable("G");
//					for (ParkingSpot temp : spotsAvailable) {
//						System.out.println(temp);
//					}
//				}
//			};
//			choice6Item.setOnAction(eventGeneral);
//
//			MenuItem zoomIn = new MenuItem("Zoom In");
//			EventHandler<ActionEvent> eventZoomIn = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					map.zoomProperty().set(map.getZoom() + 1);
//				}
//			};
//			zoomIn.setOnAction(eventZoomIn);
//			zoomIn.setDisable(true);
//
//			MenuItem zoomOut = new MenuItem("Zoom Out");
//			EventHandler<ActionEvent> eventZoomOut = new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent e) {
//					map.zoomProperty().set(map.getZoom() - 1);
//				}
//			};
//
//			zoomOut.setOnAction(eventZoomOut);
//			zoomOut.setDisable(true);
//
//			ToggleGroup toggleGroup = new ToggleGroup();
//			toggleGroup.getToggles().add(choice1Item);
//			toggleGroup.getToggles().add(choice2Item);
//			toggleGroup.getToggles().add(choice3Item);
//			toggleGroup.getToggles().add(choice4Item);
//			toggleGroup.getToggles().add(choice5Item);
//			toggleGroup.getToggles().add(choice6Item);
//
//			queryMenu.getItems().add(choice1Item);
//			queryMenu.getItems().add(choice2Item);
//			queryMenu.getItems().add(choice3Item);
//			queryMenu.getItems().add(choice4Item);
//			queryMenu.getItems().add(choice5Item);
//			queryMenu.getItems().add(choice6Item);
//
//			zoomMenu.getItems().add(zoomIn);
//			zoomMenu.getItems().add(zoomOut);
//
//			menuBar.getMenus().add(queryMenu);
//			menuBar.getMenus().add(zoomMenu);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//
//		return menuBar;
//	}
//
//	public void mapInitialized() {
//
//		// System.out.println("MainApp.mapInitialised....");
//
//		// Once the map has been loaded by the Webview, initialize the map
//		// details.
//		LatLong center = new LatLong(34.222084, -118.879372);
//		mapComponent.addMapReadyListener(() -> {
//			// This call will fail unless the map is completely ready.
//			checkCenter(center);
//		});
//
//		MapOptions options = new MapOptions();
//		options.center(center).zoom(9).overviewMapControl(false).panControl(false).rotateControl(false)
//				.scaleControl(false).streetViewControl(false).zoomControl(false).mapType(MapTypeIdEnum.TERRAIN)
//				.clickableIcons(false).disableDefaultUI(true).disableDoubleClickZoom(true).keyboardShortcuts(false)
//				.styleString(
//						"[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]");
//
//		// [{\"featureType\":\"landscape\",\"stylers\":[{\"saturation\":-100},{\"lightness\":65},{\"visibility\":\"on\"}]},{\"featureType\":\"poi\",\"stylers\":[{\"saturation\":-100},{\"lightness\":51},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.highway\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]
//		map = mapComponent.createMap(options, false);
//
//	}
//
//	private void checkCenter(LatLong center) {
//		System.out.println("Testing fromLatLngToPoint using: " + center);
//		Point2D p = map.fromLatLngToPoint(center);
//		System.out.println("Testing fromLatLngToPoint result: " + p);
//		System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth() / 2 + ", "
//				+ mapComponent.getHeight() / 2);
//	}
//}
