package com.khair.myviewholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerview;

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    ArrayList<HashMap<String,String>>finalarrayList=new ArrayList<>();

    HashMap<String,String>hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview=findViewById(R.id.recyclerview);
        lodData();
        createItem();

        MyAdApter myAdApter=new MyAdApter();
        recyclerview.setAdapter(myAdApter);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        MobileAds.initialize(this);




    }
    //=================================================================================
    public class MyAdApter extends RecyclerView.Adapter{

        int VideoItem=0;
        int BookItem=1;
        int NativeAd=2;


        private class BookViewHolder extends RecyclerView.ViewHolder{
            TextView tv_righter,tv_title;
            Button button;
            ImageView imageView2;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_righter=itemView.findViewById(R.id.tv_righter);
                tv_title=itemView.findViewById(R.id.tv_title);
                button=itemView.findViewById(R.id.button);
                imageView2=itemView.findViewById(R.id.imageView2);
            }
        }

        private class VideoViewHolder extends RecyclerView.ViewHolder{
            TextView tv_videoId;
            ImageView imageView;

            public VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.imageView);
                tv_videoId=itemView.findViewById(R.id.tv_videoId);

            }
        }

        public class Native_AdViewHolder extends RecyclerView.ViewHolder{
            TemplateView template;

            public Native_AdViewHolder(@NonNull View itemView) {
                super(itemView);
          template = itemView.findViewById(R.id.my_template);


            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();

            if (viewType==VideoItem){
                View myView=inflater.inflate(R.layout.video_item,parent,false);
                return new VideoViewHolder(myView);
            }else  if (viewType==NativeAd){
                View myView=inflater.inflate(R.layout.native_ad,parent,false);
                return new Native_AdViewHolder(myView);
            }
            else{
                View myView=inflater.inflate(R.layout.book_item,parent,false);
                return new BookViewHolder(myView);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position)==VideoItem){
                VideoViewHolder myHolder= (VideoViewHolder) holder;
                hashMap=finalarrayList.get(position);
                String VideoTitle=hashMap.get("VideoTitle");
                String VideoId=hashMap.get("VideoId");
                String imagesUrl="https://img.youtube.com/vi/"+VideoId+"/0.jpg";

                myHolder.tv_videoId.setText(VideoTitle);
                Picasso.get().load(imagesUrl).into(myHolder.imageView);
                
            } else if (getItemViewType(position)==BookItem) {
                BookViewHolder bookViewHolder= (BookViewHolder) holder;
                hashMap=finalarrayList.get(position);
                String BookTitle=hashMap.get("BookTitle");
                String BookRighter=hashMap.get("BookRighter");
                String BookImages=hashMap.get("BookImages");

                bookViewHolder.tv_righter.setText(BookRighter);
                bookViewHolder.tv_title.setText(BookTitle);
                Picasso.get().load(BookImages).into(bookViewHolder.imageView2);
                bookViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "hello iam fine", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if (getItemViewType(position)==NativeAd){
                Native_AdViewHolder nativeAdViewHolder= (Native_AdViewHolder) holder;
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
                 .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                 @Override
                public void onNativeAdLoaded(NativeAd nativeAd) {

                 nativeAdViewHolder.template.setNativeAd(nativeAd);
                }
                })
                .build();

                 adLoader.loadAd(new AdRequest.Builder().build());
            }

        }

        @Override
        public int getItemCount() {
            return finalarrayList.size();

        }

        @Override
        public int getItemViewType(int position) {
            hashMap = finalarrayList.get(position);
            String itemType = hashMap.get("itemType");

            if (itemType.contains("Video"))return VideoItem;
             else if (itemType.contains("NativeAd"))return NativeAd;
              else return BookItem;





        }
    }
///============== RecyclerViewAdApter end here =============================================
    public void lodData(){
        arrayList=new ArrayList<>();

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);



        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);




        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);




        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Video");
        hashMap.put("VideoId","C8kSrkz8Hz8");
        hashMap.put("VideoTitle","Kamli Song | Dhoom:3 | Katrina Kaif, Aamir Khan ");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);

        hashMap=new HashMap<>();
        hashMap.put("itemType","Book");
        hashMap.put("BookTitle","মোবাইল অ্যাপে ক্যারিয়ার (হার্ডকভার)");
        hashMap.put("BookRighter","জুবায়ের হোসেন");
        hashMap.put("BookImages","https://cdn.pixabay.com/photo/2014/11/25/23/04/iphone-545772_640.jpg");
        arrayList.add(hashMap);


    }

    ////=========================================================================================
    public void createItem(){
        finalarrayList=new ArrayList<>();
        for (int x=0;x<arrayList.size();x++){

            if (x==1||x==7||x==11){
                hashMap=new HashMap<>();
                hashMap.put("itemType","NativeAd");
                finalarrayList.add(hashMap);
            }
            hashMap=arrayList.get(x);
            finalarrayList.add(hashMap);

        }


    }





//=====================================================================================
}