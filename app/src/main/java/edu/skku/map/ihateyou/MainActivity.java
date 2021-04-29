package edu.skku.map.ihateyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    EditText search;
    TextView one;TextView two;TextView three;TextView five;TextView four;
    TextView six;TextView seven;TextView eight;TextView nine;TextView ten;
    TextView eleven;TextView twelve;TextView thirteen;
    TextView fourteen;TextView fifteen;TextView sixteen;TextView seventeen;TextView eighteen;TextView nineteen;TextView twenty;

    TextView one2;TextView two2;TextView three2;TextView five2;TextView four2;
    TextView six2;TextView seven2;TextView eight2;TextView nine2;TextView ten2;
    TextView eleven2;TextView twelve2;TextView thirteen2;
    TextView fourteen2;TextView fifteen2;TextView sixteen2;TextView seventeen2;TextView eighteen2;TextView nineteen2;TextView twenty2;


    int arr[][]=new int[20][20];
    String real[]=new String[20];
    int arr2[][]=new int[20][20];
    String real2[]=new String[20];
    //ImageView image;
    private static final int REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container=(LinearLayout)findViewById(R.id.container);
        Button button1=findViewById(R.id.button1);
        Button button2=findViewById(R.id.button2);

        one=findViewById(R.id.one);two=findViewById(R.id.two);three=findViewById(R.id.three);four=findViewById(R.id.four);five=findViewById(R.id.five);
        six=findViewById(R.id.six);seven=findViewById(R.id.seven);eight=findViewById(R.id.eight);nine=findViewById(R.id.nine);ten=findViewById(R.id.ten);
        eleven=findViewById(R.id.eleven);twelve=findViewById(R.id.twelve);thirteen=findViewById(R.id.thirteen);fourteen=findViewById(R.id.fourteen);fifteen=findViewById(R.id.fifteen);
        sixteen=findViewById(R.id.sixteen);seventeen=findViewById(R.id.seventeen);eighteen=findViewById(R.id.eighteen);nineteen=findViewById(R.id.nineteen);twenty=findViewById(R.id.twenty);

        one2=findViewById(R.id.one2);two2=findViewById(R.id.two2);three2=findViewById(R.id.three2);four2=findViewById(R.id.four2);five2=findViewById(R.id.five2);
        six2=findViewById(R.id.six2);seven2=findViewById(R.id.seven2);eight2=findViewById(R.id.eight2);nine2=findViewById(R.id.nine2);ten2=findViewById(R.id.ten2);
        eleven2=findViewById(R.id.eleven2);twelve2=findViewById(R.id.twelve2);thirteen2=findViewById(R.id.thirteen2);fourteen2=findViewById(R.id.fourteen2);fifteen2=findViewById(R.id.fifteen2);
        sixteen2=findViewById(R.id.sixteen2);seventeen2=findViewById(R.id.seventeen2);eighteen2=findViewById(R.id.eighteen2);nineteen2=findViewById(R.id.nineteen2);twenty2=findViewById(R.id.twenty2);
        //image=(ImageView)findViewById(R.id.image);

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                search=MainActivity.this.findViewById(R.id.search);
                OkHttpClient client=new OkHttpClient();

                String apiURL = "https://openapi.naver.com/v1/search/image?query=" + search.getText().toString() + "&display=1";
                Map<String, String> requestHeaders = new HashMap<>();
                requestHeaders.put("X-Naver-Client-Id", "4Aa18anN5fKBhpQEb0iz");
                requestHeaders.put("X-Naver-Client-Secret", "GWkjVXbi8E");

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        final String myResponse = get(apiURL, requestHeaders);
                        int one = myResponse.indexOf("[");
                        int two = myResponse.indexOf("]");

                        final String items = myResponse.substring(one + 1, two);

                        Gson gson = new GsonBuilder().create();
                        DataModel data = gson.fromJson(items, DataModel.class);

                        String imgURL = data.getLink();
                        Bitmap bitmapImg = GetImageFromURL(imgURL);

                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //image.setImageBitmap(bitmapImg);

                                Bitmap result[][]=cropBitmap(bitmapImg);

                                int index = 0;

                                for (int i = 0; i < 20; i++) {
                                    for (int j = 0; j < 20; j++) {
                                        mThumbs[index] = result[i][j];//2차원 배열 사진을 1차원 배열로 바꿔주고
                                        White[index] = whiteScale(mThumbs[index]);//새로운 1차원 배열에 사진을 흰색으로 바꿔준다.
                                        index++;
                                    }
                                }

                                ///가로줄
                                int pos2=0;
                                int check2=0;

                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        check2=Check.two(pos2, mThumbs);
                                        if(check2==0){
                                            arr2[i][j]=1;
                                        }
                                        else{
                                            arr2[i][j]=0;
                                        }
                                        pos2++;
                                    }
                                }

                                int backcheck2[][]=new int[20][20];
                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        backcheck2[j][i]=0;
                                    }
                                }
                                int stt2=0;

                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        if(arr2[j][i]==1){
                                            backcheck2[stt2][i]=backcheck2[stt2][i]+1;
                                        }
                                        else{
                                            stt2++;
                                        }
                                    }
                                    stt2=0;
                                }
                                for(int i=0;i<20;i++){
                                    real2[i]="";
                                }

                                for(int i=0;i<20;i++){
                                    StringBuilder str22 = new StringBuilder();
                                    for(int j=0;j<20;j++){
                                        if(backcheck2[j][i]==0){
                                            continue;
                                        }
                                        else{
                                            str22.append(backcheck2[j][i]);
                                            str22.append("   ");
                                        }
                                    }
                                    real2[i]=str22.toString();
                                }

                                //세로줄

                                //세로줄

                                int pos=0;
                                int check=0;

                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        check=Check.two(pos, mThumbs);
                                        if(check==0){
                                            arr[i][j]=1;
                                        }
                                        else{
                                            arr[i][j]=0;
                                        }
                                        pos++;
                                    }
                                }
                                int backcheck[][]=new int[20][20];
                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        backcheck[i][j]=0;
                                    }
                                }
                                int stt=0;

                                for(int i=0;i<20;i++){
                                    for(int j=0;j<20;j++){
                                        if(arr[i][j]==1){
                                            backcheck[i][stt]=backcheck[i][stt]+1;
                                        }
                                        else{
                                            stt++;
                                        }
                                    }
                                    stt=0;
                                }

                                for(int i=0;i<20;i++){
                                    StringBuilder str2 = new StringBuilder();
                                    for(int j=0;j<20;j++){
                                        if(backcheck[i][j]==0){
                                            continue;
                                        }
                                        else{
                                            str2.append(backcheck[i][j]);
                                            str2.append(" ");
                                        }
                                    }
                                    real[i]=str2.toString();
                                }

                                change(1);
                            }
                        });
                    }
                }).start();

            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    //image.setImageBitmap(img);

                    Bitmap result[][]=cropBitmap(img);

                    int index = 0;

                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 20; j++) {
                            mThumbs[index] = result[i][j];//2차원 배열 사진을 1차원 배열로 바꿔주고
                            White[index] = whiteScale(mThumbs[index]);//새로운 1차원 배열에 사진을 흰색으로 바꿔준다.
                            index++;
                        }
                    }

                    ///가로줄
                    int pos2=0;
                    int check2=0;

                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            check2=Check.two(pos2, mThumbs);
                            if(check2==0){
                                arr2[i][j]=1;
                            }
                            else{
                                arr2[i][j]=0;
                            }
                            pos2++;
                        }
                    }

                    int backcheck2[][]=new int[20][20];
                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            backcheck2[j][i]=0;
                        }
                    }
                    int stt2=0;

                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            if(arr2[j][i]==1){
                                backcheck2[stt2][i]=backcheck2[stt2][i]+1;
                            }
                            else{
                                stt2++;
                            }
                        }
                        stt2=0;
                    }
                    for(int i=0;i<20;i++){
                        real2[i]="";
                    }

                    for(int i=0;i<20;i++){
                        StringBuilder str22 = new StringBuilder();
                        for(int j=0;j<20;j++){
                            if(backcheck2[j][i]==0){
                                continue;
                            }
                            else{
                                str22.append(backcheck2[j][i]);
                                str22.append("   ");
                            }
                        }
                        real2[i]=str22.toString();
                    }

                    //세로줄

                    //세로줄

                    int pos=0;
                    int check=0;

                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            check=Check.two(pos, mThumbs);
                            if(check==0){
                                arr[i][j]=1;
                            }
                            else{
                                arr[i][j]=0;
                            }
                            pos++;
                        }
                    }
                    int backcheck[][]=new int[20][20];
                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            backcheck[i][j]=0;
                        }
                    }
                    int stt=0;

                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            if(arr[i][j]==1){
                                backcheck[i][stt]=backcheck[i][stt]+1;
                            }
                            else{
                                stt++;
                            }
                        }
                        stt=0;
                    }

                    for(int i=0;i<20;i++){
                        StringBuilder str2 = new StringBuilder();
                        for(int j=0;j<20;j++){
                            if(backcheck[i][j]==0){
                                continue;
                            }
                            else{
                                str2.append(backcheck[i][j]);
                                str2.append(" ");
                            }
                        }
                        real[i]=str2.toString();
                    }

                    change(1);

                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    Bitmap [] mThumbs=new Bitmap[400];
    Bitmap [] White=new Bitmap[400];
    int count=0;
    int test=0;

    public void change(int button){
        if(button==1){
            one.setText(String.valueOf(real2[0]));two.setText(String.valueOf(real2[1]));three.setText(String.valueOf(real2[2]));four.setText(String.valueOf(real2[3]));five.setText(String.valueOf(real2[4]));
            six.setText(String.valueOf(real2[5]));seven.setText(String.valueOf(real2[6]));eight.setText(String.valueOf(real2[7]));nine.setText(String.valueOf(real2[8]));ten.setText(String.valueOf(real2[9]));
            eleven.setText(String.valueOf(real2[10]));twelve.setText(String.valueOf(real2[11]));thirteen.setText(String.valueOf(real2[12]));fourteen.setText(String.valueOf(real2[13]));fifteen.setText(String.valueOf(real2[14]));
            sixteen.setText(String.valueOf(real2[15]));seventeen.setText(String.valueOf(real2[16]));eighteen.setText(String.valueOf(real2[17]));nineteen.setText(String.valueOf(real2[18]));twenty.setText(String.valueOf(real2[19]));

            one2.setText(String.valueOf(real[0]));two2.setText(String.valueOf(real[1]));three2.setText(String.valueOf(real[2]));four2.setText(String.valueOf(real[3]));five2.setText(String.valueOf(real[4]));
            six2.setText(String.valueOf(real[5]));seven2.setText(String.valueOf(real[6]));eight2.setText(String.valueOf(real[7]));nine2.setText(String.valueOf(real[8]));ten2.setText(String.valueOf(real[9]));
            eleven2.setText(String.valueOf(real[10]));twelve2.setText(String.valueOf(real[11]));thirteen2.setText(String.valueOf(real[12]));fourteen2.setText(String.valueOf(real[13]));fifteen2.setText(String.valueOf(real[14]));
            sixteen2.setText(String.valueOf(real[15]));seventeen2.setText(String.valueOf(real[16]));eighteen2.setText(String.valueOf(real[17]));nineteen2.setText(String.valueOf(real[18]));twenty2.setText(String.valueOf(real[19]));

            LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_second2,container,true);

            GridView gridView=(GridView)findViewById(R.id.gridview);
            //gridView.setAdapter(new ImageAdapter(this));
            ImageAdapter adapter1=new ImageAdapter(this);
            gridView.setAdapter(adapter1);

            //TextView text1=findViewById(R.id.text1);
            //text1.setText(blackorwhite[0][0]+"");

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(Check.four(position, White, mThumbs)==0){//정답과 다른 grid를 누르면
                        int index=0;
                        for(int i=0;i<20;i++){
                            for(int j=0;j<20;j++){
                                White[index]=whiteScale(mThumbs[index]);//다 흰색으로 바꿔주기
                                index++;
                            }
                        }
                        test=0;//클릭한 블럭 수 초기화
                        gridView.setAdapter(adapter1);
                    }
                    else{//정답과 같은 grid를 누르면
                        Check.three(position, White);//색을 검은색으로 바꿔주기
                        test++;
                        gridView.setAdapter(adapter1);
                    }

                    if(test==count){//정답의 블록 수 만큼 클릭을 해주면 메시지 띄우기
                        Toast.makeText(getApplicationContext(), "FINISHED!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c){
            mContext=c;
        }

        @Override
        public int getCount() { return mThumbs.length; }
        @Override
        public Object getItem(int position) { return null; }
        @Override
        public long getItemId(int position) { return 0; }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imgView;
            if(convertView==null){
                imgView=new ImageView(mContext);
                imgView.setLayoutParams(new ViewGroup.LayoutParams(35,35));
                imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgView.setPadding(0,0,0,0);
            }
            else{
                imgView=(ImageView)convertView;
            }
            imgView.setImageBitmap(White[position]);
            //imgView.setImageBitmap(mThumbs[position]);
            return imgView;
        }
    }

    public Bitmap[][] cropBitmap(Bitmap original){
        Bitmap result[][]=new Bitmap[20][20];
        int l=original.getWidth();
        int h=original.getHeight();
        int picW=l/20;
        int picH=h/20;

        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                result[i][j]=grayScale(Bitmap.createBitmap(original, j*picW, i*picH, picW, picH));
                //result[i][j]=Bitmap.createBitmap(original, j*picW, i*picH, picW, picH);
            }
        }
        return result;
    }

    private Bitmap grayScale(final Bitmap orgBitmap){//사진을 흑백으로 바꿔주는 함수
        int width, height;
        width = orgBitmap.getWidth();
        height = orgBitmap.getHeight();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        int A, R, G, B;
        int pixel;

        pixel = orgBitmap.getPixel(width/2, height/2);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

        if (gray > 128){
            gray = 255;
        }
        else{
            count++;
            gray = 0;
        }

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                bmpGrayScale.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmpGrayScale;
    }

    private Bitmap whiteScale(final Bitmap orgBitmap){//사진을 모두 흰색으로 바꿔주는 함수
        int width, height;
        width = orgBitmap.getWidth();
        height = orgBitmap.getHeight();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        // color information
        int pixel = orgBitmap.getPixel(width/2, height/2);
        int A = Color.alpha(pixel);

        int gray = 0;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                bmpGrayScale.setPixel(x, y, Color.argb(A, 225, 225, 225));
            }
        }
        return bmpGrayScale;
    }

    public static class Check {
        public static int two(int position, Bitmap[] mThumbs3){
            int pixel;
            int width, height;
            width = mThumbs3[position].getWidth();
            height = mThumbs3[position].getHeight();
            pixel=mThumbs3[position].getPixel(width/2, height/2);

            int R = Color.red(pixel);
            int G = Color.green(pixel);
            int B = Color.blue(pixel);
            int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

            if (gray > 128)
                gray = 255;
            else
                gray = 0;

            if(gray==0){
                return 0;
            }

            else{
                return 1;
            }
        }
        public static int four(int position, Bitmap[] mThumbs3, Bitmap[] model){
            int pixel;
            int width, height;
            width = mThumbs3[position].getWidth();
            height = mThumbs3[position].getHeight();
            pixel=mThumbs3[position].getPixel(width/2, height/2);

            int R = Color.red(pixel);
            int G = Color.green(pixel);
            int B = Color.blue(pixel);
            int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

            if (gray > 128)
                gray = 255;
            else
                gray = 0;

            int mpixel;
            mpixel=model[position].getPixel(width/2, height/2);

            int mR = Color.red(mpixel);
            int mG = Color.green(mpixel);
            int mB = Color.blue(mpixel);
            int mgray = (int) (0.2989 * mR + 0.5870 * mG + 0.1140 * mB);

            if (mgray > 128)
                mgray = 255;
            else
                mgray = 0;

            if (gray==mgray){
                return 0;
            }

            return 1;

        }
        public static void three(int position, Bitmap[] mThumbs3) {
            int pixel;
            int width, height;
            width = mThumbs3[position].getWidth();
            height = mThumbs3[position].getHeight();
            pixel=mThumbs3[position].getPixel(width/2, height/2);

            int A = Color.alpha(pixel);
            int R = Color.red(pixel);
            int G = Color.green(pixel);
            int B = Color.blue(pixel);
            int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

            if (gray > 128)
                gray = 255;
            else
                gray = 0;

            if(gray>128){
                gray=0;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        mThumbs3[position].setPixel(x, y, Color.argb(A, gray, gray, gray));
                    }

                }
            }
            else{
                gray=225;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        mThumbs3[position].setPixel(x, y, Color.argb(A, gray, gray, gray));
                    }
                }
            }
        }
    }

    private Bitmap GetImageFromURL(String imgURL) {
        Bitmap imgBitmap=null;
        try {
            URL url = new URL(imgURL);
            URLConnection conn = url.openConnection();
            conn.connect();

            int size=conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), size);
            imgBitmap = BitmapFactory.decodeStream(bis);

            bis.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return imgBitmap;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }




}