package com.example.sahin.navigasyon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class SehirAktiviti extends AppCompatActivity {
    ListView urunListView;
    BaseAdapter urunBaseAdapter;
    LayoutInflater urunLayoutInflater;
    JSONObject veriler=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehir_aktiviti);
        urunLayoutInflater = LayoutInflater.from(SehirAktiviti.this);
        urunListView = (ListView) findViewById(R.id.lv);
        String url="http://jsonbulut.com/json/product.php?ref=d3620092c9f5b4310e3dff52f36c4398&start=0";

        new JsonData(url, this).execute();
    }
    class JsonData extends AsyncTask<Void,Void,Void>
    {
        String url = "";
        String data = "";
        ProgressDialog pro;
        Context cnx;
        public JsonData(String url, Context cnx)
        {
            this.url = url;
            this.cnx = cnx;
            pro = new ProgressDialog(cnx);
            pro.setMessage("İşlem Yapılıor, Lütfen Bekleyiniz");
            pro.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try {

                data = Jsoup.connect(url).ignoreContentType(true).get().body().text();

            }catch (Exception ex) {
                Log.e("Data Json Hatası", "doInBackground: ",ex );
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid)

        {
            ;super.onPostExecute(aVoid);

            try
            {

                JSONObject obj=new JSONObject(data);
                final JSONArray bilgiler=obj.getJSONArray("Products").getJSONObject(0).getJSONArray("bilgiler");

                urunBaseAdapter=new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return bilgiler.length();
                    }

                    @Override
                    public Object getItem(int position) {
                        return null;
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup)
                    {
                        if(view == null) {
                            view = urunLayoutInflater.inflate(R.layout.picassolist, null);
                        }

                        try
                        {
                            ImageView img=(ImageView)view.findViewById(R.id.logo);

                            TextView txtbaslik=(TextView)view.findViewById(R.id.baslik23);

                            veriler=bilgiler.getJSONObject(i);
                            String imgurl = veriler.getJSONArray("images").getJSONObject(0).getString("normal");

                            String baslik=veriler.getString("productName");
                            Log.e("baslikkkll  :","1");
                            txtbaslik.setText(baslik);

                            Picasso.with(cnx).load(imgurl).into(img);
                        }
                        catch (Exception e)
                        {

                        }

                        return view;
                    }
                };
                urunListView.setAdapter(urunBaseAdapter);
                urunListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view,
                                            int i, long l)
                    {
                        try
                        {
                            String sayfaUrl=veriler.getString("brief");
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(sayfaUrl));
                            startActivity(intent);

                        }
                        catch (Exception e)
                        {}


                    }
                });


            }
            catch (Exception e )
            {

            }pro.dismiss();
        }
    }

}
