package com.wanjianhua.aooshop.act.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.adapter.TicketAdapter;
import com.wanjianhua.aooshop.act.bean.TicketInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 6005001713
 */
public class AllTicketFragment extends Fragment
{
    private View view;
    public static String photopath = null;
    private static Context context;
    private TicketAdapter ticketAdapter;
    private List<TicketInfo> ticketInfos = new ArrayList<>();
    private ListView lv_ticket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = (ViewGroup) inflater.inflate(R.layout.me_ticket, container, false);
        initView(view);
        initDate();
        return view;
    }

    private void initDate()
    {
        context = getActivity();
        for(int i = 0; i < 10; i++)
        {
            ticketInfos.add(new TicketInfo());
        }
        ticketAdapter.setData(ticketInfos);
    }

    private void initView(View view)
    {
        ticketAdapter = new TicketAdapter(getActivity());
        lv_ticket = (ListView) view.findViewById(R.id.lv_ticket);
        lv_ticket.setAdapter(ticketAdapter);

    }


}
