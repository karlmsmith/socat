package gov.noaa.pmel.sdimetadata.person;

/**
 * Same as an Investigator except isValid requires more valid fields.
 */
public class Submitter extends Investigator implements Cloneable {

    @Override
    public boolean isValid() {
        if ( lastName.isEmpty() )
            return false;
        if ( firstName.isEmpty() )
            return false;
        if ( address.isEmpty() )
            return false;
        if ( phone.isEmpty() )
            return false;
        if ( email.isEmpty() )
            return false;
        return true;
    }

    @Override
    public Submitter clone() {
        return (Submitter) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( !(obj instanceof Submitter) )
            return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("Investigator", "Submitter");
    }

}
