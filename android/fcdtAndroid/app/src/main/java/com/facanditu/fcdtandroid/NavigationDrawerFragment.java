package com.facanditu.fcdtandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.facanditu.fcdtandroid.screen.searchresto.SearchNavigator;
import com.facanditu.fcdtandroid.screen.searchresto.SearchRestosType;
import com.facanditu.fcdtandroid.util.SharedPreferenceManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment  implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ExpandableListView drawerList;

    private boolean mAlreadyLearned;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlreadyLearned = SharedPreferenceManager.getUserAlreadyLearnedDrawer(getActivity());
        if(savedInstanceState!=null){
            mFromSavedInstanceState = true;
        }
    }

    private void initDrawer() {

        drawerList = (ExpandableListView) drawerLayout.findViewById(R.id.left_drawer);

        drawerList.setAdapter(new NewAdapter(this.getActivity(), groupItem, childItem));

        drawerList.setOnChildClickListener(this);
        drawerList.setOnGroupClickListener(this);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if(SearchRestosType.Fav.name().equals((String) v.getTag())){
            SearchNavigator.goTo(getActivity(),SearchRestosType.Fav);
        }
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        /*Toast.makeText(this.getActivity(), "Clicked On Child" + v.getTag(),
                Toast.LENGTH_SHORT).show();*/
        SearchNavigator.goTo(getActivity(),SearchRestosType.valueOf((String)v.getTag()));
        return true;
    }
    private ArrayList<MenuItem> groupItem = new ArrayList<MenuItem>();

    private ArrayList<Object> childItem = new ArrayList<Object>();
    private void setGroupData() {
        groupItem.add(new MenuItem(R.drawable.ic_action_search, "法餐馆查找","searchRestos"));
        groupItem.add(new MenuItem(R.drawable.ic_mesentreesplatsdesserts, "法餐百科","wikiFrCook"));
        groupItem.add(new MenuItem(R.drawable.ic_action_favorite, "我的收藏","Fav"));
        groupItem.add(new MenuItem(R.drawable.ic_myaccount, "我的账户","myAccount"));
        groupItem.add(new MenuItem(R.drawable.ic_infomonrestaurant,"关于","about"));
    }

    public void setChildGroupData() {

        ArrayList<MenuItem> child = new ArrayList<>();
        child.add(new MenuItem(R.drawable.ic_action_place,"按当前位置", SearchRestosType.NearBy.name()));
        child.add(new MenuItem(R.drawable.ic_business_white_24dp,"按地址",SearchRestosType.Address.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按邮编", SearchRestosType.PostCode.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按城市", SearchRestosType.City.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按特色标签", SearchRestosType.Tags.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按价格", SearchRestosType.Price.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按推荐理由", SearchRestosType.RecReason.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"按菜名(支持中法)", SearchRestosType.DishName.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"随机浏览", SearchRestosType.Random.name()));
        child.add(new MenuItem(R.drawable.ic_accueil,"最新加入", SearchRestosType.Newest.name()));
        childItem.add(child);
        childItem.add(new ArrayList<String>());
        childItem.add(new ArrayList<String>());
        childItem.add(new ArrayList<String>());
        childItem.add(new ArrayList<String>());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public void setUp(int idNavigationDrawerView, DrawerLayout drawerLayout, final Toolbar toolbar){
        this.drawerLayout = drawerLayout;
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                if (!mAlreadyLearned){
                    mAlreadyLearned = true;
                    SharedPreferenceManager.setUserAlreadyLearnedDrawer(getActivity(),mAlreadyLearned);
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            /*
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.3){
                    toolbar.setAlpha(1-slideOffset);
                }
            }*/
        };

        if(!mAlreadyLearned && !mFromSavedInstanceState){
            this.drawerLayout.openDrawer(getActivity().findViewById(idNavigationDrawerView));
        }

        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

        setGroupData();
        setChildGroupData();

        initDrawer();
    }




}
