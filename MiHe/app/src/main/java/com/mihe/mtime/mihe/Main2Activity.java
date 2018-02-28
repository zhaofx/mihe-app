package com.mihe.mtime.mihe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mihe.mtime.adapter.CommonAdapter;
import com.mihe.mtime.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    public static List<User> userList = new ArrayList<User>();//实体类
    private Button bt;//activity_main.xml里的Button
    private TextView tv1;//item.xml里的TextView：Textviewname
    private TextView tv2;//item.xml里的TextView：Textviewage
    private ListView lv;//activity_main2.xml里的ListView
    private CommonAdapter adapter;//要实现的类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt = (Button) findViewById(R.id.button2);
        lv = (ListView) findViewById(R.id.listView2);
        userList = getUserList();

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String str = bundle.getString("user");
            if (null != str && !"".equals(str.trim())) {
                String[] fileds = str.split(":");
                User ue = new User();//给实体类赋值
                ue.setName(fileds[0]);
                ue.setAge(Integer.parseInt(fileds[1]));
                userList.add(ue);
            }
        }
        adapter = new CommonAdapter<User>(this, userList, R.layout.item) {
            protected void convertView(View view, User user) {
                tv1 = (TextView) view.findViewById(R.id.TextviewName);//找到Textviewname
                tv1.setText(user.getName());//设置参数
                tv2 = (TextView) view.findViewById(R.id.TextviewAge);//找到Textviewage
                tv2.setText(String.valueOf(user.getAge()));//设置参数
            }
        };
        lv.setAdapter(adapter);
        //获取当前ListView点击的行数，并且得到该数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1 = (TextView) view.findViewById(R.id.TextviewName);//找到Textviewname
                String str = tv1.getText().toString();//得到数据
                Toast.makeText(Main2Activity.this, "" + str, Toast.LENGTH_SHORT).show();//显示数据
                Intent it = new Intent(Main2Activity.this, Main2Activity.class);
                Bundle b = new Bundle();
                b.putString("we", str);  //string
                it.putExtras(b);
                startActivity(it);
            }
        });

        //获取当前ListView点击的行数，并且得到该数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = userList.get(position);
                user.setAge(user.getAge() + 1);
                userList.set(position, user);
                updateItem(position);
            }
        });
        //浮动动作按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();;
                String newUserName = "newUser" + new Random().nextInt(10);
                User ue = new User();//给实体类赋值
                ue.setName(newUserName);
                ue.setAge(18);
                userList.add(ue);
                adapter.notifyDataSetChanged();
//                addItem(ue, userList.size() - 1);
            }
        });
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<User>();
        //模拟数据库
        for (int i = 0; i < 10; i++) {
            User ue = new User();//给实体类赋值
            ue.setName("小米" + i);
            ue.setAge(18);
            userList.add(ue);
        }
        return userList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 第三种方法 调用一次getView()方法；Google推荐的做法
     *
     * @param position 要更新的位置
     */
    private void updateItem(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = lv.getLastVisiblePosition();
        Log.i("info", firstVisiblePosition + "--" + lastVisiblePosition);
        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = lv.getChildAt(position - firstVisiblePosition);
            adapter.getView(position, view, lv);
        }
    }

    private void addItem(User user, int position) {
        adapter.addItem(user);
        adapter.notifyDataSetChanged();
    }
}
