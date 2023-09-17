package com.example.testcarappwithfragment.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.testcarappwithfragment.Model.Admin;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarBrand;
import com.example.testcarappwithfragment.Model.CarParts;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.Order;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.Model.OrderMasterDetailList;
import com.example.testcarappwithfragment.Model.OrderMasterList;
import com.example.testcarappwithfragment.Model.OrderStatus;
import com.example.testcarappwithfragment.Model.Wishlist;
import com.example.testcarappwithfragment.Model.WishlistCarProperty;
import com.example.testcarappwithfragment.Model.contact_us;
import com.example.testcarappwithfragment.Ui.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*Here we create the customer table*/
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CUSTOMER +
                "(" + Constants.KEY_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_EMAIL + " TEXT," +
                Constants.KEY_CUSTOMER_NAME + " TEXT," +
                Constants.KEY_PHONE_NUMBER + " TEXT," +
                Constants.KEY_ADDRESS + " TEXT," +
                /*Constants.KEY_DATE_OF_BIRTH + " TEXT," +*/
                Constants.KEY_PASSWORD + " TEXT);";

        /*Here we create the admin table*/
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_ADMIN +
                "(" + Constants.KEY_EMAIL_ADMIN + " TEXT PRIMARY KEY," +
                Constants.KEY_ADMIN_NAME + " TEXT," +
                Constants.KEY_PHONE_NUMBER_ADMIN + " INTEGER," +
                Constants.KEY_ADDRESS_ADMIN + " TEXT," +
                /*Constants.KEY_DATE_OF_BIRTH + " TEXT," +*/
                Constants.KEY_PASSWORD_ADMIN + " TEXT);";

        /*Here we create the car table*/
        String CREATE_CAR_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CAR +
                "(" + Constants.KEY_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_PLATE_NO + " TEXT," +
                Constants.KEY_CAR_BRAND + " TEXT," +
                Constants.KEY_CAR_BRAND_ID + " INTEGER," +
                Constants.KEY_CAR_MODEL + " TEXT," +
                Constants.KEY_CAR_PRICE + " DOUBLE," +
                Constants.KEY_CAR_IMAGE + " BLOB NOT NULL," +
                Constants.KEY_CAR_OWNER_TYPE + " TEXT," +
                Constants.KEY_CAR_SHORTDESCRRIPTION + " TEXT," +
                Constants.KEY_CAR_STATUS + " INTEGER," +
                Constants.KEY_CAR_QUANTITY + " INTEGER);";

        /*Here we create the carBrand Table*/
        String CREATE_CAR_BRAND_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CAR_BRAND +
                "(" + Constants.KEY_BRAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_CAR_BRAND_NAME + " TEXT," +
                Constants.KEY_CAR_BRAND_STATUS + " INTEGER);";

        /*Here we create the cart table*/
        String CREATE_CART_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CART +
                "(" + Constants.KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_USER_ID + " INTEGER," +
                Constants.KEY_USER_EMAIL + " TEXT," +
                Constants.KEY_USER_PASSWORD + " TEXT," +
                Constants.KEY_CAR_ID_FOR_CART + " TEXT," +
                Constants.KEY_CAR_BRAND_FOR_CART + " TEXT," +
                Constants.KEY_CAR_MODEL_FOR_CART + " TEXT," +
                Constants.KEY_CAR_IMAGE_FOR_CART + " BLOB NOT NULL," +
                Constants.KEY_CAR_PRICE_FOR_CART + " DOUBLE," +
                Constants.KEY_CAR_QUANTITY_FOR_CART + " INTEGER," +
                Constants.KEY_CAR_TOTAL_PRICE_FOR_CART + " DOUBLE);";

        /*Here we create the order_status Table*/
        String CREATE_ORDER_STATUS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_ORDER_STATUS +
                "(" + Constants.KEY_ORDER_STATUS_ID + " INTEGER PRIMARY KEY," +
                Constants.KEY_ORDER_STATUS_NAME + " TEXT);";

        /*Here we create the order table*/
        String CREATE_ORDER_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_ORDER +
                "(" + Constants.KEY_ID_FOR_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_USER_ID_FOR_ORDER + " INTEGER," +
                Constants.KEY_USER_ADDRESS_FOR_ORDER + " TEXT," +
                Constants.KEY_USER_CITY_FOR_ORDER + " TEXT," +
                Constants.KEY_PINCODE_FOR_ORDER + " INTEGER," +
                Constants.KEY_PAYMENT_TYPE_FOR_ORDER + " TEXT," +
                Constants.KEY_TOTAL_PRICE_FOR_ORDER + " DOUBLE," +
                Constants.KEY_PAYMENT_STATUS_FOR_ORDER + " TEXT," +
                Constants.KEY_ORDER_STATUS_FOR_ORDER + " INTEGER," +
                Constants.KEY_ADDED_ON_FOR_ORDER + " TEXT);";

        /*Here we create the order table*/
        String CREATE_ORDER_DETAILS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_ORDER_DETAILS +
                "(" + Constants.KEY_ID_FOR_ORDER_DETAILS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_ORDER_ID_FOR_ORDER_DETAILS + " INTEGER," +
                Constants.KEY_PRODUCT_ID_FOR_ORDER_DETAILS + " INTEGER," +
                Constants.KEY_QUANTITY_FOR_ORDER_DETAILS + " INTEGER," +
                Constants.KEY_PRICE_FOR_ORDER_DETAILS + " DOUBLE," +
                Constants.KEY_ADDED_ON_FOR_ORDER_DETAILS + " TEXT);";

        /*Here we create the wishlist table*/
        String CREATE_WISHLIST_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_WISHLIST +
                "(" + Constants.KEY_ID_FOR_WISHLIST + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_USER_ID_FOR_WISHLIST + " INTEGER," +
                Constants.KEY_CAR_ID_FOR_WISHLIST + " INTEGER," +
                Constants.KEY_ADDED_ON_FOR_WISHLIST + " TEXT);";

        /*Here we create the Car Parts table*/
        String CREATE_CAR_PARTS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CAR_PARTS +
                "(" + Constants.KEY_ID_FOR_CAR_PARTS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_CAR_PARTS_NAME + " TEXT," +
                Constants.KEY_QUANTITY_FOR_CAR_PARTS + " INTEGER," +
                Constants.KEY_PRICE_FOR_CAR_PARTS + " DOUBLE," +
                Constants.KEY_VOUCHER_FOR_CAR_PARTS + " TEXT," +
                Constants.KEY_IMAGE_FOR_CAR_PARTS + " BLOB NOT NULL);";

        /*Here we create the Contact_US table*/
        String CREATE_CONTACT_US_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CONTACT_US +
                "(" + Constants.KEY_ID_FOR_CONTACT_US + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.KEY_NAME_FOR_CONTACT_US + " TEXT," +
                Constants.KEY_EMAIL_FOR_CONTACT_US + " TEXT," +
                Constants.KEY_MOBILE_FOR_CONTACT_US + " INTEGER," +
                Constants.KEY_COMMENT_FOR_CONTACT_US + " TEXT," +
                Constants.KEY_ADDED_ON_FOR_CONTACT_US + " TEXT);";






        /*Now execute the query */
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_CAR_TABLE); //PRODUCT TABLE
        db.execSQL(CREATE_CAR_BRAND_TABLE); //CATEGORY TABLE
        db.execSQL(CREATE_CART_TABLE); //CART TABLE
        db.execSQL(CREATE_ORDER_STATUS_TABLE); //ORDERSTATUS TABLE
        db.execSQL(CREATE_ORDER_TABLE); //ORDER TABLE
        db.execSQL(CREATE_ORDER_DETAILS_TABLE); //ORDER TABLE
        db.execSQL(CREATE_WISHLIST_TABLE); //WISHLIST TABLE
        db.execSQL(CREATE_CAR_PARTS_TABLE); //CAR PARTS TABLE
        db.execSQL(CREATE_CONTACT_US_TABLE); //CONTACT US TABLE

        /*Fill up the orderStatus table with static data*/
        addOrderStatus(1, "pending", db);
        addOrderStatus(2, "processing", db);
        addOrderStatus(3, "shipped", db);
        addOrderStatus(4, "canceled", db);
        addOrderStatus(5, "complete", db);

        /*onUpgrade(db, 1, Constants.DB_VERSION);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CAR);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CAR_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CART);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_ORDER_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_ORDER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CAR_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CONTACT_US);


        onCreate(db);

    }

    public void addOrderStatus(int statusId, String statusName, SQLiteDatabase db) {

//        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_ORDER_STATUS_ID, statusId);
        values.put(Constants.KEY_ORDER_STATUS_NAME, statusName);

        long result = db.insert(Constants.TABLE_NAME_ORDER_STATUS, null, values);

        if (result == -1) {
            Toast.makeText(context, "Insertion of orderStatus is failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "orderStatus's added successfully", Toast.LENGTH_SHORT).show();
        }

        /*Now print a Toast message */
    }

    //CRUD operation
    public boolean addCustomer(Customer customer) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_EMAIL, customer.getEmail());
        values.put(Constants.KEY_CUSTOMER_NAME, customer.getCustomerName());
        values.put(Constants.KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(Constants.KEY_ADDRESS, customer.getAddress());
        /*values.put(Constants.KEY_DATE_OF_BIRTH, customer.getDateOfBirth());*/
        values.put(Constants.KEY_PASSWORD, customer.getPassword());

        long result = db.insert(Constants.TABLE_NAME_CUSTOMER, null, values);

        if (result == -1) {
            Toast.makeText(context, "Insertion of customer is failed", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            Toast.makeText(context, "Customer added successfully", Toast.LENGTH_SHORT).show();
            return true;
        }

        /*Now print a Toast message */

    }

    /*Method for getting all the customers*/
    @SuppressLint("Range")
    public List<Customer> getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<Customer> customerList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CUSTOMER,
                new String[]{Constants.KEY_CUSTOMER_ID,
                        Constants.KEY_EMAIL,
                        Constants.KEY_CUSTOMER_NAME,
                        Constants.KEY_PHONE_NUMBER,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_PASSWORD},
                null, null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_CUSTOMER_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CUSTOMER_ID))));
                customer.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
                customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CUSTOMER_NAME)));
                customer.setPhoneNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE_NUMBER))));
                customer.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));
                customer.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));

                //Convert Timestamp into something readable
/*                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                        .getTime()); //Feb 23, 2020
                item.setDateItemAdded(formattedDate);*/

                //Add the item object to the itemList ArrayList
                customerList.add(customer);

            } while (cursor.moveToNext());


        }
        /*cursor.close();*/
        //Return the ArrayList containing all the items
        return customerList;
    }

    /*Method for getting the specific customer based on Customer Email*/
    @SuppressLint("Range")
    public Customer getCustomer(String email) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CUSTOMER,
                new String[]{Constants.KEY_CUSTOMER_ID,
                        Constants.KEY_EMAIL,
                        Constants.KEY_CUSTOMER_NAME,
                        Constants.KEY_PHONE_NUMBER,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_PASSWORD},
                Constants.KEY_EMAIL + "=?",
                new String[]{String.valueOf(email)},
                null, null, null);

        //Checking if the resultset is empty
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
        Log.d("DataBase Handler class ", "getCustomer: " + cursor);


        Customer customer = new Customer();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DataBase Handler class ", "getCustomer: " + cursor + " Is moved to first ? " + cursor.moveToFirst());
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CUSTOMER_ID)));
            customer.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
            customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CUSTOMER_NAME)));
            customer.setPhoneNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE_NUMBER))));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));
            customer.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/
        }
        /*cursor.close();*/
        return customer;
    }

    /*Method for deleting the customer based on the customer email*/
    public void deleteCustomer(String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME_CUSTOMER,
                Constants.KEY_EMAIL + "=?",
                new String[]{customerEmail});

        //Close the db connection
        db.close();
    }

    /*Method for getting the customer unique id based on email*/
    @SuppressLint("Range")
    public Customer getCustomerBasedOnId(int id) {
        /*Instantiate the database*/
        Log.d("DB Helper", "getCustomerBasedOnId: " + id);
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CUSTOMER,
                new String[]{Constants.KEY_CUSTOMER_ID,
                        Constants.KEY_EMAIL,
                        Constants.KEY_CUSTOMER_NAME,
                        Constants.KEY_PHONE_NUMBER,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_PASSWORD},
                Constants.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        //Checking if the resultset is empty
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        try {
//            cursor.moveToFirst();
//            Log.d("DataBaseHandler Class", "getCustomerBasedOnId: " + cursor.getString(0));
//        } catch (NullPointerException ex) {
//            Log.d("DB HElper", "getCustomerBasedOnId: " + ex.getMessage());
//        }


        Customer customer = new Customer();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DataBaseHandler Class", "getCustomerBasedOnId: " + cursor + "Is Moved To first ? " + cursor.moveToFirst());
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CUSTOMER_ID)));
            customer.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
            customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CUSTOMER_NAME)));
            customer.setPhoneNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE_NUMBER))));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));
            customer.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/
        }
        /*cursor.close();*/
        return customer;
    }

    /*Method for updating the customer name*/
    public long updateCustomerName(Customer customer, String updatedName, int loggedID){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CUSTOMER_ID, customer.getCustomerId());
        values.put(Constants.KEY_EMAIL, customer.getEmail());
        values.put(Constants.KEY_CUSTOMER_NAME, updatedName);
        values.put(Constants.KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(Constants.KEY_ADDRESS, customer.getAddress());
        values.put(Constants.KEY_PASSWORD, customer.getPassword());

        return db.update(Constants.TABLE_NAME_CUSTOMER, values,
                Constants.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(loggedID)});
    }

    @SuppressLint("Range")
    public String getLoggedUserCurrentPassword(int loggedId){
        SQLiteDatabase db = this.getReadableDatabase();

        String currentPassword = null;
        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CUSTOMER,
                new String[]{Constants.KEY_PASSWORD},
                Constants.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(loggedId)},
                null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            currentPassword = cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD));
        }

        return currentPassword;
    }

    public long updateLoggedUserPassword(Customer customer, int loggedUserID, String changedNewPassword){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CUSTOMER_ID, customer.getCustomerId());
        values.put(Constants.KEY_EMAIL, customer.getEmail());
        values.put(Constants.KEY_CUSTOMER_NAME, customer.getCustomerName());
        values.put(Constants.KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(Constants.KEY_ADDRESS, customer.getAddress());
        values.put(Constants.KEY_PASSWORD, changedNewPassword);

        return db.update(Constants.TABLE_NAME_CUSTOMER, values,
                Constants.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(loggedUserID)});
    }

    public long resetPassword(Customer user, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CUSTOMER_ID, user.getCustomerId());
        values.put(Constants.KEY_EMAIL, user.getEmail());
        values.put(Constants.KEY_CUSTOMER_NAME, user.getCustomerName());
        values.put(Constants.KEY_PHONE_NUMBER, user.getPhoneNumber());
        values.put(Constants.KEY_ADDRESS, user.getAddress());
        values.put(Constants.KEY_PASSWORD, newPassword);

        return db.update(Constants.TABLE_NAME_CUSTOMER, values,
                Constants.KEY_CUSTOMER_ID + "=?",
                new String[]{String.valueOf(user.getCustomerId())});
    }

    //CRUD operation
    public boolean addAdmin(Admin admin) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_EMAIL_ADMIN, admin.getAdminEmail());
        values.put(Constants.KEY_ADMIN_NAME, admin.getAdminName());
        values.put(Constants.KEY_PHONE_NUMBER_ADMIN, admin.getPhoneNumber());
        values.put(Constants.KEY_ADDRESS_ADMIN, admin.getAddress());
        /*values.put(Constants.KEY_DATE_OF_BIRTH, customer.getDateOfBirth());*/
        values.put(Constants.KEY_PASSWORD_ADMIN, admin.getPassword());

        long result = db.insert(Constants.TABLE_NAME_ADMIN, null, values);

        if (result == -1) {
            Toast.makeText(context, "Insertion of admin is failed", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            Toast.makeText(context, "admin added successfully", Toast.LENGTH_SHORT).show();
            return true;
        }

        /*Now print a Toast message */

    }

    /*Method for adding new cars*/
    public void addCar(Car car, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_PLATE_NO, car.getPlate_no());
        values.put(Constants.KEY_CAR_BRAND, car.getBrand());
        values.put(Constants.KEY_CAR_BRAND_ID, car.getBrandId());
        values.put(Constants.KEY_CAR_MODEL, car.getModel());
        values.put(Constants.KEY_CAR_PRICE, car.getPrice());
        /*For inserting image, we pass byte data*/
        values.put(Constants.KEY_CAR_IMAGE, imageBytes);
        values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
        values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
        values.put(Constants.KEY_CAR_STATUS, car.getStatus());
        values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());

        db.insert(Constants.TABLE_NAME_CAR, null, values);
    }

    /*Method for getting all cars*/
    @SuppressLint("Range")
    public List<Car> getAllActiveCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<Car> carList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CAR,
                new String[]{Constants.KEY_CAR_ID,
                        Constants.KEY_PLATE_NO,
                        Constants.KEY_CAR_BRAND,
                        Constants.KEY_CAR_BRAND_ID,
                        Constants.KEY_CAR_MODEL,
                        Constants.KEY_CAR_PRICE,
                        Constants.KEY_CAR_IMAGE,
                        Constants.KEY_CAR_OWNER_TYPE,
                        Constants.KEY_CAR_SHORTDESCRRIPTION,
                        Constants.KEY_CAR_STATUS,
                        Constants.KEY_CAR_QUANTITY},
                Constants.KEY_CAR_STATUS +"=?", new String[]{String.valueOf(1)}, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_CAR_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_ID))));
                car.setPlate_no(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLATE_NO)));
                car.setBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND)));
                car.setBrandId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_ID)));
                car.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL)));
                car.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE)));
                /*car.setCarImage(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_IMAGE)));*/
                car.setCarImage(cursor.getBlob(6));
                car.setOwner(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_OWNER_TYPE)));
                car.setShortDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_SHORTDESCRRIPTION)));
                car.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_STATUS)));
                car.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY)));

                //Convert Timestamp into something readable
/*                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                        .getTime()); //Feb 23, 2020
                item.setDateItemAdded(formattedDate);*/

                //Add the item object to the itemList ArrayList
                carList.add(car);

            } while (cursor.moveToNext());


        }
        /*cursor.close();*/
        //Return the ArrayList containing all the items
        return carList;
    }

    /*Method for getting all cars*/
    @SuppressLint("Range")
    public List<Car> getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<Car> carList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CAR,
                new String[]{Constants.KEY_CAR_ID,
                        Constants.KEY_PLATE_NO,
                        Constants.KEY_CAR_BRAND,
                        Constants.KEY_CAR_BRAND_ID,
                        Constants.KEY_CAR_MODEL,
                        Constants.KEY_CAR_PRICE,
                        Constants.KEY_CAR_IMAGE,
                        Constants.KEY_CAR_OWNER_TYPE,
                        Constants.KEY_CAR_SHORTDESCRRIPTION,
                        Constants.KEY_CAR_STATUS,
                        Constants.KEY_CAR_QUANTITY},
                null, null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_CAR_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_ID))));
                car.setPlate_no(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLATE_NO)));
                car.setBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND)));
                car.setBrandId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_ID)));
                car.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL)));
                car.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE)));
                /*car.setCarImage(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_IMAGE)));*/
                car.setCarImage(cursor.getBlob(6));
                car.setOwner(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_OWNER_TYPE)));
                car.setShortDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_SHORTDESCRRIPTION)));
                car.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_STATUS)));
                car.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY)));

                //Convert Timestamp into something readable
/*                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                        .getTime()); //Feb 23, 2020
                item.setDateItemAdded(formattedDate);*/

                //Add the item object to the itemList ArrayList
                carList.add(car);

            } while (cursor.moveToNext());


        }
        /*cursor.close();*/
        //Return the ArrayList containing all the items
        return carList;
    }

    public byte[] retrieveImageFromDB(SQLiteDatabase mDB) {
        Cursor cur = mDB.query(true, Constants.TABLE_NAME_CAR, new String[]{Constants.KEY_CAR_IMAGE,},
                null, null, null, null, Constants.KEY_CAR_ID + " DESC", "1");
        if (cur.moveToFirst()) {
            @SuppressLint("Range") byte[] blob = cur.getBlob(cur.getColumnIndex(Constants.KEY_CAR_IMAGE));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }


    /*Method for getting specific car based on car id*/
    @SuppressLint("Range")
    public Car getCar(int id) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CAR,
                new String[]{Constants.KEY_CAR_ID,
                        Constants.KEY_PLATE_NO,
                        Constants.KEY_CAR_BRAND,
                        Constants.KEY_CAR_BRAND_ID,
                        Constants.KEY_CAR_MODEL,
                        Constants.KEY_CAR_PRICE,
                        Constants.KEY_CAR_IMAGE,
                        Constants.KEY_CAR_OWNER_TYPE,
                        Constants.KEY_CAR_SHORTDESCRRIPTION,
                        Constants.KEY_CAR_STATUS,
                        Constants.KEY_CAR_QUANTITY},
                Constants.KEY_CAR_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Car car = new Car();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            car.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID)));
            car.setPlate_no(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLATE_NO)));
            car.setBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND)));
            car.setBrandId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_ID)));
            car.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL)));
            car.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE)));
            car.setCarImage(cursor.getBlob(6));
            car.setOwner(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_OWNER_TYPE)));
            car.setShortDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_SHORTDESCRRIPTION)));
            car.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_STATUS)));
            car.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return car;
    }

    @SuppressLint("Range")
    public Car getCar(String carModelName) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CAR,
                new String[]{Constants.KEY_CAR_ID,
                        Constants.KEY_PLATE_NO,
                        Constants.KEY_CAR_BRAND,
                        Constants.KEY_CAR_BRAND_ID,
                        Constants.KEY_CAR_MODEL,
                        Constants.KEY_CAR_PRICE,
                        Constants.KEY_CAR_IMAGE,
                        Constants.KEY_CAR_OWNER_TYPE,
                        Constants.KEY_CAR_SHORTDESCRRIPTION,
                        Constants.KEY_CAR_STATUS,
                        Constants.KEY_CAR_QUANTITY},
                Constants.KEY_CAR_MODEL + "=?",
                new String[]{carModelName},
                null, null, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Car car = new Car();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            car.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID)));
            car.setPlate_no(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLATE_NO)));
            car.setBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND)));
            car.setBrandId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_ID)));
            car.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL)));
            car.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE)));
            car.setCarImage(cursor.getBlob(6));
            car.setOwner(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_OWNER_TYPE)));
            car.setShortDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_SHORTDESCRRIPTION)));
            car.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_STATUS)));
            car.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return car;
    }

    /*Method for updating car product*/
    public int updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_PLATE_NO, car.getPlate_no());
        values.put(Constants.KEY_CAR_MODEL, car.getModel());
        values.put(Constants.KEY_CAR_PRICE, car.getPrice());
        values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
        values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
        values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());

        return db.update(Constants.TABLE_NAME_CAR, values,
                Constants.KEY_CAR_ID + "=?",
                new String[]{String.valueOf(car.getId())});

    }

    public Cursor getCarStatus(Car car) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(Constants.TABLE_NAME_CAR,
                new String[]{Constants.KEY_CAR_STATUS},
                Constants.KEY_CAR_ID + "=?",
                new String[]{String.valueOf(car.getId())},
                null, null, null, null);
    }

    /*Update Car status*/
    public int updateCarStatus(String status, Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (status.equals("Active")) {
            ContentValues values = new ContentValues();
            /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
            values.put(Constants.KEY_CAR_STATUS, 1);
            /*        values.put(Constants.KEY_CAR_MODEL, car.getModel());
                    values.put(Constants.KEY_CAR_PRICE, car.getPrice());
                    values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
                    values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
                    values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());*/

            return db.update(Constants.TABLE_NAME_CAR, values,
                    Constants.KEY_CAR_ID + "=?",
                    new String[]{String.valueOf(car.getId())});
        } else {
            ContentValues values = new ContentValues();
            /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
            values.put(Constants.KEY_CAR_STATUS, 0);
            /*        values.put(Constants.KEY_CAR_MODEL, car.getModel());
                    values.put(Constants.KEY_CAR_PRICE, car.getPrice());
                    values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
                    values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
                    values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());*/

            return db.update(Constants.TABLE_NAME_CAR, values,
                    Constants.KEY_CAR_ID + "=?",
                    new String[]{String.valueOf(car.getId())});
        }

    }

    /*Method for getting count of cars in the database*/
    /*Method for deleting the car based on car id*/
    public void deleteCar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME_CAR,
                Constants.KEY_CAR_ID + "=?",
                new String[]{String.valueOf(id)});

        //Close the db connection
        db.close();
    }

    /*Method for adding new carBrands*/
    public void addCarBrand(CarBrand carBrand) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CAR_BRAND_NAME, carBrand.getCarBrandName());
        values.put(Constants.KEY_CAR_BRAND_STATUS, carBrand.getStatus());

        db.insert(Constants.TABLE_NAME_CAR_BRAND, null, values);
    }

    /*Method for getting all carBrands*/
    @SuppressLint("Range")
    public List<CarBrand> getAllCarBrands() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<CarBrand> carBrandList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CAR_BRAND,
                new String[]{Constants.KEY_BRAND_ID,
                        Constants.KEY_CAR_BRAND_NAME,
                        Constants.KEY_CAR_BRAND_STATUS},
                null, null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_BRAND_ID + " DESC");


        if (cursor.moveToFirst()) {
            do {
                CarBrand carBrand = new CarBrand();
                carBrand.setCarBrandId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_BRAND_ID))));
                carBrand.setCarBrandName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_NAME)));
                carBrand.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_STATUS))));

                //Add the category object to the categoryList ArrayList
                carBrandList.add(carBrand);

            } while (cursor.moveToNext());


        }

        //Return the ArrayList containing all the items
        return carBrandList;
    }

    /*Method for getting all active carBrand Names*/
    @SuppressLint("Range")
    public Cursor getAllActiveCarBrandNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<CarBrand> activeCarBrandList = new ArrayList<>();

        return db.query(Constants.TABLE_NAME_CAR_BRAND,
                new String[]{Constants.KEY_BRAND_ID,
                        Constants.KEY_CAR_BRAND_NAME,
                        Constants.KEY_CAR_BRAND_STATUS},
                Constants.KEY_CAR_BRAND_STATUS + "= 1", null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_BRAND_ID + " DESC");
        /*String GET_ALL_ACTIVE_CAR_BRAND = "SELECT * FROM "+Constants.TABLE_NAME_CAR_BRAND +"WHERE "+Constants.KEY_CAR_STATUS +" = 1;";*/

//        Cursor cursor = db.rawQuery(GET_ALL_ACTIVE_CAR_BRAND, null);
    }

    /*Get the specific car brand id based on car brand name*/
    public Cursor getActiveCarBrandId(String carBrandName) {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<CarBrand> activeCarBrandList = new ArrayList<>();

        return db.query(Constants.TABLE_NAME_CAR_BRAND,
                new String[]{Constants.KEY_BRAND_ID},
                Constants.KEY_CAR_BRAND_NAME + "=?", new String[]{carBrandName}, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                null);
    }

    /*Method for getting specific carBrand based on carBrand id*/
    @SuppressLint("Range")
    public CarBrand getCarBrand(int carBrandId) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CAR_BRAND,
                new String[]{Constants.KEY_BRAND_ID,
                        Constants.KEY_CAR_BRAND_NAME,
                        Constants.KEY_CAR_BRAND_STATUS},
                Constants.KEY_BRAND_ID + "=?",
                new String[]{String.valueOf(carBrandId)},
                null, null, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        CarBrand carBrand = new CarBrand();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            carBrand.setCarBrandId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_BRAND_ID))));
            carBrand.setCarBrandName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_NAME)));
            carBrand.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_STATUS))));


        }
        return carBrand;
    }

    /*Method for upadating the car Brand Status*/
    public int updateCarBrandStatus(String status, CarBrand carBrand) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (status.equals("Active")) {
            ContentValues values = new ContentValues();
            /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
            values.put(Constants.KEY_CAR_BRAND_STATUS, 1);
            /*        values.put(Constants.KEY_CAR_MODEL, car.getModel());
                    values.put(Constants.KEY_CAR_PRICE, car.getPrice());
                    values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
                    values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
                    values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());*/

            return db.update(Constants.TABLE_NAME_CAR_BRAND, values,
                    Constants.KEY_BRAND_ID + "=?",
                    new String[]{String.valueOf(carBrand.getCarBrandId())});
        } else {
            ContentValues values = new ContentValues();
            /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
            values.put(Constants.KEY_CAR_BRAND_STATUS, 0);
            /*        values.put(Constants.KEY_CAR_MODEL, car.getModel());
                    values.put(Constants.KEY_CAR_PRICE, car.getPrice());
                    values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
                    values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
                    values.put(Constants.KEY_CAR_QUANTITY, car.getQuantity());*/

            return db.update(Constants.TABLE_NAME_CAR_BRAND, values,
                    Constants.KEY_BRAND_ID + "=?",
                    new String[]{String.valueOf(carBrand.getCarBrandId())});
        }
    }

    /*Method for updating carBrand*/
    public int updateCarBrand(CarBrand carBrand) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CAR_BRAND_NAME, carBrand.getCarBrandName());
        /*values.put(Constants.KEY_CAR_BRAND_STATUS, carBrand.getStatus());*/

        return db.update(Constants.TABLE_NAME_CAR_BRAND, values,
                Constants.KEY_BRAND_ID + "=?",
                new String[]{String.valueOf(carBrand.getCarBrandId())});
    }

    /*Method for getting count of carBrands in the database*/
    /*Method for deleting the carBrand based on car id*/
    public void deleteCarBrand(int carBrandId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME_CAR_BRAND,
                Constants.KEY_BRAND_ID + "=?",
                new String[]{String.valueOf(carBrandId)});

        //Close the db connection
        db.close();
    }


    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + Constants.TABLE_NAME_CUSTOMER + " where " + Constants.KEY_CUSTOMER_NAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + Constants.TABLE_NAME_CUSTOMER + " where " + Constants.KEY_CUSTOMER_NAME + " = ? and " + Constants.KEY_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkadminname(String adminname) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + Constants.TABLE_NAME_ADMIN + " where " + Constants.KEY_ADMIN_NAME + " = ?", new String[]{adminname});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkadminnamepassword(String adminname, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + Constants.TABLE_NAME_ADMIN + " where " + Constants.KEY_ADMIN_NAME + " = ? and " + Constants.KEY_PASSWORD_ADMIN + " = ?", new String[]{adminname, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public Cursor getCarBrandStatus(CarBrand carBrand) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(Constants.TABLE_NAME_CAR_BRAND,
                new String[]{Constants.KEY_CAR_BRAND_STATUS},
                Constants.KEY_BRAND_ID + "=?",
                new String[]{String.valueOf(carBrand.getCarBrandId())},
                null, null, null, null);
    }

    public long addCarsToLoggedUserCart(Car clickedCar, Customer loggedCustomer, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        /*This quantity is the quantity selected by the user in the spinner
         * it is multiplied with the unit price so that we get the total price*/
        double totalPrice = clickedCar.getPrice() * quantity;

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_USER_ID, loggedCustomer.getCustomerId());
        values.put(Constants.KEY_USER_EMAIL, loggedCustomer.getEmail());
        values.put(Constants.KEY_USER_PASSWORD, loggedCustomer.getPassword());
        values.put(Constants.KEY_CAR_ID_FOR_CART, clickedCar.getId());
        values.put(Constants.KEY_CAR_BRAND_FOR_CART, clickedCar.getBrand());
        values.put(Constants.KEY_CAR_MODEL_FOR_CART, clickedCar.getModel());
        values.put(Constants.KEY_CAR_IMAGE_FOR_CART, clickedCar.getCarImage());
        values.put(Constants.KEY_CAR_PRICE_FOR_CART, clickedCar.getPrice());
        values.put(Constants.KEY_CAR_QUANTITY, quantity);
        values.put(Constants.KEY_CAR_TOTAL_PRICE_FOR_CART, totalPrice);

        return db.insert(Constants.TABLE_NAME_CART, null, values);

    }

    @SuppressLint("Range")
    public List<Cart> getAllCartItemBasedOnLoggedUserId(int loggedUserId) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cart> cartList = new ArrayList<>();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CART,
                new String[]{Constants.KEY_CART_ID,
                        Constants.KEY_USER_ID,
                        Constants.KEY_USER_EMAIL,
                        Constants.KEY_USER_PASSWORD,
                        Constants.KEY_CAR_ID_FOR_CART,
                        Constants.KEY_CAR_BRAND_FOR_CART,
                        Constants.KEY_CAR_MODEL_FOR_CART,
                        Constants.KEY_CAR_IMAGE_FOR_CART,
                        Constants.KEY_CAR_PRICE_FOR_CART,
                        Constants.KEY_CAR_QUANTITY_FOR_CART,
                        Constants.KEY_CAR_TOTAL_PRICE_FOR_CART},
                Constants.KEY_USER_ID + "=?",
                new String[]{String.valueOf(loggedUserId)},
                null, null, Constants.KEY_CAR_BRAND_FOR_CART + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setCartId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CART_ID)));
                cart.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID))));
                cart.setUserEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_EMAIL)));
                cart.setUserPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_PASSWORD)));
                cart.setCarId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID_FOR_CART)));
                cart.setCarBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_FOR_CART)));
                cart.setCarModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL_FOR_CART)));
                cart.setCarImage(cursor.getBlob(7));
                cart.setUnitPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_PRICE_FOR_CART))));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY_FOR_CART)));
                cart.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_TOTAL_PRICE_FOR_CART)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/
                cartList.add(cart);

            } while (cursor.moveToNext());
        }
        /*cursor.close();*/
        return cartList;
    }

    /*Remove the Cart item based on the Logged User Id and the car ID */
    public void deleteLoggedUsersCartItem(int loggedUserId, int carId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME_CART,
                Constants.KEY_USER_ID + "=? AND " + Constants.KEY_CAR_ID_FOR_CART + "=?",
                new String[]{String.valueOf(loggedUserId), String.valueOf(carId)});

        //Close the db connection
        db.close();
    }

    public long updateLoggedUserCarItemQuantityAfterOrder(Car car, int loggedUserID, int carID, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_PLATE_NO, car.getPlate_no());
        values.put(Constants.KEY_CAR_BRAND, car.getBrand());
        values.put(Constants.KEY_CAR_MODEL, car.getModel());
        values.put(Constants.KEY_CAR_PRICE, car.getPrice());
        values.put(Constants.KEY_CAR_OWNER_TYPE, car.getOwner());
        values.put(Constants.KEY_CAR_IMAGE, car.getCarImage());
        values.put(Constants.KEY_CAR_SHORTDESCRRIPTION, car.getShortDescription());
        values.put(Constants.KEY_CAR_ID, car.getId());
        values.put(Constants.KEY_CAR_BRAND_ID, car.getBrandId());
        values.put(Constants.KEY_CAR_STATUS, car.getStatus());
        values.put(Constants.KEY_CAR_QUANTITY, quantity);

        return db.update(Constants.TABLE_NAME_CAR, values,
                Constants.KEY_CAR_ID + "=?",
                new String[]{String.valueOf(carID)});
    }


    @SuppressLint("Range")
    public Cart getCartItemBasedOnLoggedUsersId(int loggedUserId) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CART,
                new String[]{Constants.KEY_CART_ID,
                        Constants.KEY_USER_ID,
                        Constants.KEY_USER_EMAIL,
                        Constants.KEY_USER_PASSWORD,
                        Constants.KEY_CAR_ID_FOR_CART,
                        Constants.KEY_CAR_BRAND_FOR_CART,
                        Constants.KEY_CAR_MODEL_FOR_CART,
                        Constants.KEY_CAR_IMAGE_FOR_CART,
                        Constants.KEY_CAR_PRICE_FOR_CART,
                        Constants.KEY_CAR_QUANTITY_FOR_CART,
                        Constants.KEY_CAR_TOTAL_PRICE_FOR_CART},
                Constants.KEY_USER_ID + "=?",
                new String[]{String.valueOf(loggedUserId)},
                null, null, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Cart cart = new Cart();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            cart.setCartId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CART_ID)));
            cart.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID))));
            cart.setUserEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_EMAIL)));
            cart.setUserPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_PASSWORD)));
            cart.setCarId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID_FOR_CART)));
            cart.setCarBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_FOR_CART)));
            cart.setCarModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL_FOR_CART)));
            cart.setCarImage(cursor.getBlob(7));
            cart.setUnitPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_PRICE_FOR_CART))));
            cart.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY_FOR_CART)));
            cart.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_TOTAL_PRICE_FOR_CART)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return cart;

    }

    @SuppressLint("Range")
    public Cart getCartItemBasedOnCarId(int carId) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CART,
                new String[]{Constants.KEY_CART_ID,
                        Constants.KEY_USER_ID,
                        Constants.KEY_USER_EMAIL,
                        Constants.KEY_USER_PASSWORD,
                        Constants.KEY_CAR_ID_FOR_CART,
                        Constants.KEY_CAR_BRAND_FOR_CART,
                        Constants.KEY_CAR_MODEL_FOR_CART,
                        Constants.KEY_CAR_IMAGE_FOR_CART,
                        Constants.KEY_CAR_PRICE_FOR_CART,
                        Constants.KEY_CAR_QUANTITY_FOR_CART,
                        Constants.KEY_CAR_TOTAL_PRICE_FOR_CART},
                Constants.KEY_CAR_ID_FOR_CART + "=?",
                new String[]{String.valueOf(carId)},
                null, null, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Cart cart = new Cart();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            cart.setCartId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CART_ID)));
            cart.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID))));
            cart.setUserEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_EMAIL)));
            cart.setUserPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_PASSWORD)));
            cart.setCarId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID_FOR_CART)));
            cart.setCarBrand(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_BRAND_FOR_CART)));
            cart.setCarModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL_FOR_CART)));
            cart.setCarImage(cursor.getBlob(7));
            cart.setUnitPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_PRICE_FOR_CART))));
            cart.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_QUANTITY_FOR_CART)));
            cart.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_TOTAL_PRICE_FOR_CART)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return cart;

    }



    public long addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_USER_ID_FOR_ORDER, order.getUserId());
        values.put(Constants.KEY_USER_ADDRESS_FOR_ORDER, order.getAddress());
        values.put(Constants.KEY_USER_CITY_FOR_ORDER, order.getCity());
        values.put(Constants.KEY_PINCODE_FOR_ORDER, order.getPincode());
        values.put(Constants.KEY_PAYMENT_TYPE_FOR_ORDER, order.getPaymentType());
        values.put(Constants.KEY_TOTAL_PRICE_FOR_ORDER, order.getTotalPrice());
        values.put(Constants.KEY_PAYMENT_STATUS_FOR_ORDER, order.getPaymentStatus());
        values.put(Constants.KEY_ORDER_STATUS_FOR_ORDER, order.getOrderStatus());
        values.put(Constants.KEY_ADDED_ON_FOR_ORDER, order.getAddedOn());


        return db.insert(Constants.TABLE_NAME_ORDER, null, values);
    }

    public void updateStatusValueBasedOnOrderId(int orderID, int updateOrderStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE " + Constants.TABLE_NAME_ORDER + " SET " + Constants.KEY_ORDER_STATUS_FOR_ORDER
                + " = " + updateOrderStatus + " where " + Constants.KEY_ID_FOR_ORDER + " = " + orderID;
        /*"update `order` set order_status='$update_order_status',payment_status='Success' where id='$order_id'"*/
        // execute the sql statement.
        db.execSQL(sql);

    }

    @SuppressLint("Range")
    public Order getOrderBasedOnSelectedOrderedID(int orderID) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        /*"select * from `order` where id='$order_id'"*/
        String sql = "SELECT * FROM " + Constants.TABLE_NAME_ORDER + " WHERE " + Constants.KEY_ID_FOR_ORDER + " = " + orderID;

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Order order = new Order();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            order.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER)));
            order.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID_FOR_ORDER))));
            order.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ADDRESS_FOR_ORDER)));
            order.setCity(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_CITY_FOR_ORDER)));
            order.setPincode(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PINCODE_FOR_ORDER))));
            order.setPaymentType(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_TYPE_FOR_ORDER)));
            order.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_TOTAL_PRICE_FOR_ORDER)));
            order.setPaymentStatus(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_STATUS_FOR_ORDER)));
            order.setOrderStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_FOR_ORDER)));
            order.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return order;
    }

    @SuppressLint("Range")
    public Order getOrderBasedOnSelectedLoggedUserID(int UserId) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        /*"select * from `order` where id='$order_id'"*/
        String sql = "SELECT * FROM " + Constants.TABLE_NAME_ORDER + " WHERE " + Constants.KEY_USER_ID_FOR_ORDER + " = " + UserId;

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Order order = new Order();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            order.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER)));
            order.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID_FOR_ORDER))));
            order.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ADDRESS_FOR_ORDER)));
            order.setCity(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_CITY_FOR_ORDER)));
            order.setPincode(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PINCODE_FOR_ORDER))));
            order.setPaymentType(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_TYPE_FOR_ORDER)));
            order.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_TOTAL_PRICE_FOR_ORDER)));
            order.setPaymentStatus(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_STATUS_FOR_ORDER)));
            order.setOrderStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_FOR_ORDER)));
            order.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return order;
    }

    public List<String> getAllStatusNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + Constants.TABLE_NAME_ORDER_STATUS;
        // execute the sql statement.
        Cursor cursor = db.rawQuery(sql, null);

        List<String> statusNamesList = new ArrayList<>();


        // iterate over the results
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String statusName = cursor.getString(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_NAME));
                statusNamesList.add(statusName);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return statusNamesList;

    }

    @SuppressLint("Range")
    public String getOrderStatusBasedOnSelectedOrderedId(int orderID) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();
        String statusValue = null;

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        /*"select order_status.name from order_status,`order` where `order`.id='$order_id' and `order`.order_status=order_status.id"*/
        String sql = "SELECT " + Constants.TABLE_NAME_ORDER_STATUS + "." + Constants.KEY_ORDER_STATUS_NAME + " from " + Constants.TABLE_NAME_ORDER_STATUS
                + ", " + Constants.TABLE_NAME_ORDER + " where " + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_ID_FOR_ORDER + " = "
                + orderID + " and " + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_ORDER_STATUS_FOR_ORDER + " = " + Constants.TABLE_NAME_ORDER_STATUS + "."
                + Constants.KEY_ORDER_STATUS_ID;

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }



        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            statusValue = cursor.getString(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_NAME));

        }

        return statusValue;
    }

    @SuppressLint("Range")
    public int getLatestOrderId() {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
//        Cursor cursor = db.query(Constants.TABLE_NAME_ORDER,
//                new String[]{Constants.KEY_ID_FOR_ORDER,
//                        Constants.KEY_USER_ID_FOR_ORDER,
//                        Constants.KEY_USER_ADDRESS_FOR_ORDER,
//                        Constants.KEY_USER_CITY_FOR_ORDER,
//                        Constants.KEY_PINCODE_FOR_ORDER,
//                        Constants.KEY_PAYMENT_TYPE_FOR_ORDER,
//                        Constants.KEY_TOTAL_PRICE_FOR_ORDER,
//                        Constants.KEY_PAYMENT_STATUS_FOR_ORDER,
//                        Constants.KEY_ORDER_STATUS_FOR_ORDER,
//                        Constants.KEY_ADDED_ON_FOR_ORDER},
//                null,
//                null,
//                null, null, Constants.KEY_ADDED_ON_FOR_ORDER + " DESC", "1");
        /*SELECT *
    FROM    TABLE
    WHERE   ID = (SELECT MAX(ID)  FROM TABLE);*/
        String sql = "SELECT * FROM "+ Constants.TABLE_NAME_ORDER+" WHERE "+Constants.KEY_ID_FOR_ORDER
                +" = (SELECT MAX("+Constants.KEY_ID_FOR_ORDER+") FROM "+Constants.TABLE_NAME_ORDER+");";

        Cursor cursor = db.rawQuery(sql, null);

        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        Order order = new Order();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            order.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER)));
            order.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID_FOR_ORDER))));
            order.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ADDRESS_FOR_ORDER)));
            order.setCity(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_CITY_FOR_ORDER)));
            order.setPincode(cursor.getInt(cursor.getColumnIndex(Constants.KEY_PINCODE_FOR_ORDER)));
            order.setPaymentType(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_TYPE_FOR_ORDER)));
            order.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_TOTAL_PRICE_FOR_ORDER)));
            order.setPaymentStatus(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_STATUS_FOR_ORDER)));
            order.setOrderStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_FOR_ORDER)));
            order.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/


        }
        /*cursor.close();*/
        return order.getId();
    }


    public long addOrderDetails(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_ORDER_ID_FOR_ORDER_DETAILS, orderDetail.getOrderId());
        values.put(Constants.KEY_PRODUCT_ID_FOR_ORDER_DETAILS, orderDetail.getCarId());
        values.put(Constants.KEY_QUANTITY_FOR_ORDER_DETAILS, orderDetail.getQuantity());
        values.put(Constants.KEY_PRICE_FOR_ORDER_DETAILS, orderDetail.getPrice());
        values.put(Constants.KEY_ADDED_ON_FOR_ORDER_DETAILS, orderDetail.getAddedOn());


        return db.insert(Constants.TABLE_NAME_ORDER_DETAILS, null, values);
    }

//    public List<Order> getAllOrders(){
//       return new Order();
//    }
//
//    public List<OrderStatus> getAllOrderStatus(){
//
//
//    }

    public List<Double> getPriceListOfCartList() {
        List<Double> priceList = new ArrayList<>();

        return priceList;
    }

    @SuppressLint("Range")
    public OrderDetail getOrderDetailsBasedOnOrderID(int id) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        /*"select * from `order` where id='$order_id'"*/
        String sql = "SELECT * FROM " + Constants.TABLE_NAME_ORDER_DETAILS + " WHERE " + Constants.KEY_ORDER_ID_FOR_ORDER_DETAILS + " = " + id;

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //Checking if the resultset is empty
        if (cursor != null) {
            cursor.moveToFirst();
        }


        OrderDetail orderDetail = new OrderDetail();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null) {
            orderDetail.setOrderDetailId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER_DETAILS)));
            orderDetail.setOrderId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ORDER_ID_FOR_ORDER_DETAILS))));
            orderDetail.setCarId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_PRODUCT_ID_FOR_ORDER_DETAILS)));
            orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY_FOR_ORDER_DETAILS)));
            orderDetail.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_PRICE_FOR_ORDER_DETAILS))));
            orderDetail.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER_DETAILS)));


        }
        /*cursor.close();*/
        return orderDetail;
    }

    public boolean addCarsToWishlist() {
        return true;
    }

    @SuppressLint("Range")
    public List<OrderMasterList> addToOrderMasterList() {

        String sql = "SELECT " + Constants.TABLE_NAME_ORDER + ".*, " + Constants.TABLE_NAME_ORDER_STATUS + "."
                + Constants.KEY_ORDER_STATUS_NAME + " as order_status_str from " + Constants.TABLE_NAME_ORDER + " inner join "
                + Constants.TABLE_NAME_ORDER_STATUS + " on " + Constants.TABLE_NAME_ORDER_STATUS + "." + Constants.KEY_ORDER_STATUS_ID
                + " = " + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_ORDER_STATUS_FOR_ORDER + " ORDER BY "+ Constants.TABLE_NAME_ORDER+"."+Constants.KEY_ID_FOR_ORDER +" DESC ";

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // declare a collection that will hold the results.
        List<OrderMasterList> orderMasterLists = new ArrayList<>();

        // iterate over the results
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                // plant data.

                OrderMasterList orderMasterList = new OrderMasterList();
                int orderId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER)));
                String orderDate = cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER));
                String address = cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ADDRESS_FOR_ORDER));
                String paymentType = cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_TYPE_FOR_ORDER));
                String paymentStatus = cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_STATUS_FOR_ORDER));
                int orderStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_FOR_ORDER)));


                orderMasterList.setOrderID(orderId);
                orderMasterList.setOrderDate(orderDate);
                orderMasterList.setAddress(address);
                orderMasterList.setPaymentType(paymentType);
                orderMasterList.setPaymentStatus(paymentStatus);

                switch (orderStatus) {
                    case 1:
                        orderMasterList.setOrderStatus("pending");
                        break;
                    case 2:
                        orderMasterList.setOrderStatus("processing");
                        break;
                    case 3:
                        orderMasterList.setOrderStatus("shipped");
                        break;
                    case 4:
                        orderMasterList.setOrderStatus("canceled");
                        break;
                    default:
                        orderMasterList.setOrderStatus("complete");

                }


                // add the plant to the collection of plants.
                orderMasterLists.add(orderMasterList);

                cursor.moveToNext();
            }

        }
        //cursor.close();
        return orderMasterLists;
    }

    @SuppressLint("Range")
    public OrderMasterDetailList addToOrderMasterDetailList(int orderID) {

        /*"select distinct(order_detail.id) ,order_detail.*,product.name,
        product.image,`order`.address,`order`.city,`order`.pincode
        from order_detail,product ,`order` where order_detail.order_id='$order_id'
        and  order_detail.product_id=product.id GROUP by order_detail.id")*/
        String sql = "SELECT DISTINCT(" + Constants.TABLE_NAME_ORDER_DETAILS + "." + Constants.KEY_ID_FOR_ORDER_DETAILS + "), "
                + Constants.TABLE_NAME_ORDER_DETAILS + ".*, " + Constants.TABLE_NAME_CAR + "."
                + Constants.KEY_CAR_MODEL + ", " + Constants.TABLE_NAME_CAR + "." + Constants.KEY_CAR_IMAGE
                + ", " + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_USER_ADDRESS_FOR_ORDER
                + ", " + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_USER_CITY_FOR_ORDER + ", "
                + Constants.TABLE_NAME_ORDER + "." + Constants.KEY_PINCODE_FOR_ORDER + " from "
                + Constants.TABLE_NAME_ORDER_DETAILS + ", " + Constants.TABLE_NAME_CAR + ", " + Constants.TABLE_NAME_ORDER
                + " where " + Constants.TABLE_NAME_ORDER_DETAILS + "." + Constants.KEY_ORDER_ID_FOR_ORDER_DETAILS + " = " + orderID
                + " and " + Constants.TABLE_NAME_ORDER_DETAILS + "." + Constants.KEY_PRODUCT_ID_FOR_ORDER_DETAILS + " GROUP BY "
                + Constants.TABLE_NAME_ORDER_DETAILS + "." + Constants.KEY_ID_FOR_ORDER_DETAILS;
        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // declare a collection that will hold the results.
        List<OrderMasterDetailList> orderMasterDetailLists = new ArrayList<>();

        OrderMasterDetailList orderDetailList = new OrderMasterDetailList();
        // iterate over the results
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor != null && cursor.moveToFirst()) {
            // plant data.

            String carModelName = cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL));
            byte[] carImage = cursor.getBlob(cursor.getColumnIndex(Constants.KEY_CAR_IMAGE));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY_FOR_ORDER_DETAILS)));
            double unitPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_PRICE_FOR_ORDER_DETAILS)));
            double totalPrice = unitPrice * quantity;


            orderDetailList.setCarModelName(carModelName);
            orderDetailList.setCarImage(carImage);
            orderDetailList.setQuantity(quantity);
            orderDetailList.setUnitPrice(unitPrice);
            orderDetailList.setTotalPrice(totalPrice);


//                // add the plant to the collection of plants.
//                orderMasterDetailLists.add(orderDetailList);

            cursor.moveToNext();
        }

        //cursor.close();
//        return orderMasterDetailLists;
        return orderDetailList;

    }

    @SuppressLint("Range")
    public List<Order> getAllOrdersOfLoggedUserId(int userId){

        String sql = "SELECT * FROM " + Constants.TABLE_NAME_ORDER + " WHERE " + Constants.KEY_USER_ID_FOR_ORDER + " = " + userId;

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        List<Order> allOrderLists = new ArrayList<>();




        /*Now set the fields of the item object with the cursor object value*/
        if (cursor.moveToFirst()) {

            do{
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_ORDER)));
                order.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ID_FOR_ORDER))));
                order.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_ADDRESS_FOR_ORDER)));
                order.setCity(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_CITY_FOR_ORDER)));
                order.setPincode(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PINCODE_FOR_ORDER))));
                order.setPaymentType(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_TYPE_FOR_ORDER)));
                order.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_TOTAL_PRICE_FOR_ORDER)));
                order.setPaymentStatus(cursor.getString(cursor.getColumnIndex(Constants.KEY_PAYMENT_STATUS_FOR_ORDER)));
                order.setOrderStatus(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ORDER_STATUS_FOR_ORDER)));
                order.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_ORDER)));

                //Add it to the List
                allOrderLists.add(order);
            }while (cursor.moveToNext());




        }
        /*cursor.close();*/
        return allOrderLists;

    }

    public long addCarsToWishlist(int loggedUserId, int clickedCarId) {
        /*"insert into wishlist(user_id,product_id,added_on) values('$uid','$pid','$added_on')"*/
        SQLiteDatabase db = this.getWritableDatabase();

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String stringDate = DateFor.format(date);

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_USER_ID_FOR_WISHLIST, loggedUserId);
        values.put(Constants.KEY_CAR_ID_FOR_WISHLIST, clickedCarId);
        values.put(Constants.KEY_ADDED_ON_FOR_WISHLIST, stringDate);

        return db.insert(Constants.TABLE_NAME_WISHLIST, null, values);


    }

    public Cursor alreayExistedCarInWishlist(int loggedUserId, int clickedCarId) {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<Wishlist> loggedUsersWishlist = new ArrayList<>();

        return db.query(Constants.TABLE_NAME_WISHLIST,
                new String[]{Constants.KEY_ID_FOR_WISHLIST,
                        Constants.KEY_USER_ID_FOR_WISHLIST,
                        Constants.KEY_CAR_ID_FOR_WISHLIST,
                        Constants.KEY_ADDED_ON_FOR_WISHLIST},
                Constants.KEY_USER_ID_FOR_WISHLIST + "=? and " + Constants.KEY_CAR_ID_FOR_WISHLIST + "=?", new String[]{String.valueOf(loggedUserId), String.valueOf(clickedCarId)}, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                null);
    }

    @SuppressLint("Range")
    public List<WishlistCarProperty> getAllWishlistedCarsProperty(int loggedUserId) {

        /*"select product.name,product.image,product.price,product.mrp,wishlist.id,wishlist.product_id
        from product,wishlist where wishlist.product_id=product.id and wishlist.user_id='$uid'"*/

        String sql = "SELECT " + Constants.TABLE_NAME_CAR + "." + Constants.KEY_CAR_MODEL + ", " + Constants.TABLE_NAME_CAR
                + "." + Constants.KEY_CAR_IMAGE + ", " + Constants.TABLE_NAME_CAR + "." + Constants.KEY_CAR_PRICE + ", "
                + Constants.TABLE_NAME_WISHLIST + "." + Constants.KEY_ID_FOR_WISHLIST + ", " + Constants.TABLE_NAME_WISHLIST
                + "." + Constants.KEY_CAR_ID_FOR_WISHLIST + " from " + Constants.TABLE_NAME_CAR + ", " + Constants.TABLE_NAME_WISHLIST
                + " where " + Constants.TABLE_NAME_WISHLIST + "." + Constants.KEY_CAR_ID_FOR_WISHLIST + " = " + Constants.TABLE_NAME_CAR
                + "." + Constants.KEY_CAR_ID + " and " + Constants.TABLE_NAME_WISHLIST + "." + Constants.KEY_USER_ID_FOR_WISHLIST + " = "
                + loggedUserId;
        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // declare a collection that will hold the results.
        List<WishlistCarProperty> wishlistCarProperties = new ArrayList<>();

        // iterate over the results
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                // plant data.

                WishlistCarProperty wishlistCarProperty = new WishlistCarProperty();
                String carModelName = cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL));
                byte[] carImage = cursor.getBlob(cursor.getColumnIndex(Constants.KEY_CAR_IMAGE));
                double carUnitPrice = cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE));
                int wishlistID = cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_WISHLIST));
                int wishlistCarID = cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID_FOR_WISHLIST));


                wishlistCarProperty.setCarModelName(carModelName);
                wishlistCarProperty.setCarImage(carImage);
                wishlistCarProperty.setCarPrice(carUnitPrice);
                wishlistCarProperty.setWishlistID(wishlistID);
                wishlistCarProperty.setWishlistCarID(wishlistCarID);


                // add the plant to the collection of plants.
                wishlistCarProperties.add(wishlistCarProperty);

                cursor.moveToNext();
            }

        }
        //cursor.close();
        return wishlistCarProperties;
    }

//    public boolean removeCarsFromWishlist(int loggedUserID, int clickedCarId){
//
//    }

    public int deleteSelectedCarFromWishlist(int loggedUserId, int clickedCarId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numberOfRowsDeleted = db.delete(Constants.TABLE_NAME_WISHLIST,
                Constants.KEY_USER_ID_FOR_WISHLIST + "=? and " + Constants.KEY_CAR_ID_FOR_WISHLIST + "=?",
                new String[]{String.valueOf(loggedUserId), String.valueOf(clickedCarId)});

        //Close the db connection
//        db.close();
        return numberOfRowsDeleted;
    }

    @SuppressLint("Range")
    public List<WishlistCarProperty> getAllWishlistedCarBasedOnLoggedUserId(int id) {
        /*Here we will join the car table and the wishlist table*/
        String sql = "SELECT " + Constants.TABLE_NAME_CAR + ".* and " + Constants.TABLE_NAME_WISHLIST + ".* from "
                + Constants.TABLE_NAME_CAR + ", " + Constants.TABLE_NAME_WISHLIST + " where " + Constants.TABLE_NAME_CAR + "."
                + Constants.KEY_CAR_ID + " = " + Constants.TABLE_NAME_WISHLIST + "." + Constants.KEY_CAR_ID_FOR_WISHLIST + " and "
                + Constants.TABLE_NAME_WISHLIST + "." + Constants.KEY_USER_ID_FOR_WISHLIST + " = " + id;

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // declare a collection that will hold the results.
        List<WishlistCarProperty> wishlistCarProperties = new ArrayList<>();

        // iterate over the results
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                // plant data.

                WishlistCarProperty wishlistCarProperty = new WishlistCarProperty();
                String carModelName = cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_MODEL));
                byte[] carImage = cursor.getBlob(cursor.getColumnIndex(Constants.KEY_CAR_IMAGE));
                double carUnitPrice = cursor.getDouble(cursor.getColumnIndex(Constants.KEY_CAR_PRICE));
                int wishlistID = cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_WISHLIST));
                int wishlistCarID = cursor.getInt(cursor.getColumnIndex(Constants.KEY_CAR_ID_FOR_WISHLIST));


                wishlistCarProperty.setCarModelName(carModelName);
                wishlistCarProperty.setCarImage(carImage);
                wishlistCarProperty.setCarPrice(carUnitPrice);
                wishlistCarProperty.setWishlistID(wishlistID);
                wishlistCarProperty.setWishlistCarID(wishlistCarID);


                // add the plant to the collection of plants.
                wishlistCarProperties.add(wishlistCarProperty);

                cursor.moveToNext();
            }


        }
        return wishlistCarProperties;

    }

    @SuppressLint("Range")
    public Customer getCustomerBasedOnUsername(String typedUsername) {
        /*Instantiate the database*/
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CUSTOMER,
                new String[]{Constants.KEY_CUSTOMER_ID,
                        Constants.KEY_EMAIL,
                        Constants.KEY_CUSTOMER_NAME,
                        Constants.KEY_PHONE_NUMBER,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_PASSWORD},
                Constants.KEY_CUSTOMER_NAME + "=?",
                new String[]{typedUsername},
                null, null, null);

        //Checking if the resultset is empty
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
        Log.d("DataBase Handler class ", "getCustomer: " + cursor);


        Customer customer = new Customer();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DataBase Handler class ", "getCustomer: " + cursor + " Is moved to first ? " + cursor.moveToFirst());
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_CUSTOMER_ID)));
            customer.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
            customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CUSTOMER_NAME)));
            customer.setPhoneNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE_NUMBER))));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));
            customer.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/
        }
        /*cursor.close();*/
        return customer;
    }

    public void deleteCarParts(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME_CAR_PARTS,
                Constants.KEY_ID_FOR_CAR_PARTS + "=?",
                new String[]{String.valueOf(id)});

        //Close the db connection
        db.close();
    }

    @SuppressLint("Range")
    public List<CarParts> getAllCarParts() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<CarParts> carPartsList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CAR_PARTS,
                new String[]{Constants.KEY_ID_FOR_CAR_PARTS,
                        Constants.KEY_CAR_PARTS_NAME,
                        Constants.KEY_QUANTITY_FOR_CAR_PARTS,
                        Constants.KEY_PRICE_FOR_CAR_PARTS,
                        Constants.KEY_VOUCHER_FOR_CAR_PARTS,
                        Constants.KEY_IMAGE_FOR_CAR_PARTS},
                null, null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_ID_FOR_CAR_PARTS + " DESC");

        if (cursor.moveToFirst()) {
            do {
                CarParts carParts = new CarParts();
                carParts.setCarPartsId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_FOR_CAR_PARTS))));
                carParts.setCarPartsName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CAR_PARTS_NAME)));
                carParts.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY_FOR_CAR_PARTS)));
                carParts.setPrice(cursor.getInt(cursor.getColumnIndex(Constants.KEY_PRICE_FOR_CAR_PARTS)));
                carParts.setVoucher(cursor.getString(cursor.getColumnIndex(Constants.KEY_VOUCHER_FOR_CAR_PARTS)));
                carParts.setCarPartsImage(cursor.getBlob(5));

                carPartsList.add(carParts);

            } while (cursor.moveToNext());


        }
        /*cursor.close();*/
        //Return the ArrayList containing all the items
        return carPartsList;
    }

    public long updateCarPartsQuantityAfterCustomerBuy(CarParts carParts, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CAR_PARTS_NAME, carParts.getCarPartsName());
        values.put(Constants.KEY_QUANTITY_FOR_CAR_PARTS, quantity);
        values.put(Constants.KEY_PRICE_FOR_CAR_PARTS, carParts.getPrice());
        values.put(Constants.KEY_VOUCHER_FOR_CAR_PARTS, carParts.getVoucher());
        /*For inserting image, we pass byte data*/
        values.put(Constants.KEY_IMAGE_FOR_CAR_PARTS, carParts.getCarPartsImage());

        return db.update(Constants.TABLE_NAME_CAR_PARTS, values,
                Constants.KEY_ID_FOR_CAR_PARTS + "=?",
                new String[]{String.valueOf(carParts.getCarPartsId())});
    }


    public void addCarParts(CarParts carParts, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_CAR_PARTS_NAME, carParts.getCarPartsName());
        values.put(Constants.KEY_QUANTITY_FOR_CAR_PARTS, carParts.getQuantity());
        values.put(Constants.KEY_PRICE_FOR_CAR_PARTS, carParts.getPrice());
        values.put(Constants.KEY_VOUCHER_FOR_CAR_PARTS, carParts.getVoucher());
        /*For inserting image, we pass byte data*/
        values.put(Constants.KEY_IMAGE_FOR_CAR_PARTS, imageBytes);



        db.insert(Constants.TABLE_NAME_CAR_PARTS, null, values);
    }

    public long addtoContactUs(String name, String email, int mobile, String comment, contact_us contactUs, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*We do not need to add the id value as it is a primary key and automatically given a unique value*/
        values.put(Constants.KEY_NAME_FOR_CONTACT_US, name);
        values.put(Constants.KEY_EMAIL_FOR_CONTACT_US, email);
        values.put(Constants.KEY_MOBILE_FOR_CONTACT_US, mobile);
        values.put(Constants.KEY_COMMENT_FOR_CONTACT_US, comment);
        /*values.put(Constants.KEY_DATE_OF_BIRTH, customer.getDateOfBirth());*/
        values.put(Constants.KEY_ADDED_ON_FOR_CONTACT_US, date);

        return db.insert(Constants.TABLE_NAME_CONTACT_US, null, values);

    }

    @SuppressLint("Range")
    public List<contact_us> getAllContactUs(){
        SQLiteDatabase db = this.getReadableDatabase();
        /*byte[] imageBytes = retrieveImageFromDB(db);*/
        /*Create the List of Items*/
        List<contact_us> contact_usList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CONTACT_US,
                new String[]{Constants.KEY_ID_FOR_CONTACT_US,
                        Constants.KEY_NAME_FOR_CONTACT_US,
                        Constants.KEY_EMAIL_FOR_CONTACT_US,
                        Constants.KEY_MOBILE_FOR_CONTACT_US,
                        Constants.KEY_COMMENT_FOR_CONTACT_US,
                        Constants.KEY_ADDED_ON_FOR_CONTACT_US},
                null, null, null, null,
                /*The item that is added most recently(added last) will be shown first*/
                Constants.KEY_ID_FOR_CONTACT_US + " DESC");

        if (cursor.moveToFirst()) {
            do {
                contact_us contactUs = new contact_us();
                contactUs.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_FOR_CONTACT_US))));
                contactUs.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME_FOR_CONTACT_US)));
                contactUs.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL_FOR_CONTACT_US)));
                contactUs.setMobile(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOBILE_FOR_CONTACT_US))));
                contactUs.setComment(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMMENT_FOR_CONTACT_US)));
                contactUs.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_CONTACT_US)));

                //Convert Timestamp into something readable
/*                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                        .getTime()); //Feb 23, 2020
                item.setDateItemAdded(formattedDate);*/

                //Add the item object to the itemList ArrayList
                contact_usList.add(contactUs);

            } while (cursor.moveToNext());


        }
        /*cursor.close();*/
        //Return the ArrayList containing all the items
        return contact_usList;
    }

    public long deleteCommentFromDB(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Constants.TABLE_NAME_CONTACT_US,
                Constants.KEY_ID_FOR_CONTACT_US + "=?",
                new String[]{String.valueOf(id)});

        //Close the db connection
//        db.close();
    }

    @SuppressLint("Range")
    public contact_us getContactUsBasedOnID(int id){
        /*Instantiate the database*/
        Log.d("DB Helper", "getContactUsBasedOnID: " + id);
        SQLiteDatabase db = this.getReadableDatabase();

        /*Create a Cursor object */
        /*By the following query, the cursor object get the specific baby object based on id*/
        Cursor cursor = db.query(Constants.TABLE_NAME_CONTACT_US,
                new String[]{Constants.KEY_ID_FOR_CONTACT_US,
                        Constants.KEY_NAME_FOR_CONTACT_US,
                        Constants.KEY_EMAIL_FOR_CONTACT_US,
                        Constants.KEY_MOBILE_FOR_CONTACT_US,
                        Constants.KEY_COMMENT_FOR_CONTACT_US,
                        Constants.KEY_ADDED_ON_FOR_CONTACT_US},
                Constants.KEY_ID_FOR_CONTACT_US + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);



        contact_us contactUs = new contact_us();
        /*Now set the fields of the item object with the cursor object value*/
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DataBaseHandler Class", "getCustomerBasedOnId: " + cursor + "Is Moved To first ? " + cursor.moveToFirst());
            contactUs.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID_FOR_CONTACT_US)));
            contactUs.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME_FOR_CONTACT_US)));
            contactUs.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL_FOR_CONTACT_US)));
            contactUs.setMobile(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOBILE_FOR_CONTACT_US))));
            contactUs.setComment(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMMENT_FOR_CONTACT_US)));
            contactUs.setAddedOn(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDED_ON_FOR_CONTACT_US)));

 /*           //Convert Timestamp into something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_TIME)))
                    .getTime()); //Feb 23, 2020
            item.setDateItemAdded(formattedDate);*/
        }
        /*cursor.close();*/
        return contactUs;
    }
}