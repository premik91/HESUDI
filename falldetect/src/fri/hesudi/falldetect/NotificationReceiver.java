package fri.hesudi.falldetect;

import android.os.Bundle;
import android.app.Activity;

public class NotificationReceiver extends Activity{
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.notification);
   }
}