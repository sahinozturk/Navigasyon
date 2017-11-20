package com.example.sahin.navigasyon;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    LatLng latLng2;
    LatLng latLng;
    double enlem0;
    double enlem;
    double boylam;
    double boylam0;

    GoogleMap map;
    EditText etBasla, etBitis;
    JSONArray res = new JSONArray();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        etBasla = (EditText) findViewById(R.id.etBasla);
        etBitis = (EditText) findViewById(R.id.etBitis);

        //Fragmente ulaşıyoruz
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    //kullanıcı donov buutonuna bastığı zaman enlem ve boylamları alıyoruz
    public void doNav(View view)
    {
        final String basla = etBasla.getText().toString();
        final String bitis = etBitis.getText().toString();

        Log.e("x","Basşlangıç alanı : "+basla);
        Log.e("x","bitis  alanı : "+bitis);

        new AsyncTask<String, String, String>()
        {
            ProgressDialog pd;

            //Ara yüzde istediğimiz değişikliği yapabilmemizi sağlıyor
            protected void onPreExecute()
            {
                //Aşağıda ki yolla dialoğ gösterebiliyoruz
                pd = new ProgressDialog(MapsActivity.this);
                pd.setMessage("Lütfen Bekleyin");
                pd.setCancelable(false);
                pd.show();
            }

            //Arayüzü kitlemeden arka planda işlem yapmamıza olanak sağlayan metotdur
            //AsyncTAskın çağrılan metodudur
            protected String doInBackground(String... strings)
            {
                try
                {
                    // jsoup arraye bağlanıp jsonu çekmemiz için aşağıdaki yolu kullanıyoruz
                    String adr = "https://maps.googleapis.com/maps/api/directions/json?origin="+basla+"&destination="+bitis;
                    String jsonStr = Jsoup.connect(adr)
                            .timeout(30000)
                            .userAgent("Mozilla")
                            .ignoreContentType(true)
                            .get()
                            .text();


                    //Çektiğimiz jsonun içinde birden fazla array var bizim istediğimiz
                    // routes arrayi --> onunda bir çok obje varmış
                    // o objelerin içinden ilk olanına ulaşıp içindeki legs arrayine ulaşıyorum
                    // bu arrayin içinde başka bir obje var onada erişip asıl istediğim arraya ulaşıyorum
                    //steps arrayı bize istediğim sonucu dönderecek
                    res = new JSONObject(jsonStr)
                            .getJSONArray("routes")
                            .getJSONObject(0)
                            .getJSONArray("legs")
                            .getJSONObject(0)
                            .getJSONArray("steps");

                    Log.e("x","Response : "+jsonStr);
                }catch(Exception e) { }
                return null;
            }

            //İşlemlerimiz bittiği zaman  çalışan metotdur
            protected void onPostExecute(String s)
            {
                try
                {

                    //steplerin içinde distance adında objeler var bu objenin içinde içinde
                    //text ve  value değerleri var bu değerlere ihtiyacımız var
                    for (int i = 0; i<res.length(); i++)
                    {
                        //distancenin içindeki text değeri bize dönüşlerden önce gideceğimiz mesafelerin
                        //bilgilerini vermekte
                        JSONObject step = res.getJSONObject(i);
                        String distance = step.getJSONObject("distance")
                                .getString("text");
                        //duration içerisindeki texte erişerek uyarı ekranında veya marker
                        // üzerinde paylaşacağım yolün süre bilgisini alabilirim

                        String duration = step.getJSONObject("duration")
                                .getString("text");

                        //html_instructions içerisinde açıklama metni olduğunu farkettik ve onuda çekiyoruz
                        String aciklama = step.getString("html_instructions");

                        // end_location içerisindeki enlem ve boylam bilgilerini çekiyoruz
                          enlem = step.getJSONObject("end_location")
                                .getDouble("lat");
                     boylam = step.getJSONObject("end_location")
                                .getDouble("lng");



                         enlem0 = step.getJSONObject("start_location")
                                .getDouble("lat");

                        boylam0 = step.getJSONObject("start_location")
                                .getDouble("lng");


                        //jsonu çekmeye devam ediyoruz
                        //json un içinde bir polyline diye objem var onun içindede points diye bir stringim var onun içindeki veriyi çekerek yol çizeceğim
                        String points = step.getJSONObject("polyline")
                                .getString("points");


                        // noktaları bir array içerisinde tuttuk
                        List<LatLng> noktalar = decodePoly(points);
                        //polylineoptions nesnesi oluşturduk

                        PolylineOptions po = new PolylineOptions();

                        //bu noktaları polylineoptionsa ekliyorum
                        for (int q = 0; q<noktalar.size(); q++)
                            po.add(noktalar.get(q));
                        //po nun nasıl bir çizgi görünümünde olmasın gerektiğini belirliyorum
                        po.width(8.0f);
                        po.color(Color.BLUE);
                        map.addPolyline(po);
                        EditText etLocation=(EditText)findViewById(R.id.etBasla);
                        EditText etbitis=(EditText)findViewById(R.id.etBitis);
                        String location=etLocation.getText().toString();
                        String location2=etbitis.getText().toString();




                            List<Address> adressList=null;
                        List<Address> adressList1=null;
                            Geocoder geocoder=new Geocoder(MapsActivity.this);
                            try
                            {

                                adressList1=geocoder.getFromLocationName(location,1);
                                adressList=geocoder.getFromLocationName(location2,2);

                                Address address=adressList1.get(0);
                                 latLng=new LatLng(address.getLatitude(),address.getLongitude());
                                Log.e("x","başlangıç noktası **** : "+location);

                                Address address2=adressList.get(0);
                                Log.e("x","bitiş noktası ***** : "+location2);
                                 latLng2=new LatLng(address2.getLatitude(),address2.getLongitude());

                                map.addMarker(new MarkerOptions().position(latLng).title("burası başlangıç noktası  "));
                                map.addMarker(new MarkerOptions().position(latLng2).title("burası bitiş noktası"));
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

                                Log.e("x","başlangıç noktası  : "+latLng);
                                Log.e("x","bitiş noktası  : "+latLng2);
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }




                    }

                        // haritaya başlangıç konumunu ekliyorum
                        LatLng konum0 = new LatLng(enlem0, boylam0);
                        /* MarkerOptions mo0  =new MarkerOptions()
                                .title("START : "+distance+" / "+duration)
                                .snippet(aciklama)
                                .position(konum0); */
                      //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(konum0, 14));



                       LatLng konum = new LatLng(enlem, boylam);
                       /* MarkerOptions mo  =new MarkerOptions()
                                .title("END : "+distance+" / "+duration)
                                .snippet(aciklama)
                                .position(konum);*/

                       // map.addMarker(new MarkerOptions().position(konum).title("burası bitiş noktası  "));


                        addMarkerim();
                        //çizdiğim yolu haritaya ekliyorum



                } catch (Exception e) { Log.e("x","MAP PARSE EX : "+e); }

                pd.dismiss();
            }
        }.execute();//AsnycTaskı çalıştırmak için execute kullanıyoruz
    }


    //Google mapi burada çağırıyoruz
    public void onMapReady(GoogleMap googleMap)
    {
        this.map = googleMap;
    }


    public void addMarkerim()
    {



            double enlemim=41.015982;
            double enlemim1=41.0416152;
                double boylamım=28.9653091;
                double boylamım1=29.0047815;


        LatLng latLng11=new LatLng(enlemim,boylamım);
        map.addMarker(new MarkerOptions().position(latLng11).title("Süleymaniye cami en güzel camilerden biridir  "));
        LatLng latLng12=new LatLng(enlemim1,boylamım1);
        map.addMarker(new MarkerOptions().position(latLng12).title("Denizcilik Müzesi").snippet("Türkiye'nin denizcilik alanında en büyük müzesi " +
                "olan Deniz Müzesi, içerdiği koleksiyon çeşitliliği açısından dünyanın sayılı müzelerinden birisidir.\n" +
                "\n" +
                "MÜZENİN AÇIK OLDUĞU GÜN VE SAATLER\n" + "\n"+
                "Yılbaşı, dini bayramların ilk günü ve Pazartesi günleri kapalı olup; haftaiçi" + "\n"+
                " 09:00-17:00, haftasonu 10:00-18:00 saatleri arasında müzemizi ziyaret edebilirsiniz.  "));

    }


    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
