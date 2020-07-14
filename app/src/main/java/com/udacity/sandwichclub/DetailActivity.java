package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescription;
    private TextView mKnownAs;
    private TextView mIngredients;
    private TextView mPlaceOfOrgin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescription = (TextView) findViewById(R.id.description_tv);
        mKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mPlaceOfOrgin = (TextView) findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescription.setText(sandwich.getDescription());
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i ++) {
            mKnownAs.append(sandwich.getAlsoKnownAs().get(i));
            if (i != sandwich.getAlsoKnownAs().size() - 1) {
              mKnownAs.append(", ");
            }
        }
        for (int i = 0; i < sandwich.getIngredients().size(); i++ ) {
            mIngredients.append(sandwich.getIngredients().get(i));
            if (i != sandwich.getIngredients().size() - 1) {
                mIngredients.append(", ");
            }
        }
        mPlaceOfOrgin.setText(sandwich.getPlaceOfOrigin());
    }
}
