# Farm Dashboard Application

## Overview

This project is a farm dashboard application built using JavaFX. It allows users to visualize and manage farm items and item containers. The application provides functionality to add, rename, change the location, dimensions, price, and delete farm items and containers. Additionally, it includes the ability to visualize the farm layout and control drone movements.

## Classes & Functionality

### **Model Classes**

1. **FarmItem**
   - **Description**: The base class for all farm items. It stores basic information about an item such as name, location, dimensions, price, and associated shapes for visualization.
   - **Key Methods**: `getName()`, `getLocationX()`, `setLocationX()`, `setAssociatedShapes()`, etc.

2. **Item** (extends FarmItem)
   - **Description**: A subclass of `FarmItem` representing a specific item on the farm, which may be a drone.
   - **Key Methods**: `checkDrone()` (to check if the item is a drone).

3. **ItemContainer** (extends FarmItem)
   - **Description**: Represents a container for farm items. It holds a list of farm items.
   - **Key Methods**: `getItemList()`, `setItemList()`.

### **Service Classes**

4. **Add**
   - **Description**: Responsible for adding new items or item containers to the dashboard.
   - **Key Methods**: `addItem()`, `addItemContainer()`.

5. **ChangeDimensions**
   - **Description**: Allows the user to change the dimensions of a selected farm item or container.
   - **Key Methods**: `changeItemDimensions()`, `changeItemContainerDimensions()`.

6. **ChangeLocation**
   - **Description**: Allows the user to change the location of a selected farm item or container.
   - **Key Methods**: `changeItemLocation()`, `changeItemContainerLocation()`.

7. **ChangePrice**
   - **Description**: Allows the user to change the price of a selected farm item or container.
   - **Key Methods**: `changeItemPrice()`, `changeItemContainerPrice()`.

8. **Delete**
   - **Description**: Handles the deletion of a selected farm item or container.
   - **Key Methods**: `deleteItem()`, `deleteItemContainer()`.

9. **Rename**
   - **Description**: Allows the user to rename a selected farm item or container.
   - **Key Methods**: `renameItem()`, `renameItemContainer()`.

10. **Draw**
    - **Description**: Responsible for drawing farm items and containers in the visualization area. It visualizes items as rectangles with diagonal lines and handles the display of drones.
    - **Key Methods**: `drawFarmItem()`, `redraw()`, `drawDrone()`.

### **Controller Classes**

11. **DashboardController**
    - **Description**: Manages the interactions between the view and the underlying data. It responds to user actions such as visiting items or scanning the farm.
    - **Key Methods**: `handleVisitItem()`, `handleScanFarm()`.

### **View Classes**

12. **DashboardView**
    - **Description**: Contains the main user interface for the farm dashboard, including the tree view of farm items, controls for interacting with the items, and the visualization area where items are displayed.
    - **Key Methods**: `createScene()`, `drawFarmItem()`, `redraw()`.

### **Application Class**

13. **FarmDashboardApp**
    - **Description**: The entry point for the JavaFX application. It initializes the primary stage and sets up the dashboard view.
    - **Key Methods**: `start()`.

---

## Design Patterns Implemented

### **Singleton Design Pattern**
The Singleton design pattern is used to ensure that only one instance of a particular class is created throughout the application. In our implementation, the `DashboardView` class is designed as a Singleton to ensure that there is only one instance of the dashboard throughout the application’s lifecycle. Instead of creating multiple instances of the `DashboardView`, we instantiate it only once. This ensures that all user interactions, visualization, and item management happen in a single, consistent environment.

By using the Singleton pattern for the `DashboardView`, we avoid the creation of redundant UI elements and ensure that all operations like adding, renaming, and changing items are managed in one unified instance, preventing potential conflicts or inconsistencies across multiple dashboard instances.

### **Composite Design Pattern**
The Composite design pattern is used to treat individual farm items and item containers uniformly. An `ItemContainer` can hold multiple `FarmItem` objects, including other containers or individual items. This pattern allows us to treat both `Item` and `ItemContainer` as `FarmItem` objects, which simplifies the management of these objects in the UI and operations like adding, deleting, or changing their properties. By using this pattern, we can create complex hierarchical structures of farm items (individual items and containers) while maintaining simplicity in the way we manipulate and display them.

---

## Project Setup

1. Clone this repository or download the source code.
2. Ensure that you have Java and JavaFX set up on your system.
3. Run `FarmDashboardApp.java` as a JavaFX application.

---

## Dependencies

- JavaFX
