private Handler customHandler = new Handler();
long timeInMilliseconds = 0L;
boolean isRunning = false;
long startTime = 0L;

private Runnable updateTimerThread = new Runnable() {
    public void run() {
        isRunning = true;
        timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
        int seconds = (int) (timeInMilliseconds / 1000) % 60 ;
        int minutes = (int) ((timeInMilliseconds / (1000*60)) % 60);
        int hours   = (int) ((timeInMilliseconds / (1000*60*60)) % 24);
        binding.connectionTimeValueTextView.setText(String.format("%02d", hours) +":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        Log.i("MyTag", "Timer is: "+String.format("%02d", hours) +":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        customHandler.postDelayed(this, 1000);
    }
    };
// START
if(!isRunning){
    startTime = SystemClock.uptimeMillis();
    customHandler.postDelayed(updateTimerThread, 0);
}
//STOP
customHandler.removeCallbacks(updateTimerThread);
isRunning = false;
