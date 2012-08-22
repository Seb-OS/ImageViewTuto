package com.sebastienmagat.imageviewtuto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sebastien Magat
 * 
 */

public class MainActivity extends Activity {

	ImageView imageView;
	TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// On cast notre ImageView et notre TextView en les reliant à la vue
		imageView = (ImageView) findViewById(R.id.image_view);
		textView = (TextView) findViewById(R.id.text_view);
		// On appelle nos 2 méthodes privées
		downloadImage();
		downloadText();
	}

	private void downloadImage() {
		/**
		 * On crée un objet Bitmap. Celui-ci servira a recevoir n'importe quel
		 * type d'image
		 */
		Bitmap bitmap = null;
		try {
			URL urlImage = new URL(
					"https://www.google.fr/images/srpr/logo3w.png");
			HttpURLConnection connection = (HttpURLConnection) urlImage
					.openConnection();
			InputStream inputStream = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
			imageView.setImageBitmap(bitmap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(this, "MalformedURLException !", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void downloadText() {
		try {
			URL urlPage = new URL("http://www.google.com");
			HttpURLConnection connection = (HttpURLConnection) urlPage
					.openConnection();
			InputStream inputStream = connection.getInputStream();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();

			String temp;
			/**
			 * On fait une boucle afin d'ajouter chaque ligne de texte au
			 * stringBuffer. Contrairement au String, il n'est pas immuable.
			 */
			while ((temp = bufferedReader.readLine()) != null) {
				stringBuffer.append(temp);

				if (!bufferedReader.ready()) {
					break;
				}
			}
			// On affiche le text receuilli dans la TextView
			textView.setText(stringBuffer.toString());
			// Et on pense à fermer les connections !
			connection.disconnect();
			bufferedReader.close();
			inputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(this, "MalformedURLException !", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
