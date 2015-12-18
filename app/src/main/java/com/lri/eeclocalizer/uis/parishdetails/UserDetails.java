package com.lri.eeclocalizer.uis.parishdetails;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Lyonnel T. DZOTANG on 15/07/2015.
 */
public class UserDetails {

    @Getter
    @Setter
    private String cardTitle;

    @Getter
    @Setter
    private String information;

    @Getter
    @Setter
    private String type;


    public UserDetails(String cardTitle, String information, String type) {
        this.cardTitle = cardTitle;
        this.information = information;
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "cardTitle='" + cardTitle + '\'' +
                ", information='" + information + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetails that = (UserDetails) o;

        return !(information != null ? !information.equals(that.information) : that.information != null);

    }

    @Override
    public int hashCode() {
        return information != null ? information.hashCode() : 0;
    }

    //protected List<UserDetails>
}
