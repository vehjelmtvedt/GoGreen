package data;

public abstract class TransportationActivity extends Activity{
    private int kilometres;

    /**
     * Constructor.
     */
    public TransportationActivity() {
        super();
        this.setCategory("Transportation");
        this.kilometres = 0;

    }
}
