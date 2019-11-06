package enums;

public enum RecordType {
    VEST(0),
    SALE(1),
    PERF(2);


    private Integer sortOrder;

    RecordType(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int compareTo(RecordType obj1, RecordType obj2){
        return obj1.sortOrder.compareTo(obj2.sortOrder);
    }
}


