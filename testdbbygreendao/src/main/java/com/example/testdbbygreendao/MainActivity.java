package com.example.testdbbygreendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity
{
    private Button btn_do;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_do = (Button) findViewById(R.id.btn_do);
        btn_do.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TaskViewEntity taskViewEntity = new TaskViewEntity();
                taskViewEntity.setGuid(DateUtil.curTime());
                DbUtils.getsTaskViewEntityDaoImpl().insertOrUpdate(taskViewEntity);
                List<TaskViewEntity> taskViewEntities = DbUtils.getsTaskViewEntityDaoImpl().getTaskViewListByDisplayable();
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < taskViewEntities.size(); i++)
                {
                    sb.append(taskViewEntities.get(i).getGuid()).append("\n");
                }
                Toast.makeText(MainActivity.this, sb.toString(), 1).show();
            }
        });
    }
}
