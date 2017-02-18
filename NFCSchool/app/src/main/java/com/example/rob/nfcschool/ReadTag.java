package com.example.rob.nfcschool;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//ACTIVITY TO READ TAG AND GET INFORMATION
public class ReadTag extends AppCompatActivity {
    private NfcAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_tag);
        adapter = NfcAdapter.getDefaultAdapter(this);

        if(adapter == null){
            Toast.makeText(getApplicationContext(),"NFC NOT ENABLED",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        resolveIntent(intent); //add resolve intent
        super.onNewIntent(intent);
    }
    @Override
    public void onResume(){
        Intent intent = new Intent(this,ReadTag.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        adapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
        super.onResume();
    }
    @Override
    public void onPause(){
        adapter.disableForegroundDispatch(this);
        super.onPause();
    }
    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
        {
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                buildTagViews(msgs);
            }

        }
    }

    void buildTagViews(NdefMessage[] msgs)
    {
        if (msgs == null || msgs.length == 0) {
            return;
        }

        String tagId = new String(msgs[0].getRecords()[0].getType());

        String body = new String(msgs[0].getRecords()[0].getPayload());
        MainActivity.mainActivity.setMessage(body);
        finish();


    }

}
