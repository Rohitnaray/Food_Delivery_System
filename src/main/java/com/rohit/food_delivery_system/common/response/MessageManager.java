package com.rohit.food_delivery_system.common.response;

public final class MessageManager {

    private MessageManager() {
    }

    // Success messages
    public static final String SUCCESS =
            "Operation completed successfully";

    public static final String USER_CREATED =
            "User created successfully";

    public static final String ADDRESS_CREATED =
            "Address created successfully";

    public static final String RESTAURANT_CREATED =
            "Restaurant created successfully";

    public static final String MENU_ITEM_CREATED =
            "Menu item created successfully";

    public static final String ORDER_CREATED =
            "Order created successfully";

    public static final String DELIVERY_ASSIGNED =
            "Delivery assigned successfully";

    public static final String DELIVERY_STATUS_UPDATED =
            "Delivery status updated successfully";


    // Error messages
    public static final String USER_NOT_FOUND =
            "User not found";

    public static final String RESTAURANT_NOT_FOUND =
            "Restaurant not found";

    public static final String MENU_ITEM_NOT_FOUND =
            "Menu item not found";

    public static final String ORDER_NOT_FOUND =
            "Order not found";

    public static final String DELIVERY_NOT_FOUND =
            "Delivery not found";

    public static final String DRIVER_NOT_FOUND =
            "Driver not found";

    public static final String NOT_A_DRIVER =
            "User is not a driver";

    public static final String DELIVERY_ALREADY_ASSIGNED =
            "Delivery already assigned";

    public static final String INVALID_DELIVERY_STATUS =
            "Invalid delivery status transition";

    public static final String MENU_ITEM_NOT_AVAILABLE =
            "Menu item is not available";

    public static final String INVALID_REQUEST =
            "Invalid request";

    public static final String INTERNAL_SERVER_ERROR =
            "Internal server error";
}