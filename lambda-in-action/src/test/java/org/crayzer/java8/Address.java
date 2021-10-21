package org.crayzer.java8;

import java.util.Optional;

/**
 * @author crayzer
 */
public class Address {
    private Country country;

    public Country getCountry1() {
        return this.country;
    }

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country=" + country +
                '}';
    }
}
