// In the scope of class for example MainActivity
Handler handler; // the handler
// In the scope of method for example onCreate
handler = new Handler(); // Initialize the handler
// Do the job and calls runnable - somewhere in the scope of the class
private void worker(){
    Log.i("MyTag", "Worker");
    handler.postDelayed(runnable, 1000);//call runnable with (runnable, delay  in milliseconds)
}
// method to do the job and communicate with the main thread - somewhere in the scope of the class
private Runnable runnable = new Runnable() {
    @Override
    public void run() {
        worker();
    }
};