public abstract class WarehouseState {
    protected static LibContext context;
    protected WarehouseState() {
        //context = LibContext.instance();
    }
    public abstract void run();
}
