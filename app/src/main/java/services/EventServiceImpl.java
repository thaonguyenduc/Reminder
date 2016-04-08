package services;

import java.util.ArrayList;
import java.util.List;

import model.Event;

/**
 * Created by Thao.nguyenduc on 4/8/2016.
 */
public class EventServiceImpl implements EventService{
    @Override
    public List<Event> getAll() {
        return initEventList();
    }

    private List<Event> initEventList() {
        List<Event> listEvent = new ArrayList<Event>();

        Event evt = new Event();
        evt.setEventName("hoi cho");
        evt.setEventLocation("nha trang");
        evt.setEventOrganizer("nguyen thao");

        listEvent.add(evt);

        evt = new Event();
        evt.setEventName("dua xe");
        evt.setEventLocation("sai gon");
        evt.setEventOrganizer("nguyen thao");
        listEvent.add(evt);

        evt = new Event();
        evt.setEventName("dua cho");
        evt.setEventLocation("vung tau");
        evt.setEventOrganizer("nguyen thao");
        listEvent.add(evt);
        return listEvent;
    }
}
