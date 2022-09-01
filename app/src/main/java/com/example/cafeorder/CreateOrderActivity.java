package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {
    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String name;
    private String password;
    private String drink;

    private StringBuilder builderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        Intent intent = getIntent();
        if(intent.hasExtra("name") && intent.hasExtra("pass")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("pass");
        }else{
            name = getString(R.string.defaultName);
            name = getString(R.string.defaultPass);
        }

        String hello = String.format(getString(R.string.helloUser), name);
        textViewHello = findViewById(R.id.textViewHello);
        textViewHello.setText(hello);

        drink = getString(R.string.tea);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.additions) ,drink);

        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerOfTea);
        spinnerCoffee = findViewById(R.id.spinnerOfCoffee);

        builderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        //изменения напитка в зависимости от выбора radioButton
        //получаем нажатую кнопку
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id == R.id.rbTea){
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);

        }else if(id == R.id.rbCoffee){
            drink = getString(R.string.coffee);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions) ,drink);
        textViewAdditions.setText(additions);

    }

    public void onClickSendOrder(View view) {
        builderAdditions.setLength(0);
        if(checkBoxMilk.isChecked()){
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if(checkBoxSugar.isChecked()){
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        if(checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))){
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }

        String optionOfDrink = "";
        if(drink.equals(R.string.tea)){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        }else{
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order),name,password,drink,optionOfDrink);
        String additions;
        if(builderAdditions.length()>0){
            additions = getString(R.string.needAdditions) + builderAdditions.toString();
        }else{
            additions = "";
        }
        String fullOrder = order + additions;

        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}