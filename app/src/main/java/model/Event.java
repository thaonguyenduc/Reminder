package model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Thao.nguyenduc on 4/8/2016.
 */
public class Event {
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventStartDate;
    private String eventOrganizer;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    @Override
    public String toString() {
        StringBuilder eventInfoSb = new StringBuilder();
        eventInfoSb.append("The "+ this.eventName);// the abc
        eventInfoSb.append(" event");//event
        eventInfoSb.append(StringUtils.isNotEmpty(this.eventLocation)?" takes place in " + eventLocation:"" );//takes place in xyz
        eventInfoSb.append(" at "+ eventDate);// at
        eventInfoSb.append(StringUtils.isNotEmpty(this.eventStartDate)?" from "+ eventStartDate:"");// form abc
        eventInfoSb.append(" organised by "+eventOrganizer);//organised by
        return eventInfoSb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!eventName.equals(event.eventName)) return false;
        return eventLocation.equals(event.eventLocation);

    }

    @Override
    public int hashCode() {
        int result = eventName.hashCode();
        result = 31 * result + eventLocation.hashCode();
        return result;
    }
}
