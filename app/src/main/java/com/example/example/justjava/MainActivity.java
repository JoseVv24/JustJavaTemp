package com.example.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

import static android.R.attr.button;
import static android.R.attr.checked;
import static android.R.attr.order;
import static android.R.id.message;
import static android.content.Intent.createChooser;
import static com.example.example.justjava.R.drawable.coffeecup;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    TextView txtView;
    Button button;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    //ImageView img4;
    CheckBox whippedCream_checkbox;
    CheckBox chocolate_checkbox;
    //Animation frombottom;
    Animation fromtop;
    Animation fromleft;
    Animation fromright;
    EditText edit;
    TextView orderSummaryTextView;
    String text = "";
    String orderMessage = "";
    int basePrice = 0;
    boolean hasWhippedCream;
    boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        img1 = (ImageView) findViewById(R.id.btn);
        img2 = (ImageView) findViewById(R.id.cup);
        img3 = (ImageView) findViewById(R.id.logo);
        //img4 = (ImageView) findViewById(R.id.come);

        //frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromleft = AnimationUtils.loadAnimation(this,R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        img1.setAnimation(fromright);
        img2.setAnimation(fromtop);
        img3.setAnimation(fromleft);
        //img4.setAnimation(fromright);

    }

    public void btn (View view) {

        setContentView(R.layout.activity_main);
        txtView = (TextView)findViewById(R.id.error_text_view);
        txtView.setVisibility(View.GONE);

        edit = (EditText)findViewById(R.id.name);
        whippedCream_checkbox = (CheckBox)findViewById(R.id.whippedCream_checkbox);
        chocolate_checkbox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        //orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity<10) {
            //orderMessage="\u20B9 10";
            //displayMessage(orderMessage);
            txtView.setVisibility(View.GONE);
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
        else {
            txtView.setText("Isn't it Too Much?");
            txtView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity >0) {
            //orderMessage="\u20B9 10";
            //displayMessage(orderMessage);
            txtView.setVisibility(View.GONE);
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        else {
            txtView.setText("Negative Cups of Coffee??");
            txtView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        //TextView priceTextView = (TextView)findViewById(R.id.order_summary_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * Calculates the price of the order
     * @return price total price
     */
    private int calculatePrice() {
        int price = (quantity * 5)+ basePrice;
        return price;
    }
    /**
     * This is called if or when whippedCream CheckBox is checked
     */
    public void check_whippedCream(View v) {
        if (whippedCream_checkbox.isChecked()) {
            hasWhippedCream = true;
            basePrice += 1;
        }
        else {
            hasWhippedCream = false;
        }
    }

    public void check_chocolate(View v) {
        if (chocolate_checkbox.isChecked())
        {
            hasChocolate = true;
            basePrice += 2;
        }
        else {
            hasChocolate = false;
        }
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){

        int price = calculatePrice();

        createOrderSummary(price,hasWhippedCream,hasChocolate);

        button = (Button)findViewById(R.id.buttonToast);
        Toast toast = Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,15,30);
        toast.show();
    }


    /**
     * This method is called to formulate the order summary
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @param price total price
     */
    public void createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate) {
        text = edit.getText().toString();
        orderMessage = "Name: " + text + "\n" + "Add Whipped Cream? " + addWhippedCream + "\nAdd Chocolate? " + addChocolate + "\nQuantity: " + quantity + "\nTotal: " + "\u20B9" + price + "\nThank You!";
        String subject = "Just Java Order For " + text;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        //Intent chooser =  Intent.createChooser(intent, "Send Email");
        intent.setType("message/rfc822");
        startActivity(intent);
    }


}