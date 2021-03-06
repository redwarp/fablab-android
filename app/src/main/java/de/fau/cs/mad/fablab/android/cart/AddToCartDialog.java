package de.fau.cs.mad.fablab.android.cart;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import de.fau.cs.mad.fablab.android.R;
import de.fau.cs.mad.fablab.android.ui.UiUtils;
import de.fau.cs.mad.fablab.rest.core.Product;

public class AddToCartDialog extends DialogFragment implements NumberPicker.OnValueChangeListener {
    private Product product;
    private double amount;
    private TextView priceTotalTextView;

    public static AddToCartDialog newInstance(Product product) {
        AddToCartDialog dialog = new AddToCartDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        product = (Product) getArguments().getSerializable("product");
        if (savedInstanceState != null) {
            amount = savedInstanceState.getDouble("amount");
        } else {
            amount = 1;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_to_cart, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_to_cart, container, false);

        ((TextView) view.findViewById(R.id.add_to_cart_name)).setText(product.getName());
        ((TextView) view.findViewById(R.id.add_to_cart_price)).setText(product.getPrice()
                + getResources().getString(R.string.currency) + " "
                + getResources().getString(R.string.add_to_cart_price_per_unit) + " " + product.getUnit());
        priceTotalTextView = (TextView) view.findViewById(R.id.add_to_cart_price_total);
        priceTotalTextView.setText(String.format("%.2f", product.getPrice())
                + getResources().getString(R.string.currency));
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.add_to_cart_numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(50);
        numberPicker.setValue((int) amount);
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(
                android.R.drawable.ic_menu_close_clear_cancel);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_cart:
                Cart.MYCART.addProduct(product, amount);
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void finish() {
        UiUtils.hideKeyboard(getActivity());
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("amount", amount);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        priceTotalTextView.setText(String.format("%.2f", newVal * product.getPrice())
                + getResources().getString(R.string.currency));
        amount = newVal;
    }
}
