package de.fau.cs.mad.fablab.android.productMap;


public enum FablabView
{
    MAIN_ROOM("Main Room"),
    ELECTRIC_WORKSHOP("Electric Workshop"),
    ACRYLIC_GLAS_SHELF("Plexiglasregal"),
    LATHE_TABLE("Lathe Table"),
    CHEMISTRY_TABLE("Chemistry Table");

    private String productName;

    private FablabView(String productName)
    {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

}
