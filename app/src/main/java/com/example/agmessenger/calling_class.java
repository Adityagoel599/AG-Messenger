//package com.example.agmessenger;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Application;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
//import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
//
//public class calling_class extends AppCompatActivity {
//EditText editText;
//Button btn;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calling_class);
//        editText=findViewById(R.id.Textid);
//        btn= findViewById(R.id.callogin);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startmyservice(editText.getText().toString());
//                Intent intent = new Intent(getApplicationContext(),Profile.class);
//                intent.putExtra("caller",editText.getText().toString().trim());
//                startActivity(intent);
//            }
//        });
//
//    }
//    public void startmyservice(String userid){
//        Application application =getApplication() ; // Android's application context
//        long appID =519001608 ;   // yourAppID
//        String appSign ="1e451f609735df7a31970e3e7ea1ce399afff49c01dac4ff1cd5f13f4214bf8e";  // yourAppSign
//        String userID =userid; // yourUserID, userID should only contain numbers, English characters, and '_'.
//        String userName =userid;   // yourUserName
//
//        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
//
//        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
//    }
//    public void onDestroy(){
//        super.onDestroy();
//        ZegoUIKitPrebuiltCallInvitationService.unInit();
//    }
//}