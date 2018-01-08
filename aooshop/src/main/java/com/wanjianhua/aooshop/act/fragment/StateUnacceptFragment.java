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

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author 6005001713
 */
public class StateUnacceptFragment extends Fragment
{
    @Bind(R.id.lv_ticket)
    ListView lvTicket;
    private View view;
    public static String photopath = null;
    private static Context context;
    private TicketAdapter ticketAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = (ViewGroup) inflater.inflate(R.layout.me_ticket, container, false);
        ButterKnife.bind(this, view);
        initDate();
        return view;
    }

    private void initDate()
    {
        context = getActivity();

    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
