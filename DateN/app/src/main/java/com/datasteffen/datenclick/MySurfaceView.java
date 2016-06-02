package com.datasteffen.datenclick;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by steffen on 23-05-2016.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private static final String TAG = "MySurfaceView";


    private int width;
    private int height;

    private final SurfaceHolder mHolder;

    private Camera mCamera;
    private byte[] imageBytes;

    private boolean isPreviewRunning = false;


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mHolder.getSurface() == null){
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        ViewGroup.LayoutParams lp = this.getLayoutParams();
//        lp.width = 800;
//        lp.height = 600;
//        this.setLayoutParams(lp);

        synchronized (this) {
            if (isPreviewRunning)
                return;

            this.setWillNotDraw(false); // This allows us to make our own draw calls to this canvas

// camera id 1 = front
            mCamera = Camera.open(1);
            isPreviewRunning = true;
            Camera.Parameters p = mCamera.getParameters();
            Camera.Size size = p.getPreviewSize();
            width = size.width;
            height = size.height;
            p.setPreviewFormat(ImageFormat.NV21);
            showSupportedCameraFormats(p);
            mCamera.setParameters(p);
            mCamera.setDisplayOrientation(90);

            mCamera.startPreview();
            mCamera.setPreviewCallback(this);

        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (this) {
            try {
                if (mCamera != null) {
                    //mHolder.removeCallback(this);
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    isPreviewRunning  = false;
                    mCamera.release();
                }
            } catch (Exception e) {
                Log.e("Camera", e.getMessage());
            }
        }
    }

    public byte[] TakePicture()
    {
        return imageBytes;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d("Camera", "Got a camera frame");
        if (!isPreviewRunning)
            return;

        Canvas canvas = null;

        if (mHolder == null) {
            return;
        }

        try {

            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            YuvImage yuvImage1 = new YuvImage(data, ImageFormat.NV21, width, height, null);
            yuvImage1.compressToJpeg(new Rect(0, 0, width, height), 50, out1);
            imageBytes = out1.toByteArray();

            synchronized (mHolder) {
                canvas = mHolder.lockCanvas(null);
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
                imageBytes = out.toByteArray();
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                Matrix matrix = new Matrix();
                //matrix.postRotate(270);
                matrix.setScale(-1,1);
                matrix.postTranslate(width,0);
                image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);


                canvas.drawBitmap(image, 0,0 ,null);



            }
        }  catch (Exception e){
            e.printStackTrace();
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (canvas != null) {
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void showSupportedCameraFormats(Camera.Parameters p) {
        List<Integer> supportedPictureFormats = p.getSupportedPreviewFormats();
        Log.d(TAG, "preview format:" + cameraFormatIntToString(p.getPreviewFormat()));
        for (Integer x : supportedPictureFormats) {
            Log.d(TAG, "suppoterd format: " + cameraFormatIntToString(x.intValue()));
        }

    }

    private String cameraFormatIntToString(int format) {
        switch (format) {
            case PixelFormat.JPEG:
                return "JPEG";
            case PixelFormat.YCbCr_420_SP:
                return "NV21";
            case PixelFormat.YCbCr_422_I:
                return "YUY2";
            case PixelFormat.YCbCr_422_SP:
                return "NV16";
            case PixelFormat.RGB_565:
                return "RGB_565";
            default:
                return "Unknown:" + format;

        }
    }

}
