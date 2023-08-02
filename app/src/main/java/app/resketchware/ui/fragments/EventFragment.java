package app.resketchware.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.resketchware.R;
import app.resketchware.beans.EventBean;
import java.util.ArrayList;

public class EventFragment extends Fragment {
    private ArrayList<EventBean> moreBlocks;
    private ArrayList<EventBean> viewEvents;
    private ArrayList<EventBean> componentEvents;
    private ArrayList<EventBean> activityEvents;
    private ArrayList<EventBean> drawerViewEvents;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_event, container, false);
        init(view);
        return view;
    }

    public void init(ViewGroup layout) {
        ((RecyclerView)layout.findViewById(R.id.category_list)).setAdapter(new CategoryList());
        ((RecyclerView)layout.findViewById(R.id.category_list)).setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private class CategoryList extends RecyclerView.Adapter<CategoryList.ViewHolder> {

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.layout_events_category_item, null);
            RecyclerView.LayoutParams _lp =
                    new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            int icon;
            String title;
            switch (position) {
                case 0:
                    icon = R.drawable.ic_cycle_color_48dp;
                    title = "Activity";
                    break;
                case 1:
                    icon = R.drawable.multiple_devices_48;
                    title = "View";
                    break;
                case 2:
                    icon = R.drawable.component_96;
                    title = "Component";
                    break;
                case 3:
                    icon = R.drawable.ic_drawer_color_48dp;
                    title = "Drawer";
                    break;
                case 4:
                    icon = R.drawable.more_block_96dp;
                    title = "More Block";
                    break;
                default:
                    icon = R.drawable.ic_cycle_color_48dp;
                    title = "Activity";
                    break;
            }
            View view = holder.itemView;
            ((ImageView) view.findViewById(R.id.img_icon)).setImageResource(icon);
            ((TextView) view.findViewById(R.id.title)).setText(title);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
