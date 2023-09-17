package com.example.testcarappwithfragment.Ui;

public class Constants {
    public static final int DB_VERSION = 4;
    public static final String DB_NAME = "CarSalesAndManagement";
    public static final String TABLE_NAME_CUSTOMER = "Customer";
    public static final String TABLE_NAME_ADMIN = "Admin";
    public static final String TABLE_NAME_CAR = "car";
    public static final String TABLE_NAME_CAR_BRAND = "carBrand";
    public static final String TABLE_NAME_CART = "cart";
    public static final String TABLE_NAME_ORDER_STATUS = "orderStatus";
    public static final String TABLE_NAME_ORDER = "order_table";
    public static final String TABLE_NAME_ORDER_DETAILS = "order_details";
    public static final String TABLE_NAME_WISHLIST = "wishlist";
    public static final String TABLE_NAME_CAR_PARTS = "car_parts_table";
    public static final String TABLE_NAME_CONTACT_US = "contact_us";

    //Customer Table columns
    public static final String KEY_CUSTOMER_ID = "customer_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CUSTOMER_NAME = "customer_name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";

    //Admin Table columns
    public static final String KEY_EMAIL_ADMIN = "admin_email";
    public static final String KEY_ADMIN_NAME = "admin_name";
    public static final String KEY_PHONE_NUMBER_ADMIN = "admin_phone_number";
    public static final String KEY_ADDRESS_ADMIN = "admin_address";
    public static final String KEY_PASSWORD_ADMIN = "admin_password";
    public static final String KEY_DATE_OF_BIRTH_ADMIN = "admin_date_of_birth";

    //Car or product Table columns
    public static final String KEY_PLATE_NO = "plate_no";
    public static final String KEY_CAR_BRAND = "car_brand";
    public static final String KEY_CAR_MODEL = "car_model";
    public static final String KEY_CAR_PRICE = "car_price";
    public static final String KEY_CAR_OWNER_TYPE = "car_owner_type";
    public static final String KEY_CAR_IMAGE = "car_image";
    public static final String KEY_CAR_SHORTDESCRRIPTION = "car_short_descrription";
    public static final String KEY_CAR_ID = "car_id";
    public static final String KEY_CAR_BRAND_ID = "car_brand_id";
    public static final String KEY_CAR_STATUS = "car_status";
    public static final String KEY_CAR_QUANTITY = "car_quantity";

    //CarBrand or category Table columns
    public static final String KEY_BRAND_ID = "car_brand_id";
    public static final String KEY_CAR_BRAND_NAME = "car_brand_name";
    public static final String KEY_CAR_BRAND_STATUS = "car_brand_status";

    //Shopping Cart Table columns
    public static final String KEY_CART_ID = "shopping_cart_id"; //This is the primary key
    public static final String KEY_USER_ID = "customer_id";
    public static final String KEY_USER_EMAIL = "customer_email";
    public static final String KEY_USER_PASSWORD = "customer_password";
    public static final String KEY_CAR_ID_FOR_CART = "car_id";
    public static final String KEY_CAR_BRAND_FOR_CART = "car_brand";
    public static final String KEY_CAR_MODEL_FOR_CART = "car_model";
    public static final String KEY_CAR_IMAGE_FOR_CART = "car_image";
    public static final String KEY_CAR_PRICE_FOR_CART = "car_unit_price";
    public static final String KEY_CAR_QUANTITY_FOR_CART = "car_quantity";
    public static final String KEY_CAR_TOTAL_PRICE_FOR_CART = "car_total_price";

    //OrderStatus Table columns
    public static final String KEY_ORDER_STATUS_ID = "order_status_id";
    public static final String KEY_ORDER_STATUS_NAME = "order_status_name";

    //Order Table columns
    public static final String KEY_ID_FOR_ORDER = "id"; //This is the primary key
    public static final String KEY_USER_ID_FOR_ORDER = "user_id";
    public static final String KEY_USER_ADDRESS_FOR_ORDER = "address";
    public static final String KEY_USER_CITY_FOR_ORDER = "city";
    public static final String KEY_PINCODE_FOR_ORDER = "pincode";
    public static final String KEY_PAYMENT_TYPE_FOR_ORDER = "payment_type";
    public static final String KEY_TOTAL_PRICE_FOR_ORDER = "total_price";
    public static final String KEY_PAYMENT_STATUS_FOR_ORDER = "payment_status";
    public static final String KEY_ORDER_STATUS_FOR_ORDER = "order_status";
    public static final String KEY_ADDED_ON_FOR_ORDER = "added_on";

    //OrderDetails Table columns
    public static final String KEY_ID_FOR_ORDER_DETAILS = "id"; //This is the primary key
    public static final String KEY_ORDER_ID_FOR_ORDER_DETAILS = "order_id";
    public static final String KEY_PRODUCT_ID_FOR_ORDER_DETAILS = "product_id";
    public static final String KEY_QUANTITY_FOR_ORDER_DETAILS = "qty";
    public static final String KEY_PRICE_FOR_ORDER_DETAILS = "price";
    public static final String KEY_ADDED_ON_FOR_ORDER_DETAILS = "added_on";

    //Wishliat Table columns
    public static final String KEY_ID_FOR_WISHLIST = "id"; //This is the primary key
    public static final String KEY_USER_ID_FOR_WISHLIST = "user_id";
    public static final String KEY_CAR_ID_FOR_WISHLIST = "car_id";
    public static final String KEY_ADDED_ON_FOR_WISHLIST = "added_on";

    //CarParts Table columns
    public static final String KEY_ID_FOR_CAR_PARTS = "id"; //This is the primary key
    public static final String KEY_CAR_PARTS_NAME = "car_parts_name";
    public static final String KEY_QUANTITY_FOR_CAR_PARTS = "car_parts_quantity";
    public static final String KEY_PRICE_FOR_CAR_PARTS = "car_parts_price";
    public static final String KEY_VOUCHER_FOR_CAR_PARTS = "car_parts_voucher";
    public static final String KEY_IMAGE_FOR_CAR_PARTS= "car_parts_image";


    //Contact Us Table columns
    public static final String KEY_ID_FOR_CONTACT_US = "id"; //This is the primary key
    public static final String KEY_NAME_FOR_CONTACT_US = "sender_name";
    public static final String KEY_EMAIL_FOR_CONTACT_US = "sender_email";
    public static final String KEY_MOBILE_FOR_CONTACT_US = "sender_mobile";
    public static final String KEY_COMMENT_FOR_CONTACT_US = "sender_comment";
    public static final String KEY_ADDED_ON_FOR_CONTACT_US= "added_on";










}
