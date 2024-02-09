if (Build.VERSION.SDK_INT >= 21) { 
    Window window = this.getWindow(); 
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); 
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); 
    window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark)); 
}
// or from Class
public class Utils {

    public void TaskBarChangeColor(Window window, int color){
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }
}
//using in onCreate for example
Utils utils = new Utils();
utils.TaskBarChangeColor(this.getWindow(), this.getResources().getColor(R.color.colorBlack));
