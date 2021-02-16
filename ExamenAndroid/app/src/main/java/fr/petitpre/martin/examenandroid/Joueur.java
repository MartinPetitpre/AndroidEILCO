package fr.petitpre.martin.examenandroid;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Joueur {

    private String name;
    private Integer number;
    private String photo;
    private String position;
    private Height height;
    //private Weight weight;
    private String dateOfBirth;
    private String yearsPro;
    private String country;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    //probleme cannot find class symbol ??
    /*public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }*/

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getYearsPro() {
        return yearsPro;
    }

    public void setYearsPro(String yearsPro) {
        this.yearsPro = yearsPro;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void loadImageIntoImageView(Context context, ImageView imageView) {
        Glide.with(context)
                .load(String.format(Locale.ENGLISH, this.photo))
                .apply(new RequestOptions()
                        )
                .into(imageView);
    }

}