package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.justjava.R.id.Choc;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 1;
    boolean whipp;
    boolean hasWhipp;
    boolean choc;
    boolean hasChoc;
    String nameCl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            numberOfCoffees = savedInstanceState.getInt("numberOfCoffees");
            displayQuantity(numberOfCoffees);
            hasWhipp = savedInstanceState.getBoolean("hasWhipp");
            if (hasWhipp == true) {
                CheckBox hasW = (CheckBox) findViewById(R.id.Whip);
                hasW.setChecked(true);
            }
            hasChoc = savedInstanceState.getBoolean("hasChoc");
            if (hasChoc == true) {
                CheckBox choc = (CheckBox) findViewById(R.id.Choc);
                choc.setChecked(true);
            }
            nameCl = savedInstanceState.getString("name");
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(nameCl);

        }
    }


    public void submitOrder(View view) {
        createOrderSummery();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "pavluha.losenator69@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameOfClient());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummery());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private String createOrderSummery () {
        CheckBox hasWhipp = (CheckBox) findViewById(R.id.Whip);
        CheckBox hasChoc = (CheckBox) findViewById(Choc);
        calculatePrice();
       int price = calculatePrice();
        String name = nameOfClient();
        String priceMessage = "Name: " + name;
        if (hasWhipp.isChecked()) {
            priceMessage = priceMessage + "\nAdd whipped cream? Yes.";
            price += 1;
        }
        else
            priceMessage =  priceMessage + "\nAdd whipped cream? No.";
        if (hasChoc.isChecked()) {
            priceMessage = priceMessage + "\nAdd chocolate? Yes.";
            price += 2;
        }
        else
            priceMessage = priceMessage + "\nAdd chocolate? No.";
        priceMessage = priceMessage +"\nQuantity: " + numberOfCoffees;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    public void increment (View view) {
        if (numberOfCoffees < 100) {
            ++numberOfCoffees;
            displayQuantity(numberOfCoffees);
        }
        else if (numberOfCoffees >=100) {
            displayQuantity(numberOfCoffees);
            Context more = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffee!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(more, text, duration);
            toast.show();
        }

    }
    public void decrement (View view) {
        if (numberOfCoffees == 1) {
            displayQuantity(numberOfCoffees);
            Context less = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(less, text, duration);
            toast.show();
        }
        else
            --numberOfCoffees;
        displayQuantity(numberOfCoffees);

    }
    private int calculatePrice() {
        int price = numberOfCoffees * 5;
        return price;
    }


    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + numberOfCoffees);
    }

   private String nameOfClient (){
       EditText nameOfCl = (EditText) findViewById(R.id.name);
       String name = nameOfCl.getText().toString();
      return name;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("numberOfCoffees", numberOfCoffees);
        savedInstanceState.putString("name", nameOfClient());
        CheckBox hasWhipp = (CheckBox) findViewById(R.id.Whip);
        if (hasWhipp.isChecked()) {
            whipp = true;
        } else {
            whipp = false;
        }
        savedInstanceState.putBoolean("hasWhipp", whipp);
        CheckBox hasChoc = (CheckBox) findViewById(Choc);
        if (hasChoc.isChecked()){
            choc = true;
        } else {
            choc = false;
        }
        savedInstanceState.putBoolean("hasChoc", choc);
        savedInstanceState.putString("name", nameOfClient());
    }


}