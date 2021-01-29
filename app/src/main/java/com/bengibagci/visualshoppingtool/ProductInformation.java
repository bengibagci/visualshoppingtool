package com.bengibagci.visualshoppingtool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

//import android.widget.ProgressBar;

public class ProductInformation extends AsyncTask<Void,Void,Void> {

    private String url;
    private String term;
    private String btn;

    private Context context;
    public static List<ProductModel> marketItems;
    private String market, title, price;
    //ProgressBar progressBar;

    public ProductInformation(Context context, String term, String btn) {
        this.context = context;
        marketItems = new ArrayList<>();

        this.term = term;
        this.btn = btn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //progressBar gelecek
    }

    protected Void doInBackground(Void... voids) {

        try{
            String yourURLStr = "";
            Document doc;

            switch (btn){
                case "Migros":
                    yourURLStr = "https://www.migros.com.tr/arama?q=" + java.net.URLEncoder.encode(term, "UTF-8");
                    url = new java.net.URL(yourURLStr).toString();
                    doc = Jsoup.connect(url).get();
                    goMigros(doc);
                    break;
                case "A101":
                    yourURLStr = "https://www.a101.com.tr/list/?search_text=" + java.net.URLEncoder.encode(term, "UTF-8");
                    url = new java.net.URL(yourURLStr).toString();
                    doc = Jsoup.connect(url).get();
                    goA101(doc);
                    break;
                case "ŞOK":
                    yourURLStr = "https://www.ceptesok.com/arama/" + java.net.URLEncoder.encode(term, "UTF-8") + "#/";
                    url = new java.net.URL(yourURLStr).toString();
                    doc = Jsoup.connect(url).get();
                    goSOK(doc);
                    break;
            }
        } catch (SocketTimeoutException | ProtocolException e){
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (UnknownHostException | ConnectException e){
            e.printStackTrace();
        } catch (IOException | RuntimeException e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ProductAdapter adapter = new ProductAdapter(context, marketItems);
        SearchFragment.productList.setAdapter(adapter);
        ShoppingListFragment.productList.setAdapter(adapter);
    }

    private void goMigros(Document doc) throws IOException {

        for (Element elem : doc.select("div.product-card-center")) {
            Elements figure = elem.select("figure");
            Elements a = figure.select("a.product-link").select("div.product-image");
            Element img = a.select("img.product-card-image").first();
            String imgSrc = img.absUrl("data-src");
            InputStream input = new URL(imgSrc).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            market = "MİGROS";
            title = elem.select("h5").text();
            price = elem.select("div.price-campaign-container").text();

            ProductModel item = new ProductModel(bitmap, market, title, price);
            marketItems.add(item);
        }
    }

    private void goA101(Document doc)throws IOException{
        for (Element elem : doc.select("div.product-card")) {
            Elements actions = elem.select("div.product-actions");
            Elements a = actions.select("a");
            Element img = a.select("img").first();
            String imgSrc = img.absUrl("src");
            InputStream input = new URL(imgSrc).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            market = "A101";
            title = elem.select("h3").text();
            price = elem.select("div.prices").text();

            ProductModel item = new ProductModel(bitmap, market, title, price);
            marketItems.add(item);
        }
    }

    private void goSOK(Document doc)throws IOException{
        for (Element elem : doc.select("div.product-content")) {
            Elements a = elem.select("div.product-image-wrap").select("a.product-image");
            Element img = a.select("img").first();
            String imgSrc = img.absUrl("data-src");
            InputStream input = new URL(imgSrc).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            title = elem.select("div.product-description").text();
            price = elem.select("div.product-price").text();

            ProductModel item = new ProductModel(bitmap, market, title, price);
            marketItems.add(item);
        }
    }
}
