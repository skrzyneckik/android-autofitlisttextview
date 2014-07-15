package net.kskrzynecki.autofitlisttextview.sample;

/**
 * @author Krzysztof 'Szpecku' Skrzynecki
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.kskrzynecki.autofitlisttextview.widget.AutofitListTextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView dataList = (ListView) findViewById(R.id.lvData);

        List<ExampleStructureData> data = new ArrayList<ExampleStructureData>();
        String[] items = getResources().getStringArray(R.array.data);
        for (String item : items) {
            data.add(new ExampleStructureData(item, item));
        }
        ExampleAdapter adapter = new ExampleAdapter(this, data);
        dataList.setAdapter(adapter);
    }

    private class ExampleStructureData {
        private String label1;
        private String label2;

        public ExampleStructureData(String label1, String label2) {
            this.label1 = label1;
            this.label2 = label2;
        }

        public String getLabel1() {
            return label1;
        }

        public String getLabel2() {
            return label2;
        }
    }

    private class ExampleAdapter extends BaseAdapter {

        class ViewHolder {
            TextView tvStandard;
            AutofitListTextView tvAutofit;
        }

        private List<ExampleStructureData> items;
        private LayoutInflater inflater;

        public ExampleAdapter(Context context, List<ExampleStructureData> items) {
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return (items == null) ? 0 : items.size();
        }

        @Override
        public Object getItem(int position) {
            return (items != null && position >= 0 && position < getCount())
                    ? items.get(position)
                    : null;
        }

        @Override
        public long getItemId(int position) {
            return (position >= 0 && position < getCount()) ? position : -1;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();

                view = inflater.inflate(R.layout.list_item, null);

                holder.tvStandard= (TextView) view.findViewById(R.id.tvStandard);
                holder.tvAutofit = (AutofitListTextView) view.findViewById(R.id.tvAutofit);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            final ExampleStructureData current = (ExampleStructureData) getItem(position);
            if (current != null) {
                holder.tvStandard.setText(current.getLabel1());

                holder.tvAutofit.setText(current.getLabel2());
                holder.tvAutofit.setTextSizeBasedOnUsedLabels(getResources().getStringArray(R.array.data));
            }

            return view;
        }

    }
}
