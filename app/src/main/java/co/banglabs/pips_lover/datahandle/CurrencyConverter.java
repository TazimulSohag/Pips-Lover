
package co.banglabs.pips_lover.datahandle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyConverter {

    @SerializedName("PEAR_VALUE")
    @Expose
    private Double pearValue;

    public Double getPearValue() {
        return pearValue;
    }

    public void setPearValue(Double pearValue) {
        this.pearValue = pearValue;
    }

}
