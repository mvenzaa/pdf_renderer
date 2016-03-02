package com.venza.pdfrendererdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {

    private ImageView imageView;
    private int currentPage = 0;
    private Button next, previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage++;
                render();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage--;
                render();
            }
        });

        render();
    }

    /*private void openRenderer(Activity activity) throws IOException {
        // Reading a PDF file from the assets directory.
        File file = activity.getAssets().openFd("sample.pdf").getParcelFileDescriptor();

        // This is the PdfRenderer we use to render the PDF.
        PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
    }*/

    private void render(){
        try{
          imageView = (ImageView) findViewById(R.id.image);
            int REQ_WIDTH = imageView.getWidth();
            int REQ_HEIGHT = imageView.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH, REQ_HEIGHT, Bitmap.Config.ARGB_4444);
            File file = new File("tes.otomindo.com/uploads/magazine/OTONOMINDO%20NOVEMBER%202015.pdf");
            //File file = new File("/storage/emulated/0/Download/SampleWarta.pdf");
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            //File file = activity.getAssets().openFd("samplee.pdf").getParcelFileDescriptor();

            // This is the PdfRenderer we use to render the PDF.

            if (currentPage < 0){
                currentPage = 0;
            } else if (currentPage > renderer.getPageCount()) {
                currentPage = renderer.getPageCount() - 1;
            }

            Matrix m = imageView.getImageMatrix();
            Rect rect = new Rect(0, 0, REQ_WIDTH, REQ_HEIGHT);
            renderer.openPage(currentPage).render(bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(m);
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


