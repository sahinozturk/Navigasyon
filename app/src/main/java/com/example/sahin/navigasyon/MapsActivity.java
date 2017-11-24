package com.example.sahin.navigasyon;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,NavigationView.OnNavigationItemSelectedListener
{
    LatLng latLng2;
    LatLng latLng;
    double enlem0;
    double enlem;
    double boylam;
    double boylam0;
    private Context cntx;
    ImageButton btn;
    GoogleMap map;
    EditText etBasla, etBitis;

    private ViewGroup infoWindow;
    //toolbar nesnemizi oluşturduk
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    JSONArray res = new JSONArray();
    //  private WeakHashMap<Marker, Image> hashMap;  incele :https://medium.com/@iammert/log-v-threetips-2-fc4d3eb7e0dc

    Map<String, String> imgMap = new HashMap<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Açılır menü işaretini ve o işarate tıklanınca menünün açılması gerektiğini  ActionBarDrawerToggle ile ayarlıyoruz
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle=
                new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                        R.string.drawer_open, R.string.drawer_close);
        //ikonun kullanım sırasında şekil değiştirmesini saglıyor
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        etBasla = (EditText) findViewById(R.id.etBasla);
        etBitis = (EditText) findViewById(R.id.etBitis);

       // btn=(ImageButton)infoWindow.findViewById(R.id.btnrotayaekle);
        //Fragmente ulaşıyoruz
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //HashMap dizimin içerisine tıklanan marker a ait id ve karşılığında çağrılacak resim yolu atanmıştır.
        imgMap.put("m0", "denizcilikmuzesi.jpg");
        imgMap.put("m2", "dolmabahcesarayi.jpg");
        imgMap.put("m3", "denizcilikmuzesi.jpg");

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        String itemAdi=(String)item.getTitle();

        Log.e("adi","item :"+itemAdi);


        //istediğimiz menü sayfası açıldığı zaman navibarın kapanması gerekiyor bu yüzden aşağıdaki metodu oluşturarak çağırıyoruz
        navigetionviewKapat();

        //Menü elemanlarından birine tıkladığım zaman çalışmasını istediğim bir eylem varsa onu switch case yapısı ile gerçekleştirebilirim
        switch (item.getItemId())
        {
            case R.id.item_gezirotalari:
                {


                    Intent in=new Intent(MapsActivity.this,Gezilecek_Rota.class);
                    startActivity(in);
                    Log.e("item_android","id "+item.getItemId());
                    break;
                }

            case R.id.item_blackberry:
                break;
        }
        return false;
    }
    public void navigetionviewKapat()
    {
        //başlangıç noktasına doğru kapat
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    // navibarın açılmasını istediğimiz zaman kullanabilelim diye bir metot oluşturduk şart değil ilerleyen yerlerde kullanmamız
    //gerekebilir
    public void navigetionviewAc()
    {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    /*onBackPressed metodu cihazın back butonuna tıklanınca devreye giren bir metotdur
    * biz bu metodu çağırmazsak navibar menümüzden çıkmak için back butonuna tıklanınca uygulama tamamen kapanır
    * amacımız navibar açık durumda basılmış ise sadece navibarı kapatsın eğer navibar açık değil ise
    * uygulamadan çıkılacaktır
    * */
    @Override
    public void onBackPressed() {
        //eğer benim Drawer yani navibar açık ise geri butonuna basılınca navi barı kapat
         if(drawerLayout.isDrawerOpen(GravityCompat.START))
             navigetionviewKapat();
        super.onBackPressed();
    }

    //kullanıcı donov buutonuna bastığı zaman enlem ve boylamları alıyoruz
    public void doNav(View view) {
        final String basla = etBasla.getText().toString();
        final String bitis = etBitis.getText().toString();

        Log.e("x", "Basşlangıç alanı : " + basla);
        Log.e("x", "bitis  alanı : " + bitis);
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return ;
        }
        map.setMyLocationEnabled(true);


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
                }
                catch(Exception e)
                { }
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
                            }
                            catch (IOException e)
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



                }
                catch (Exception e) { Log.e("x","MAP PARSE EX : "+e); }

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

        //harita üzerinde özellik verdiğim noktaları burada belirledim
        LatLng latLng11=new LatLng(enlemim,boylamım);
        map.addMarker(new MarkerOptions().position(latLng11).title("Süleymaniye cami en güzel camilerden biridir  "));
        LatLng latLng12=new LatLng(enlemim1,boylamım1);
        map.addMarker(new MarkerOptions().position(latLng12).title("Denizcilik Müzesi")
                .snippet("Türkiye'nin denizcilik alanında en büyük müzesi ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                View v=null;

                try
                {

                    v=getLayoutInflater().inflate(R.layout.custom_infowindow,null);
                    TextView txtadress=(TextView)v.findViewById(R.id.txtname);
                    txtadress.setText(marker.getTitle());
                    ImageView img=(ImageView)v.findViewById(R.id.tanitimresmi);
                    Log.e("adres id","sss :"+marker.getId());

                    //hashmap dizinin boyutunu alırız
                    int iMs=imgMap.size();

                    //tıklanan marker id değeri bu şekilde alınır
                    String getid=""+marker.getId();

                    //eğer hashmap içerisinde bu yeri işlemişsek   bize boş dönmeyecek ve istediğimiz değeri alabileceğiz
                       if(imgMap.get(getid)!=null)
                       {
                           Log.e("  xx  ","yırttık kefeni "+imgMap.get(getid));
                   /*        String addd=imgMap.get(getid);
                        String adree="C:\\Users\\Sahin\\Desktop\\Navigasyon1\\app\\src\\main\\res\\drawable\\";*/

                          // String path = getApplication().getFilesDir().getAbsolutePath();

                          /* InputStream is = new FileInputStream(adree +""+addd);
                           Drawable icon = new BitmapDrawable(is);
                           img.setImageDrawable(icon);*/




                            //Deneme amaçlı marker içerisindeki resim dosyasını değiştirme kodu

                           if (imgMap.get(getid)==imgMap.get("m0"))
                           {
                               img.setImageResource(R.drawable.denizcilikmuzesi);
                           }
                           else if (imgMap.get(getid)==imgMap.get("m2"))
                           {
                               img.setImageResource(R.drawable.dolmabahcesarayi);
                           }
                       }

                  //resimleri biçimlendirmek için ilerleyen dönemlerde kullanacağız
                 //   Picasso.with(cntx).load("http://kingfisher.scene7.com/is/image/Kingfisher/4051315123607_01c?$PROMO_60_60$").into(img);





                    map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(final Marker marker) {

                            Log.e("başlık "+marker.getTitle(),"ne bileyim ben");

                        }
                    });


                }
                catch (Exception e)
                {
                    Log.e("infowindow ","hata : "+e);
                }


                return v;
            }
        });

    }


    private List<LatLng> decodePoly(String encoded)
    {

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
