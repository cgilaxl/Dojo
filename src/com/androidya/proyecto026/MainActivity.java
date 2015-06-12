package com.androidya.proyecto026;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import android.media.audiofx.EnvironmentalReverb;
import android.annotation.SuppressLint;
import android.util.Log;
import android.media.audiofx.Equalizer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.media.audiofx.Virtualizer;
import android.media.audiofx.NoiseSuppressor;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.util.Log;

@SuppressLint("NewApi") public class MainActivity extends Activity implements OnCompletionListener, OnSeekBarChangeListener {
	TextView tv1,tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
	MediaRecorder recorder;
	MediaPlayer player;
	MediaPlayer player2;
	File archivo;
	ImageButton b1, b2, b3;
	Button  b5, b6, b7;
	SeekBar s1, s2, s3, s4, s5, s6, s7, s8;
	RelativeLayout layout1;
	private Button open_file_explorer;
	private String archivo_seleccionado;
	private String archivo_nombre;
	private static final String TAG="AudioFxDemo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layout1 = (RelativeLayout) this.findViewById(R.id.layout1);
		layout1.setBackgroundColor(Color.BLACK);
		
		tv1 = (TextView) this.findViewById(R.id.textView1);
		tv2 = (TextView) this.findViewById(R.id.textView2); 
		tv3 = (TextView) this.findViewById(R.id.textView3); 
		tv4 = (TextView) this.findViewById(R.id.textView4); 
		tv5 = (TextView) this.findViewById(R.id.textView5);
		tv6 = (TextView) this.findViewById(R.id.textView6);
		tv7 = (TextView) this.findViewById(R.id.textView7);
		tv8 = (TextView) this.findViewById(R.id.textView8);
		tv9 = (TextView) this.findViewById(R.id.textView9);
		
		tv1.setText("Estado");
		s1 = (SeekBar) findViewById(R.id.seekBar1);
		s2 = (SeekBar) findViewById(R.id.seekBar2);
		s3 = (SeekBar) findViewById(R.id.seekBar3);
		s4 = (SeekBar) findViewById(R.id.seekBar4);
		s5 = (SeekBar) findViewById(R.id.seekBar5);
		s6 = (SeekBar) findViewById(R.id.seekBar6);
		s7 = (SeekBar) findViewById(R.id.seekBar7);
		s8 = (SeekBar) findViewById(R.id.seekBar8);
		//tv1.setText(1);
		b1 = (ImageButton) findViewById(R.id.imageButton1);
		b2 = (ImageButton) findViewById(R.id.imageButton2);
		b3 = (ImageButton) findViewById(R.id.imageButton3);
		b5 = (Button) findViewById(R.id.button5);
		b6 = (Button) findViewById(R.id.button6);
		b7 = (Button) findViewById(R.id.button7);
		
		b1.setEnabled(false);
		b2.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		
		
		s1.setOnSeekBarChangeListener(this);
		s2.setOnSeekBarChangeListener(this);
		s3.setOnSeekBarChangeListener(this);
		s4.setOnSeekBarChangeListener(this);
		s5.setOnSeekBarChangeListener(this);
		s6.setOnSeekBarChangeListener(this);
		s7.setOnSeekBarChangeListener(this);
		s8.setOnSeekBarChangeListener(this);
		
		s1.setProgress(100);
		s2.setProgress(2000);
		s3.setProgress(1);
		s4.setProgress(1000);
		s5.setProgress(s5.getMax());
		s6.setProgress(s6.getMax());
		s7.setProgress(150);
		s8.setProgress(90);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void abrir(View v)
	{
	//Intent file_explorer = new Intent(MainActivity.this,FileExplorerActivity.class);
    //startActivityForResult(file_explorer, 555);// <-- �?
    tv1.setText("Elija un archivo para modificar");
	}
	
	   public void abrir() {
	       Intent data = new Intent();
	       data.putExtra("archivo_seleccionado", archivo_seleccionado);
	       data.putExtra("archivo_nombre", archivo_nombre);
	       setResult(RESULT_OK, data);
	       super.finish();
	   }
	
	public void grabar(View v) {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		File path = new File(Environment.getExternalStorageDirectory()
				.getPath());
		try {
			archivo = File.createTempFile("temporal", ".3gp", path);
		} catch (IOException e) {
		}
		recorder.setOutputFile(archivo.getAbsolutePath());
		try {
			recorder.prepare();
		} catch (IOException e) {
		}
		recorder.start();
		tv1.setText("Grabando");
		b1.setEnabled(false);
		b2.setEnabled(true);
		b3.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
	}

	public void detener(View v) {
		recorder.stop();
		recorder.release();
		
		b1.setEnabled(true);
		b2.setEnabled(false);
		b3.setEnabled(true);
		b5.setEnabled(true);
		b6.setEnabled(true);
		b7.setEnabled(true);
		tv1.setText("Listo para reproducir");
	}

	public void reproducir(View v) {
		player = new MediaPlayer();
		player.setOnCompletionListener(this);
		try {	
			player.setDataSource(archivo.getAbsolutePath());
			}
			catch (IOException e) {	}
		try{
			player.prepare();
		}
		catch(IOException e){}
		player.start();
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		tv1.setText("Reproduciendo");
	}
	
	public void reproducir2(View v) {

		player2 = new MediaPlayer();
		player2.setOnCompletionListener(this);
		try {
			player2.setDataSource(archivo.getAbsolutePath());
		} catch (IOException e) {
		}
		try {
			int hfradio = s1.getProgress();
			int decaytime = s2.getProgress()+100;
			int density = s3.getProgress();
			int diffusion = s4.getProgress();
			int reverblevel = s5.getProgress()-9000;
			int roomlevel = s6.getProgress()-9000;
			int reflection = s7.getProgress();
			int reverbDelay = s8.getProgress();
			EnvironmentalReverb  eReverb = new EnvironmentalReverb(1,0); 
			eReverb.setEnabled(true);
			eReverb.setDecayHFRatio((short) hfradio);
			eReverb.setDecayTime(decaytime);
			eReverb.setDensity((short) density);
			eReverb.setDiffusion((short) diffusion);
			eReverb.setReverbLevel((short) reverblevel);	
			eReverb.setRoomLevel((short)roomlevel);
			eReverb.setReflectionsDelay(reflection);
			eReverb.setReverbDelay(reverbDelay);
			player2.setAuxEffectSendLevel(1.0f);
			player2.attachAuxEffect(eReverb.getId());				
			player2.prepare();
		} catch (IOException e) {
		}
		player2.start();
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		tv1.setText("Reproduciendo");
	}
	
	public void reproducir3(View v)
	{
		for(int i=1; i<=5; i++)
		{
		dormir();
		reproducir(v);
		}
	}
	
	public void reproducir4(View v)
	{
		for(int i=1; i<=5; i++)
		{
		dormir();
		reproducir2(v);
		}
	}
	
	public void dormir()
	{
		try{
			Thread.sleep(1000);
			}
		catch(InterruptedException e){}
	}
	
	public void onCompletion(MediaPlayer mp) {
		b1.setEnabled(true);
		b2.setEnabled(true);
		b3.setEnabled(true);
		tv1.setText("Listo");
		b5.setEnabled(true);
		b6.setEnabled(true);
		b7.setEnabled(true);
	}
	
	private void actualizarseekbar()
	{
		int ihf = s1.getProgress();
		String shf = Integer.toString(ihf);
		tv2.setText("HF RADIO: "+shf);
		
		int idt = s2.getProgress()+100;
		String sdt = Integer.toString(idt);
		tv3.setText("DECAYTIME: "+sdt);

		int idens = s3.getProgress();
		String sdens = Integer.toString(idens);
		tv4.setText("DENSITY: "+sdens);
	
		int idiff = s4.getProgress();
		String sdiff = Integer.toString(idiff);
		tv5.setText("DIFFUSION: "+sdiff);

		int irlevel = s5.getProgress()-9000;
		String srlevel = Integer.toString(irlevel);
		tv6.setText("REVLEVEL: "+srlevel);

		int iroom = s6.getProgress()-9000;
		String sroom = Integer.toString(iroom);
		tv7.setText("ROOMLEVEL: "+sroom);

		int ireflection = s7.getProgress();
		String sreflection = Integer.toString(ireflection);
		tv8.setText("REFLEXDELAY: "+sreflection);

		int ireverbdelay = s8.getProgress();
		String sreverbdelay = Integer.toString(ireverbdelay);
		tv9.setText("REVDELAY: "+sreverbdelay);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		actualizarseekbar();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	    
}
