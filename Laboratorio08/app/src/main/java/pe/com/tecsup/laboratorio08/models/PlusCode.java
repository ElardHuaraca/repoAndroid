
package pe.com.tecsup.laboratorio08.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PlusCode {

    private String compoundCode;
    private String globalCode;

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("compoundCode", compoundCode).append("globalCode", globalCode).toString();
    }

}
