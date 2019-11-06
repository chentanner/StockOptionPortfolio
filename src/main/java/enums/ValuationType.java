package enums;

public enum ValuationType  {
    VEST(0),
    SALE(1),
    PERF(2);


    private Integer sortOrder;

    ValuationType(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int compareTo(ValuationType obj1, ValuationType obj2){
        return obj1.sortOrder.compareTo(obj2.sortOrder);
    }
}


