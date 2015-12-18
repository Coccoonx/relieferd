package com.lri.eeclocalizer.uis;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lri.eeclocalizer.R;

public class ParishDetails extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_parish_details);
//
//        List<String> myContent = new ArrayList<String>(); // or another object list
//        ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<String>(content) {
//            @Override
//            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
//                // If you're using your custom handler (as you should of course)
//                // you need to cast viewHolder to it.
//                ((MyCustomViewHolder) viewHolder).textView.setText(myContent.get(i)); // your bind holder routine.
//            }
//
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<String> adapter, int i) {
//                // Here is where you inflate your row and pass it to the constructor of your ViewHolder
//                return new MyCustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myRow, viewGroup, false));
//            }
//
//            @Override
//            public int getItemCountImpl(ParallaxRecyclerAdapter<String> adapter) {
//                // return the content of your array
//                return myContent.size();
//            }
//        };

//        myAdapter.setParallaxHeader(LayoutInflater.from(this).inflate(
//            R.layout.myParallaxView, myRecycler, false), myRecyclerView);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parish_details, menu);
        return true;
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
}
